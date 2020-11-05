package com.vanguard8.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc
public class InterceptorConfig implements WebMvcConfigurer {

    private Logger logger = LoggerFactory.getLogger(InterceptorConfig.class);

    @Bean
    AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //这里可以添加多个拦截器
        logger.info("开始注册权限拦截器……");
        String[] excludes = new String[]{"/", "/error", "/login/**", "/static/**", "/test/**"};
        registry.addInterceptor(authInterceptor()).addPathPatterns("/**").excludePathPatterns(excludes);
        logger.info("权限拦截器注册完成！");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        logger.info("开放静态资源访问……");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
