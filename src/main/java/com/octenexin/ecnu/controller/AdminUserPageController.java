package com.octenexin.ecnu.controller;

import com.octenexin.ecnu.dao.PaperDao;
import com.octenexin.ecnu.dao.ProjectDao;
import com.octenexin.ecnu.dao.UserDao;
import com.octenexin.ecnu.pojo.Paper;
import com.octenexin.ecnu.pojo.Project;
import com.octenexin.ecnu.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminUserPageController {

    @Autowired
    UserDao userDao;

    @Autowired
    ProjectDao projectDao;

    @Autowired
    PaperDao paperDao;

    @RequestMapping("/admin/user-list-n")
    public String toPageN(@RequestParam("type")String type,@RequestParam("page")String page, Model model){
        model.addAttribute("list",userDao.autoQuery("select * from users where authority = "+type+" limit "+Integer.parseInt(page)*10+",10;"));

        int maxPages=(userDao.count(new User())/10)+1;//31->4
        int curPage=Integer.parseInt(page);//0,1,2,3


        Map<Integer,String> arr=new HashMap<>();
        for(int i=1;i<10;i++){
            if(curPage+i<maxPages) arr.put(curPage+i+1,"/admin/user-list-n?type="+type+"&page="+(curPage+i));
        }

        model.addAttribute("prev",curPage==0?"javascript: void(0);":"/admin/user-list-n?type="+type+"&page="+(curPage-1));
        model.addAttribute("next",curPage==maxPages-1?"javascript: void(0);":"/admin/user-list-n?type="+type+"&page="+(curPage+1));
        model.addAttribute("arr",arr);
        model.addAttribute("cur_page",curPage);

        return "/admin/user-list";

    }

    @RequestMapping("/admin/user-change")
    public String toUserChange(@RequestParam("id")String id,Model model){
        User user=new User();
        user.setUserId(id);

        model.addAttribute("user",userDao.getUserById(user));

        return "/admin/user-change";
    }

    @PostMapping("/admin/do-change")
    public String doUserChange(String email,String userId,String userName,String userPassword,String major,String authority){

        User user=new User();
        user.setEmail(email);
        user.setUserId(userId);
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        user.setMajor(major);
        user.setAuthority(Integer.parseInt(authority));

        userDao.update(user);

        return "redirect:/admin/user-list-n?page=1";
    }

    @PostMapping("/admin/user-delete")
    public String doUserDelete(@RequestParam("id")String id){

        //delete projects
        List<Project> projects=projectDao.getProjectByStu(id);
        for(int i=0;i<projects.size();i++){
            Integer paperId= projects.get(i).getProjectPaperId();

            //delete paper
            if(paperId!=null){
                Paper paper=new Paper();
                paper.setPaperId(paperId);
                paperDao.deletePaper(paper);
            }

            projectDao.delete(projects.get(i));
        }

        //delete user
        User user=new User();
        user.setUserId(id);
        userDao.delete(user);


        return "redirect:/admin/user-list-n?page=1";
    }

}
