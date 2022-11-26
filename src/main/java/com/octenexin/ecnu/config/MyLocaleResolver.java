package com.octenexin.ecnu.config;

import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class MyLocaleResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String location;
        if((location=request.getParameter("l"))!=null){
            String[] arr=location.split("_");
            return new Locale(arr[0],arr[1]);
        }
        return Locale.getDefault();
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        throw new RuntimeException("setLocale shouldn't be called!");
    }
}
