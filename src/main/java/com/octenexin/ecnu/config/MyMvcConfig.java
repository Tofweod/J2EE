package com.octenexin.ecnu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/pwd_recovery").setViewName("pwd_recovery");
        registry.addViewController("/register").setViewName("register");


        //student
        registry.addViewController("/student/index").setViewName("/student/index");

        registry.addViewController("/student/message").setViewName("/student/message");

        registry.addViewController("/student/project/project-list").setViewName("/student/project/project-list");
        registry.addViewController("/student/project/project-add").setViewName("/student/project/project-add");
        registry.addViewController("/student/project/project-update").setViewName("/student/project/project-update");



        //admin
        registry.addViewController("/admin/index").setViewName("/admin/index");
        registry.addViewController("/admin/profile").setViewName("/admin/profile");
        
        // submit paper
        registry.addViewController("/paperForm").setViewName("paperForm");
    }

    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //open if you need intercept

//        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**")
//                .excludePathPatterns("/login.html","/","/user/login","/common/**");
    }
}
