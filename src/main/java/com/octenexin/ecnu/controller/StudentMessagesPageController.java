package com.octenexin.ecnu.controller;

import com.octenexin.ecnu.dao.MessageDao;
import com.octenexin.ecnu.pojo.Message;
import com.octenexin.ecnu.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class StudentMessagesPageController {

    @Autowired
    MessageDao messageDao;

    @Autowired
    MessageService messageService;


    @RequestMapping("/student/message")
    public String toMessage(HttpSession session,Model model){

//        String id= (String) session.getAttribute("loginUserId");
//        User user=new User();
//        user.setUserId(id);

        //System.out.println("to message!");


        List<Message> msgs=messageService.getMessages();

        //System.out.println(msgs);


        model.addAttribute("messages",msgs);

        return "/student/message";
    }


}
