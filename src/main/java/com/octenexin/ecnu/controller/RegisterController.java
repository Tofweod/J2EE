package com.octenexin.ecnu.controller;


import com.octenexin.ecnu.pojo.User;
import com.octenexin.ecnu.service.UserService;
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

        User res=userService.getUser(user);
        if(res!=null){
            return "error";
        }

        userService.addUser(user);
        return "success";


    }
}
