package org.ssriot.cityoa.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        /*registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173") // 允许特定的来源
                //.allowedOriginPatterns("http://*.example.com", "http://another-example.com") // 使用模式匹配
                .allowCredentials(true) // 允许发送凭据
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的方法
                .allowedHeaders("*") // 允许的所有头部
                .maxAge(3600); // 最大缓存时间*/

    }
    
}
