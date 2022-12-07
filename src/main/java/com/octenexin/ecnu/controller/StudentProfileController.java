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
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class StudentProfileController {

    @Autowired
    UserDao userDao;

    @Autowired
    ProjectDao projectDao;

    @RequestMapping("/student/profile")
    public String toStudentProfile(Model model, HttpSession session){

        String id= (String) session.getAttribute("loginUserId");
        System.out.println(id);

        User user=new User();
        user.setUserId(id);
        User realUser=userDao.getUserById(user);
        System.out.println(realUser);


        Project project=new Project();
        project.setProjectChargePersonId(id);

        Integer projectNums=projectDao.count(project);
        Integer projectFinishNums=projectDao.countByFinish(project);

        model.addAttribute("user",realUser);
        model.addAttribute("projectNums",projectNums);
        model.addAttribute("finishNums",projectFinishNums);


        return "/student/profile";
    }

}
