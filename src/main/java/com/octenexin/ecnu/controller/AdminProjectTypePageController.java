package com.octenexin.ecnu.controller;

import com.octenexin.ecnu.dao.ProjectTypeDao;
import com.octenexin.ecnu.pojo.Paper;
import com.octenexin.ecnu.pojo.ProjectType;
import com.octenexin.ecnu.util.IdManageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminProjectTypePageController {

    @Autowired
    ProjectTypeDao projectTypeDao;


    @RequestMapping("/admin/project-type-n")
    public String toPageN(@RequestParam("page")String page, Model model){

        model.addAttribute("list",projectTypeDao.getList(Integer.parseInt(page)));
        model.addAttribute("pClass", IdManageUtils.projectClassMap);

        int maxPages=(projectTypeDao.count()/10)+1;//31->4
        int curPage=Integer.parseInt(page);//0,1,2,3


        Map<Integer,String> arr=new HashMap<>();
        for(int i=1;i<10;i++){
            if(curPage+i<maxPages) arr.put(curPage+i+1,"/admin/project-type-n?page="+(curPage+i));
        }

        model.addAttribute("prev",curPage==0?"javascript: void(0);":"/admin/project-type-n?page="+(curPage-1));
        model.addAttribute("next",curPage==maxPages-1?"javascript: void(0);":"/admin/project-type-n?page="+(curPage+1));
        model.addAttribute("arr",arr);
        model.addAttribute("cur_page",curPage+1);


        return "/admin/project-type";
    }


    @RequestMapping("/admin/project/project-type-update")
    public String toUpdate(@RequestParam("id")String id, Model model){

        ProjectType type=new ProjectType();
        type.setProjectTypeId(Integer.parseInt(id));

        model.addAttribute("type",projectTypeDao.query(type));

        model.addAttribute("pClass",IdManageUtils.projectClassMap);



        return "/admin/project-type-update";

    }
}
