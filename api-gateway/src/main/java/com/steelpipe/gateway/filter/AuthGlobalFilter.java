package com.steelpipe.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * Gateway 全局认证过滤器
 * 对应原版的 JWTInterceptor + WebConfig 白名单机制
 * 
 * 认证流程：
 * 1. 检查白名单路径
 * 2. 从请求头/参数获取 token
 * 3. 解析 JWT 获取 userId-role
 * 4. 从 Redis 获取密码验证 JWT 签名
 * 5. 从 Redis 验证 IP 绑定
 * 6. 将用户信息传递给下游微服务
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(AuthGlobalFilter.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 白名单路径（不需要 token 验证），与原版 WebConfig 一致
     */
    private static final List<String> WHITE_LIST = Arrays.asList(
            "/login",
            "/api/v1/login",
            "/updatePassword",
            "/api/v1/updatePassword",
            "/files/download",
            "/api/v1/files/download",
            "/uniapp/getObtainInformationDataList",
            "/api/v1/uniapp/getObtainInformationDataList",
            "/uniapp/getfeaturedCompaniesDataList",
            "/api/v1/uniapp/getfeaturedCompaniesDataList",
            "/uniapp/getSteelPriceData",
            "/api/v1/uniapp/getSteelPriceData",
            "/uniapp/getIndustryNewsDetailData",
            "/api/v1/uniapp/getIndustryNewsDetailData",
            "/uniapp/getSampleData",
            "/api/v1/uniapp/getSampleData",
            "/uniapp/getAboutData",
            "/api/v1/uniapp/getAboutData",
            "/uniapp/getTechData",
            "/api/v1/uniapp/getTechData",
            "/hello",
            "/api/v1/hello",
            "/actuator"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        // 检查是否在白名单中
        if (isWhiteListed(path)) {
            return chain.filter(exchange);
        }

        // 1. 从请求头获取 token
        String token = request.getHeaders().getFirst("token");
        if (StrUtil.isBlank(token)) {
            // 尝试从查询参数获取
            token = request.getQueryParams().getFirst("token");
        }

        if (StrUtil.isBlank(token)) {
            log.warn("请求路径 {} 缺少 token", path);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 2. 解析 token 获取 userId-role
        String audience;
        String userId;
        String role;
        try {
            audience = JWT.decode(token).getAudience().get(0);
            String[] split = audience.split("-");
            userId = split[0];
            role = split[1];
        } catch (Exception e) {
            log.warn("token 解析失败: {}", e.getMessage());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 3. 从 Redis 获取密码（登录时由 user-service 的 TokenUtils 写入）
        String passwordKey = "token:password:" + audience;
        Object passwordObj = redisTemplate.opsForValue().get(passwordKey);
        if (passwordObj == null) {
            log.warn("token 密码缓存不存在, audience={}", audience);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        String password = passwordObj.toString();

        // 4. 验证 JWT 签名
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(password)).build();
            jwtVerifier.verify(token);
        } catch (Exception e) {
            log.warn("token 签名验证失败: {}", e.getMessage());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 5. 验证 IP 绑定
        String clientIp = getClientIp(request);
        String redisKey = "token:" + audience;
        Object storedIp = redisTemplate.opsForValue().get(redisKey);
        if (storedIp == null || !clientIp.equals(storedIp.toString())) {
            log.warn("token IP 不匹配, storedIp={}, clientIp={}", storedIp, clientIp);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 6. 认证通过，将用户信息放入请求头传递给下游微服务
        ServerHttpRequest mutatedRequest = request.mutate()
                .header("X-User-Id", userId)
                .header("X-User-Role", role)
                .build();

        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    /**
     * 检查路径是否在白名单中
     */
    private boolean isWhiteListed(String path) {
        for (String prefix : WHITE_LIST) {
            if (path.equals(prefix) || path.startsWith(prefix + "/")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取客户端真实 IP
     */
    private String getClientIp(ServerHttpRequest request) {
        String ip = request.getHeaders().getFirst("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddress() != null ? request.getRemoteAddress().getAddress().getHostAddress() : "unknown";
        }
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }

    @Override
    public int getOrder() {
        return -1; // 高优先级
    }
}
