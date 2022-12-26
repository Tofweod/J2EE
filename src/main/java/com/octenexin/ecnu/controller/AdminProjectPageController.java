package com.octenexin.ecnu.controller;

import com.octenexin.ecnu.dao.ProjectDao;
import com.octenexin.ecnu.dao.ProjectDelayDao;
import com.octenexin.ecnu.pojo.Project;
import com.octenexin.ecnu.util.IdManageUtils;
import com.octenexin.ecnu.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminProjectPageController {

    //where
    private static String cpId;
    private static String hFunds;
    private static String lFunds;
    private static String sTime;
    private static String eTime;
    private static String cId;
    private static String sId;

    //order by
    private static String orderBy="project_id";
    private static String mode="ASC";

    //limit
    private static int beg=0;
    private static int step=10;

    private static String sql="select * from projects ";


    @Autowired
    ProjectDao projectDao;

    @Autowired
    ProjectDelayDao projectDelayDao;


    private void doQuery(Model model){

        String newSql=sql;

        //where
        if(!StringUtil.isEmptyString(cpId)) newSql+= ("where project_charge_person_id = "+cpId+" ");
        if(!StringUtil.isEmptyString(cId)) newSql+= ("where project_class_id = "+cId+" ");
        if(!StringUtil.isEmptyString(sId)) newSql+= ("where project_state_id = "+sId+" ");
        if(!StringUtil.isEmptyString(lFunds)) newSql+= ("where project_funds_up between "+lFunds+" and "+hFunds+" ");
        if(!StringUtil.isEmptyString(sTime)) newSql+= ("where project_end_time between "+sTime+" and "+eTime+" ");
        //order by, limit
        newSql+=("order by "+orderBy+" "+mode+" " +
                "limit "+beg*10+","+step+";");

        System.out.println(newSql);

        List<Project> projects=projectDao.autoQuery(newSql);
        int projectCnt=projectDao.count(new Project());


        model.addAttribute("projects",projects);
        model.addAttribute("project_class", IdManageUtils.projectClassMap);
        model.addAttribute("project_state",IdManageUtils.projectStateMap);
        model.addAttribute("project_cnt",projectCnt);


        model.addAttribute("cpId",cpId);
        model.addAttribute("cId",cId);
        model.addAttribute("sId",sId);
        model.addAttribute("lFunds",lFunds);
        model.addAttribute("hFunds",hFunds);
        model.addAttribute("sTime",sTime);
        model.addAttribute("eTime",eTime);

        model.addAttribute("beg",beg+1);
        model.addAttribute("end",beg+step);
        model.addAttribute("step",step);

    }

    /**
     * default action.
     * display 1~10, latest first
     * */
    @RequestMapping("/admin/project-list")
    public String toProjectList(Model model){


        doQuery(model);


        return "/admin/project-list";

    }

    /**
     * order-by name, type
     * */
    @RequestMapping("/admin/project-list/order-by")
    public String doOrderBy(String o,String t,Model model){

        orderBy=o;
        mode=t;

        doQuery(model);

        return "redirect:/admin/project-list";
    }



    /**
     * page 1
     * */
    @RequestMapping("/admin/project-list/limit")
    public String doLimit(String i,Model model){

        step=Integer.parseInt(i);

        doQuery(model);

        return "redirect:/admin/project-list";
    }

    /**
     * page 1
     * */
    @RequestMapping("/admin/project-list-n")
    public String doPage(@RequestParam("page")String page,Model model){

        beg=Integer.parseInt(page);

        doQuery(model);

        return "redirect:/admin/project-list";
    }

    /**
     * lost all filters
     * page 1
     * */
    @RequestMapping("/admin/project-list/filter")
    public String doFilter(String projectChargePersonId,String projectClass,String projectState,String fundsDown,String fundsUp,String startTime,String endTime,Model model){

        cpId=projectChargePersonId;
        lFunds=fundsDown;
        hFunds=fundsUp;
        sTime=startTime;
        eTime=endTime;
        cId=projectClass;
        sId=projectState;

        doQuery(model);

        return "/admin/project-list";
    }

    @RequestMapping("/admin/project-update")
    public String toUpdate(@RequestParam("id")String id,Model model){

        Project project=new Project();
        project.setProjectId(Integer.parseInt(id));
        model.addAttribute("project",projectDao.getProject(project));

        model.addAttribute("pClass",IdManageUtils.projectClassMap);

        return "/admin/project-update";
    }

    @RequestMapping("/admin/project-delay-n")
    public String toDelay(@RequestParam("page")String page,Model model){

        model.addAttribute("list",projectDelayDao.getDelays(Integer.valueOf(page)));



        return "/admin/project-delay";
    }
}
