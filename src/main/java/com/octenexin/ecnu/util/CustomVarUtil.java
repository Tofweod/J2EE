package com.octenexin.ecnu.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CustomVarUtil {
    public static String uploadUrl;

    @Value("${upload.path}")
    public void setUrl(String uploadUrl) {
        CustomVarUtil.uploadUrl= uploadUrl;
    }

}

