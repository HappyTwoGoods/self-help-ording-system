package com.yangnan.selfhelpordingsystem;

import com.yangnan.selfhelpordingsystem.common.CookInterceptor;
import com.yangnan.selfhelpordingsystem.common.ManagerInterceptor;
import com.yangnan.selfhelpordingsystem.common.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private CookInterceptor cookInterceptor;
    @Resource
    private ManagerInterceptor managerInterceptor;
    @Resource
    private UserInterceptor userInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(managerInterceptor).addPathPatterns("/manager/**")
                .excludePathPatterns("/manager/login");
        registry.addInterceptor(cookInterceptor).addPathPatterns("/cook/**")
                .excludePathPatterns("/cook/login");
        registry.addInterceptor(userInterceptor).addPathPatterns("/user/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/goodsList");

    }

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600)
                .allowCredentials(true);
    }
}
