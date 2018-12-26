package com.test.springboot.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private static final String[] ORIGINS = { "GET", "POST", "PUT", "DELETE" };

    //解决跨域问题
    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        registry.addMapping("/**").allowedOrigins("*").allowCredentials(true).allowedMethods(ORIGINS)
                .maxAge(3600L);
    }
}
