package com.octenexin.ecnu.controller;


import com.octenexin.ecnu.pojo.User;
import com.octenexin.ecnu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping("/user/login")
    public String redirectRule(@RequestParam String email, @RequestParam String password, Model model, HttpSession session){

        User user=new User();
        user.setEmail(email);

        User res=userService.getUser(user);
        if(res==null||!Objects.equals(password, res.getUserPassword())){
            model.addAttribute("fail","true");
            return "/login";
        }

        session.setAttribute("loginUser",res.getUserName());
        if(res.getAuthority()==1){
            return "redirect:/admin/index";
        }else{
            return "redirect:/student/index";
        }


    }
}
