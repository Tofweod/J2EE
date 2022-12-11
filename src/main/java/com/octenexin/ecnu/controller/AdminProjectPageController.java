package com.octenexin.ecnu.controller;

import com.octenexin.ecnu.dao.ProjectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminProjectPageController {

    @Autowired
    ProjectDao projectDao;

    /**
     * default action.
     * display 1~10, latest first
     * */
    @RequestMapping("/admin/project-list")
    public String toProjectList(Model model){




        return "/admin/project-list";

    }
}
