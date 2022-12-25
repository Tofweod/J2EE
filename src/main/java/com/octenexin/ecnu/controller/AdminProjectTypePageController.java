package com.octenexin.ecnu.controller;

import com.octenexin.ecnu.dao.ProjectTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminProjectTypePageController {

    @Autowired
    ProjectTypeDao projectTypeDao;

    @RequestMapping("/admin/project-type")
    public String toPageN(@RequestParam("page")String page, Model model){

        model.addAttribute("list",projectTypeDao.getList(page));

        return "/admin/project-type";
    }
}
