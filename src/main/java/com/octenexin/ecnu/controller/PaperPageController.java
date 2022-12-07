package com.octenexin.ecnu.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaperPageController {


    @RequestMapping("/student/paper/paper-add")
    public String toAddPaper(@RequestParam("id") Integer id, Model model){

        model.addAttribute("projectId",id);

        return "/student/paper/paper-add";
    }
}
