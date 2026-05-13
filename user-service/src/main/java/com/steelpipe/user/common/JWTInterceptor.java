package com.steelpipe.user.common;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.steelpipe.user.entity.Account;
import com.steelpipe.common.exception.CustomerException;
import com.steelpipe.user.service.AdminService;
import com.steelpipe.user.service.UserService;
import com.steelpipe.user.utils.TokenUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JWTInterceptor implements HandlerInterceptor {

    @Resource
    private AdminService adminService;

    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 放行 OPTIONS 请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        // 1. 从请求头拿到token
        String token = request.getHeader("token");
        if (StrUtil.isEmpty(token)) {
            token = request.getParameter("token");
        }

        // 2. 认证token
        if (StrUtil.isBlank(token)) {
            throw new CustomerException("401", "您无权限操作");
        }

        Account account = null;
        try {
            // 拿到token 的载荷数据
            String audience = JWT.decode(token).getAudience().get(0);
            String[] split = audience.split("-");
            String userId = split[0];
            String role = split[1];
            // 根据token解析出来的userId去对应的表查询用户信息
            if ("ADMIN".equals(role)) {
                account = adminService.selectById(userId);
            } else if ("USER".equals(role)) {
                account = userService.selectById(userId);
            }
        } catch (Exception e) {
            throw new CustomerException("401", "您无权限操作");
        }

        if (account == null) {
            throw new CustomerException("401", "您无权限操作");
        }

        try {
            // 验证签名
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(account.getPassword())).build();
            jwtVerifier.verify(token);
        } catch (Exception e) {
            throw new CustomerException("401", "您无权限操作");
        }

        // 3. 验证 token 的 IP 是否匹配（防止多 IP 使用同一个 token）
        String clientIp = TokenUtils.getClientIp(request);
        if (!TokenUtils.verifyTokenIp(token, clientIp)) {
            throw new CustomerException("401", "Token 无效或已在其他设备登录");
        }

        return true;
    }
}
