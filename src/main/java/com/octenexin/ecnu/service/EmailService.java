package com.octenexin.ecnu.service;

public interface EmailService {
    /**
     * toEmail 接收验证码的邮箱
     * text 主题
     * message 主体信息
     * */
    public void sendEmail(String toEmail, String text, String message);

    public void sendHtmlMail(String to, String subject, String content);


}

