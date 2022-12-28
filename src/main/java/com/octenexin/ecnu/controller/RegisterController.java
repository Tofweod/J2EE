package com.octenexin.ecnu.controller;


import com.octenexin.ecnu.pojo.Message;
import com.octenexin.ecnu.pojo.User;
import com.octenexin.ecnu.service.MessageService;
import com.octenexin.ecnu.service.UserService;
import com.octenexin.ecnu.service.impl.MessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@RestController
public class RegisterController {

    @Autowired
    UserService userService;
    
    @RequestMapping("/user/register")
    @ResponseBody
    public String registerUser(@ModelAttribute User user, Model model, HttpSession session){

        //query, if has result, it fails!
        User res=userService.getUser(user);
        if(res!=null){
            return "error";
        }

        userService.addUser(user);
        MessageService messageService = (MessageService) session.getAttribute("messageService");
        // 设置欢迎信息

        Message message=new Message();
        message.setMessageUserId(user.getUserId());
        message.setMessageTopic("Welcome!");
        message.setMessageRawData("Dear "+user.getUserName()+": Hello world!");

        messageService.sendMessage(message);
        return "success";
    }
}
