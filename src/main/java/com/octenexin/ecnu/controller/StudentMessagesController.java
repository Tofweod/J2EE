package com.octenexin.ecnu.controller;

import com.octenexin.ecnu.pojo.Message;
import com.octenexin.ecnu.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class StudentMessagesController {

    @Autowired
    MessageService messageService;


    @RequestMapping("/message/set-read")
    public String setRead(@RequestParam("messageId") String messageId){
        System.out.println(messageId);
        Message message=new Message();
        message.setMessageId(Integer.parseInt(messageId));

        System.out.println(message);

        messageService.setRead(message);

        return "success";
    }

    @RequestMapping("/message/delete")
    public String setDelete(@RequestParam("messageId") String messageId){

        System.out.println(messageId);
        Message message=new Message();
        message.setMessageId(Integer.parseInt(messageId));

        ArrayList<Message> list=new ArrayList<>();
        list.add(message);

        messageService.deleteMessages(list);

        return "success";
    }
}
