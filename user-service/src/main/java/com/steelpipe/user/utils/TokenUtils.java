package com.steelpipe.user.utils;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.steelpipe.user.entity.Account;
import com.steelpipe.user.service.AdminService;
import com.steelpipe.user.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class TokenUtils {

    private static final Logger log = LoggerFactory.getLogger(TokenUtils.class);

    @Resource
    private AdminService adminService;

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private static AdminService staticAdminService;
    private static UserService staticUserService;
    private static RedisTemplate<String, Object> staticRedisTemplate;

    @PostConstruct
    public void init() {
        staticAdminService = adminService;
        staticUserService = userService;
        staticRedisTemplate = redisTemplate;
    }

    /**
     * 生成token
     * @param data userId-role
     * @param sign password（作为签名密钥）
     * @param ip 客户端IP地址
     */
    public static String createToken(String data, String sign, String ip) {
        // token 过期时间：2小时
        long expireTime = 2 * 60 * 60 * 1000;
        Date expiresAt = new Date(System.currentTimeMillis() + expireTime);

        String token = JWT.create()
                .withAudience(data) // 将 userId-role 保存到 token 里面,作为载荷
                .withExpiresAt(expiresAt) // 2小时后token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥, HMAC256算法加密

        // 将 IP 存储到 Redis，key 为 "token:{userId-role}", value 为 IP 地址
        String redisKey = "token:" + data;
        staticRedisTemplate.opsForValue().set(redisKey, ip, 2, TimeUnit.HOURS);

        // 将密码存储到 Redis，供 Gateway 验证 JWT 签名使用
        String passwordKey = "token:password:" + data;
        staticRedisTemplate.opsForValue().set(passwordKey, sign, 2, TimeUnit.HOURS);

        return token;
    }

    /**
     * 验证 token 的 IP 是否匹配
     * @param token JWT token
     * @param currentIp 当前请求的 IP 地址
     * @return 是否匹配
     */
    public static boolean verifyTokenIp(String token, String currentIp) {
        try {
            String audience = JWT.decode(token).getAudience().get(0);
            String redisKey = "token:" + audience;
            Object storedIp = staticRedisTemplate.opsForValue().get(redisKey);

            if (storedIp == null) {
                return false;
            }

            return currentIp.equals(storedIp.toString());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 删除 token（用于登出）
     * @param token JWT token
     */
    public static void removeToken(String token) {
        try {
            String audience = JWT.decode(token).getAudience().get(0);
            String redisKey = "token:" + audience;
            String passwordKey = "token:password:" + audience;
            staticRedisTemplate.delete(redisKey);
            staticRedisTemplate.delete(passwordKey);
        } catch (Exception e) {
            // 忽略异常
        }
    }

    /**
     * 获取当前登录的用户信息
     */
    public static Account getCurrentUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            token = request.getParameter("token");
        }
        // 拿到token 的载荷数据
        String audience = JWT.decode(token).getAudience().get(0);
        String[] split = audience.split("-");
        String userId = split[0];
        String role = split[1];
        // 根据token解析出来的userId去对应的表查询用户信息
        if ("ADMIN".equals(role)) {
            return staticAdminService.selectById(userId);
        } else if ("USER".equals(role)) {
            return staticUserService.selectById(userId);
        }
        return null;
    }

    /**
     * 获取当前请求的客户端IP
     */
    public static String getCurrentClientIp() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return "unknown";
        }
        HttpServletRequest request = attributes.getRequest();
        return getClientIp(request);
    }

    /**
     * 获取客户端真实IP地址
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
