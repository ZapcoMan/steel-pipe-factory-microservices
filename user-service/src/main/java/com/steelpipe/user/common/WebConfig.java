package com.steelpipe.user.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${jwt.interceptor.enabled:true}")
    private boolean jwtInterceptorEnabled;

    private final JWTInterceptor jwtInterceptor;

    public WebConfig(JWTInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (jwtInterceptorEnabled) {
            registry.addInterceptor(jwtInterceptor)
                    .addPathPatterns("/**")
                    .excludePathPatterns(
                            "/login",
                            "/updatePassword",
                            "/files/download/**",
                            "/uniapp/**",
                            "/hello",
                            "**/actuator/**"
                    );
        }
    }
}
