package com.quan.windsleeve.core.configuration;

import com.quan.windsleeve.core.interceptor.JwtTokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfigurer是一个Spring中，提供个性化配置的接口。如果我们需要在项目中添加自定义的
 * 拦截器，那么需要 implements WebMvcConfigurer 接口
 */
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Bean
    public JwtTokenInterceptor getJwtTokenInterceptorBean() {
        return new JwtTokenInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       // registry.addInterceptor(new JwtTokenInterceptor());
        registry.addInterceptor(this.getJwtTokenInterceptorBean());
    }
}
