package com.octenexin.ecnu.controller;

import com.octenexin.ecnu.dao.PaperDao;
import com.octenexin.ecnu.dao.ProjectDao;
import com.octenexin.ecnu.dao.UserDao;
import com.octenexin.ecnu.pojo.Paper;
import com.octenexin.ecnu.pojo.Project;
import com.octenexin.ecnu.pojo.User;
import com.octenexin.ecnu.util.IdManageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class StudentProfileController {

    @Autowired
    UserDao userDao;

    @Autowired
    ProjectDao projectDao;

    @RequestMapping("/student/profile")
    public String toStudentProfile(@RequestParam("id")String id, Model model, HttpSession session){


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

        model.addAttribute("projectList5",projectDao.getProjectByStu5(id));
        model.addAttribute("pClass",IdManageUtils.projectClassMap);
        model.addAttribute("pState",IdManageUtils.projectStateMap);

        return "/student/profile";
    }


    @RequestMapping("/admin/profile")
    public String toAdminProfile(@RequestParam("id")String id,Model model, HttpSession session){

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

        model.addAttribute("projectList5",projectDao.getProjectByStu5(id));
        model.addAttribute("pClass",IdManageUtils.projectClassMap);
        model.addAttribute("pState",IdManageUtils.projectStateMap);


        return "/admin/profile";
    }

}
