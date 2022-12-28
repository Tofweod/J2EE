package com.octenexin.ecnu.controller;

import com.octenexin.ecnu.dao.MessageDao;
import com.octenexin.ecnu.pojo.Message;
import com.octenexin.ecnu.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class StudentMessagesPageController {

    @Autowired
    MessageDao messageDao;

    @Autowired
    MessageService messageService;


    @RequestMapping("/student/message-n")
    public String toMessage(@RequestParam("page")String page, HttpSession session, Model model){

        String id= (String) session.getAttribute("loginUserId");

        model.addAttribute("messages",messageService.getMessages(id,Integer.valueOf(page)));



        int maxPages=(messageDao.getMsgCntByUser(id)/10)+1;//31->4
        int curPage=Integer.parseInt(page);//0,1,2,3


        Map<Integer,String> arr=new HashMap<>();
        for(int i=1;i<10;i++){
            if(curPage+i<maxPages) arr.put(curPage+i+1,"/student/message-n?page="+(curPage+i));
        }

        model.addAttribute("prev",curPage==0?"javascript: void(0);":"/student/message-n?page="+(curPage-1));
        model.addAttribute("next",curPage==maxPages-1?"javascript: void(0);":"/student/message-n?page="+(curPage+1));
        model.addAttribute("arr",arr);
        model.addAttribute("cur_page",curPage+1);

        model.addAttribute("maxPages",maxPages);


        return "/student/message";
    }

    @RequestMapping("/admin/message-n")
    public String toAdminMessage(@RequestParam("page")String page, HttpSession session,Model model){

//        String id= (String) session.getAttribute("loginUserId");
//        User user=new User();
//        user.setUserId(id);

        //System.out.println("to message!");

        String id= (String) session.getAttribute("loginUserId");

        model.addAttribute("messages",messageService.getMessages(id, Integer.valueOf(page)));



        int maxPages=(messageDao.getMsgCntByUser(id)/10)+1;//31->4
        int curPage=Integer.parseInt(page);//0,1,2,3


        Map<Integer,String> arr=new HashMap<>();
        for(int i=1;i<10;i++){
            if(curPage+i<maxPages) arr.put(curPage+i+1,"/admin/message-n?page="+(curPage+i));
        }

        model.addAttribute("prev",curPage==0?"javascript: void(0);":"/admin/message-n?page="+(curPage-1));
        model.addAttribute("next",curPage==maxPages-1?"javascript: void(0);":"/admin/message-n?page="+(curPage+1));
        model.addAttribute("arr",arr);
        model.addAttribute("cur_page",curPage+1);

        model.addAttribute("maxPages",maxPages);

        return "/admin/message";
    }

}
