package com.maptools.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * 重写则要集成WebMvcConfigurerAdapter
 * Created by mr on 2017/8/21.
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.maptools")
public class SpringMvcConfig extends WebMvcConfigurerAdapter{

    //视图解析设置
    @Bean
    public InternalResourceViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver=new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(JstlView.class);
        return viewResolver;
    }

    //上传文件大小设置
    @Bean
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(20480000);
        multipartResolver.setDefaultEncoding("UTF-8");
        return multipartResolver;
    }

    //静态文件设置
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //对外访问路径    文件访问目录
        registry.addResourceHandler("/images/**","/js/**").addResourceLocations("/images/","/js/");
    }

    //跳转页面设置
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/baidu").setViewName("baidu");
    }

    //访问路径设置
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        //不忽略路径中.后的内容
        configurer.setUseSuffixPatternMatch(false);
    }
}
