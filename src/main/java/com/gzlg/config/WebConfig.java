package com.gzlg.config;

import com.gzlg.interceptor.LoginCheckInterceptor;
import com.gzlg.interceptor.RequestLoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * Spring MVC 配置类
 * <p>
 * 实现 WebMvcConfigurer 接口，用于自定义 Spring MVC 的行为，
 * 例如注册拦截器、配置跨域、配置消息转换器等。
 * <p>
 * 当前配置：
 * - 注册 {@link RequestLoggingInterceptor}，对所有路径（除 /error）记录请求日志
 *
 * @author gzlg
 * @see RequestLoggingInterceptor
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private RequestLoggingInterceptor requestLoggingInterceptor;

    @Autowired
    private LoginCheckInterceptor loginCheckInterceptor;

    /**
     * 注册拦截器
     * <p>
     * 拦截路径：/** （所有请求）
     * 排除路径：/error （Spring Boot 默认错误页面，无需记录日志）
     *
     * @param registry 拦截器注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestLoggingInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/error", "/image/**");

        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/error", "/image/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String imageDir = System.getProperty("user.dir") + File.separator + "image" + File.separator;
        registry.addResourceHandler("/image/**")
                .addResourceLocations("file:" + imageDir);
    }
}
