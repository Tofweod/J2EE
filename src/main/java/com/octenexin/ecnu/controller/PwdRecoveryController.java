package com.octenexin.ecnu.controller;

import com.octenexin.ecnu.pojo.User;
import com.octenexin.ecnu.service.EmailService;
import com.octenexin.ecnu.service.UserService;
import com.octenexin.ecnu.service.VerificationCodeService;
import com.octenexin.ecnu.util.FileLoadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PwdRecoveryController {

    @Autowired
    EmailService emailService;

    @Autowired
    VerificationCodeService verificationCodeService;

    @Autowired
    UserService userService;

    @RequestMapping("/mail")
    @ResponseBody
    public String handleMail(@RequestParam("email") String email, Model model){
        String captcha=verificationCodeService.genVerificationCode();


        FileLoadUtil.loadEmailTemplate("templates/mail-code.html");
        String template=FileLoadUtil.getEmailTemplate(captcha);
        emailService.sendHtmlMail(email,"Pwd Recovery",template);

        return captcha;

    }

    @RequestMapping("/pwd/change")
    @ResponseBody
    public String changePwd(@RequestParam("email") String email, @RequestParam("newPwd") String newPwd){

        User user=new User();
        user.setEmail(email);
        User target=userService.getUser(user);

        if(target==null){
            return "error";
        }

        target.setUserPassword(newPwd);
        userService.updateUser(target);
        return "success";

    }


}
