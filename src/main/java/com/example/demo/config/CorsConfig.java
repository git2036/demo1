package com.example.demo.config; // 根据你的项目结构调整包名

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 标记为配置类
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 允许所有路径
                .allowedOrigins("*") // 允许的前端域名
                .allowedMethods("*") // 允许的 HTTP 方法
                .allowedHeaders("*") // 允许的请求头
//                .allowCredentials(true) // 允许携带凭证（如 Cookie）
                .maxAge(3600); // 预检请求的缓存时间（秒）
    }
}
// 注意：你需要在 application.properties 中添加以下配置：
//# 允许跨域请求
//spring.web.cors.allowed-origins=*
//spring.web.cors.allowed-methods=*