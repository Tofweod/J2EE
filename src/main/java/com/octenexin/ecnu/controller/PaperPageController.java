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

@Controller
public class PaperPageController {

    @Autowired
    PaperDao paperDao;


    @RequestMapping("/student/paper/paper-add")
    public String toAddPaper(@RequestParam("id") Integer id, Model model){

        model.addAttribute("projectId",id);

        return "/student/paper/paper-add";
    }

    @RequestMapping("/student/paper/paper-update")
    public String toAddPaper(@RequestParam("projectId")String projectId,@RequestParam("paperId") String paperId, Model model){

        Paper paper=new Paper();
        paper.setPaperId(Integer.parseInt(paperId));
        Paper realPaper=paperDao.getPaper(paper);

        model.addAttribute("projectId",projectId);
        model.addAttribute("paper",realPaper);

        return "/student/paper/paper-update";
    }

    @RequestMapping("/student/paper/paper-details")
    public String toPaperDetails(@RequestParam("paperId") String paperId, Model model){

        Paper paper=new Paper();
        paper.setPaperId(Integer.parseInt(paperId));
        Paper realPaper=paperDao.getPaper(paper);

        model.addAttribute("paper",realPaper);
        model.addAttribute("paperFile", FileSaveUtil.getFileLoadRootUrl()+realPaper.getPaperUrl());
        model.addAttribute("paperState", IdManageUtils.paperStateMap);

        return "/student/paper/paper-details";
    }
}
