package com.octenexin.ecnu.controller;

import com.octenexin.ecnu.dao.PaperDao;
import com.octenexin.ecnu.pojo.Paper;
import com.octenexin.ecnu.util.FileSaveUtil;
import com.octenexin.ecnu.util.IdManageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class AdminPaperPageController {

    @Autowired
    PaperDao paperDao;


    @RequestMapping("/admin/paper-list-n")
    public String toPaperList(@RequestParam("page")String page, Model model){

        List<Paper> list=paperDao.autoQuery("select * from papers limit "+Integer.parseInt(page)*10+",10;");




        model.addAttribute("list",list);
        model.addAttribute("states",IdManageUtils.paperStateMap);

        System.out.println(IdManageUtils.paperStateMap);

        return "/admin/paper-list";
    }

    @RequestMapping("/admin/paper-details")
    public String toPaperDetails(@RequestParam("paperId") String paperId, Model model){

        Paper paper=new Paper();
        paper.setPaperId(Integer.parseInt(paperId));
        Paper realPaper=paperDao.getPaper(paper);

        model.addAttribute("paper",realPaper);
        model.addAttribute("paperFile", FileSaveUtil.getFileLoadRootUrl()+realPaper.getPaperUrl());
        model.addAttribute("paperState", IdManageUtils.paperStateMap);
        model.addAttribute("paperStateColor",IdManageUtils.paperStateColorMap);

        return "/admin/paper-details";
    }
}
