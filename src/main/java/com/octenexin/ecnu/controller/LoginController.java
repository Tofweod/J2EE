package com.octenexin.ecnu.controller;


import com.octenexin.ecnu.dao.MessageDao;
import com.octenexin.ecnu.pojo.User;
import com.octenexin.ecnu.service.MessageService;
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
    
    @Autowired
    MessageService messageService;

    @Autowired
    MessageDao messageDao;

    @PostMapping("/user/login")
    public String redirectRule(@RequestParam String email, @RequestParam String password, Model model, HttpSession session){
    
        // 添加邮件服务至会话
        if(session.getAttribute("messageService") == null)
            session.setAttribute("messageService",messageService);

        User user=new User();
        user.setEmail(email);

        User res=userService.getUser(user);
        if(res==null||!Objects.equals(password, res.getUserPassword())){
            model.addAttribute("fail","true");
            return "/login";
        }


        //session设置用户；以后都用
        session.setAttribute("loginUser",res.getUserName());
        session.setAttribute("loginUserId",res.getUserId());

        //初始消息
        session.setAttribute("messageCnt",messageDao.getMsgUnreadCntByUser(res.getUserId()));
        
        if(res.getAuthority()==1){
            return "redirect:/admin/index";
        }else{
            return "redirect:/student/index";
        }


    }
}
