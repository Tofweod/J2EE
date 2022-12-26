package com.octenexin.ecnu.controller;

import com.octenexin.ecnu.dao.ProjectDao;
import com.octenexin.ecnu.dao.ProjectDelayDao;
import com.octenexin.ecnu.pojo.Project;
import com.octenexin.ecnu.pojo.ProjectDelay;
import com.octenexin.ecnu.util.IdManageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AdminProjectPageController {

    @Autowired
    ProjectDao projectDao;

    @Autowired
    ProjectDelayDao projectDelayDao;

    /**
     * default action.
     * display 1~10, latest first
     * */
    @RequestMapping("/admin/project-list-n")
    public String toProjectList(@RequestParam("page")String page,@RequestParam("step")String step,@RequestParam("orderby")String orderBy,@RequestParam("mode")String mode, Model model){


        model.addAttribute("project_state",IdManageUtils.projectStateMap);
        model.addAttribute("project_class",IdManageUtils.projectClassMap);
        model.addAttribute("projects",projectDao.autoQuery("select * from projects order by "+orderBy+" "+mode+" limit "+Integer.parseInt(page)*10+","+step+";"));
        model.addAttribute("page",page);
        model.addAttribute("step",step);
        model.addAttribute("orderby",orderBy);
        model.addAttribute("mode",mode);

        //handle pagnation
        int maxPages=(projectDao.count(new Project())/10)+1;//31->4
        int curPage=Integer.parseInt(page);//0,1,2,3


        Map<Integer,String> arr=new HashMap<>();
        for(int i=1;i<10;i++){
            if(curPage+i<maxPages) arr.put(curPage+i+1,"/admin/project-list-n?page="+(curPage+i)+"&step="+step+"&orderby="+orderBy+"&mode="+mode);
        }

        model.addAttribute("prev",curPage==0?"javascript: void(0);":"/admin/project-list-n?page="+(curPage-1)+"&step="+step+"&orderby="+orderBy+"&mode="+mode);
        model.addAttribute("next",curPage==maxPages-1?"javascript: void(0);":"/admin/project-list-n?page="+(curPage+1)+"&step="+step+"&orderby="+orderBy+"&mode="+mode);
        model.addAttribute("arr",arr);
        model.addAttribute("cur_page",curPage);
        model.addAttribute("max_pages",maxPages);

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

    @RequestMapping("/admin/project-details")
    public String toDetails(@RequestParam("id")String id,Model model){

        Project project=new Project();
        project.setProjectId(Integer.parseInt(id));
        Project realProject=projectDao.getProject(project);
        model.addAttribute("project",realProject);

        model.addAttribute("pState",IdManageUtils.projectStateMap.get(realProject.getProjectStateId()));
        model.addAttribute("pClass",IdManageUtils.projectClassMap.get(realProject.getProjectClassId()));

        model.addAttribute("className",IdManageUtils.projectStateColorMap.get(realProject.getProjectStateId()));

        ArrayList<String> member=new ArrayList<>(Arrays.asList(realProject.getProjectOtherPeopleInfo().split(";")));
        model.addAttribute("member",member);

        return "/admin/project-details";
    }

    @RequestMapping("/admin/project-delay-n")
    public String toDelay(@RequestParam("page")String page,Model model){

        model.addAttribute("list",projectDelayDao.getDelays(Integer.valueOf(page)));

        int maxPages=(projectDelayDao.countAll()/10)+1;//31->4
        int curPage=Integer.parseInt(page);//0,1,2,3


        Map<Integer,String> arr=new HashMap<>();
        for(int i=1;i<10;i++){
            if(curPage+i<maxPages) arr.put(curPage+i+1,"/admin/project-delay-n?page="+(curPage+i));
        }

        model.addAttribute("prev",curPage==0?"javascript: void(0);":"/admin/project-delay-n?page="+(curPage-1));
        model.addAttribute("next",curPage==maxPages-1?"javascript: void(0);":"/admin/project-delay-n?page="+(curPage+1));
        model.addAttribute("arr",arr);
        model.addAttribute("cur_page",curPage);



        return "/admin/project-delay";
    }

    @RequestMapping("admin/project/project-delay-details")
    public String toDelayDetails(@RequestParam("id")String id,Model model){
        ProjectDelay d=projectDelayDao.getDelayById(Integer.parseInt(id));
        model.addAttribute("delay",d);

        String className="";
        String stateName="";
        switch (d.getProjectDelayState()){
            case -1: {
                className= "badge badge-danger-lighten";
                stateName= "已驳回";
            }break;
            case 0: {
                className= "badge badge-primary-lighten";
                stateName= "未审批";
            }break;
            case 1: {
                className= "badge badge-success-lighten";
                stateName= "已通过";
            }break;
        }

        model.addAttribute("className",className);
        model.addAttribute("stateName",stateName);

        Project p=new Project();
        p.setProjectId(d.getProjectId());
        model.addAttribute("project",projectDao.getProject(p));

        return "/admin/project-delay-details";
    }


}
