package org.ssriot.cityoa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.ssriot.cityoa.interceptor.LoginInterceptor;

//注册拦截器
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        // 拦截所有请求，但排除以下不需要认证的接口
        registry.addInterceptor(loginInterceptor)
                .excludePathPatterns(
                        // 用户认证相关
                        "/user/login",
                        "/user/register",

                        // 设备类型相关 (根据需求决定是否放行)
                        "/device-type/**",

                        // 设备数据查询相关
                        "/iot-data/",                 // 获取所有数据
                        "/iot-data/search",            // 搜索数据
                        "/iot-data/{id}/device",       // 获取数据关联设备

                        // 设备信息查询相关
                        "/device/",                   // 获取所有设备
                        "/device/{id}",               // 获取设备详情
                        "/device/{deviceId}/data",    // 获取设备数据

                        // 设备共享信息查询相关
                        "/user-device/user/{userId}",  // 获取用户共享设备
                        "/user-device/device/{deviceId}", // 获取设备共享用户

                        // 特殊数据上报接口
                        "/iot-data/"                  // 数据上报
                );
    }
}
