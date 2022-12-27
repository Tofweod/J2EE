package com.octenexin.ecnu.controller;

import com.octenexin.ecnu.dao.PaperDao;
import com.octenexin.ecnu.pojo.Paper;
import com.octenexin.ecnu.pojo.User;
import com.octenexin.ecnu.util.CustomVarUtil;
import com.octenexin.ecnu.util.FileSaveUtil;
import com.octenexin.ecnu.util.IdManageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class AdminPaperPageController {

    @Autowired
    PaperDao paperDao;


    @RequestMapping("/admin/paper-list-n")
    public String toPaperList(@RequestParam("page")String page, Model model){

        List<Paper> list=paperDao.autoQuery("select * from papers order by paper_id desc limit "+Integer.parseInt(page)*10+",10;");


        int maxPages=(paperDao.countPapers(new Paper())/10)+1;//31->4
        int curPage=Integer.parseInt(page);//0,1,2,3


        Map<Integer,String> arr=new HashMap<>();
        for(int i=1;i<10;i++){
            if(curPage+i<maxPages) arr.put(curPage+i+1,"/admin/paper-list-n?page="+(curPage+i));
        }

        model.addAttribute("prev",curPage==0?"javascript: void(0);":"/admin/paper-list-n?page="+(curPage-1));
        model.addAttribute("next",curPage==maxPages-1?"javascript: void(0);":"/admin/paper-list-n?page="+(curPage+1));
        model.addAttribute("arr",arr);
        model.addAttribute("cur_page",curPage);



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

        String keywords=realPaper.getPaperKeywords();
        List<String> keys= Arrays.asList(keywords.split(";"));

        model.addAttribute("paper",realPaper);
        model.addAttribute("paperKeywords",keys);
        model.addAttribute("paperState", IdManageUtils.paperStateMap);
        model.addAttribute("paperStateColor",IdManageUtils.paperStateColorMap);



        return "/admin/paper-details";
    }
}
