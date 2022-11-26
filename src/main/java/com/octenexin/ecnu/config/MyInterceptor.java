package com.octenexin.ecnu.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getSession().getAttribute("loginUser")==null){
            request.setAttribute("message","No authority! Please login first.");
            request.getRequestDispatcher("/login.html").forward(request,response);
            return false;
        }
        return true;
    }
}
