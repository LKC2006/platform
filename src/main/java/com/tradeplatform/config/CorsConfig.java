package com.tradeplatform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 允许所有前端地址访问（本地测试用，部署后可改为具体域名）
        registry.addMapping("/**")
                .allowedOrigins("*") // 允许所有跨域来源
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的请求方法
                .allowedHeaders("*") // 允许所有请求头（包括token）
                .maxAge(3600); // 预检请求缓存时间（减少重复预检）
    }
}