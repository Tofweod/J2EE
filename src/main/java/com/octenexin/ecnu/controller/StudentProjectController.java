package com.octenexin.ecnu.controller;

import com.octenexin.ecnu.dao.ProjectClassDao;
import com.octenexin.ecnu.dao.ProjectDao;
import com.octenexin.ecnu.dao.ProjectTypeDao;
import com.octenexin.ecnu.pojo.Project;
import com.octenexin.ecnu.pojo.ProjectClass;
import com.octenexin.ecnu.pojo.ProjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
public class StudentProjectController {

    @Autowired
    ProjectClassDao projectClassDao;

    @Autowired
    ProjectDao projectDao;

    @Autowired
    ProjectTypeDao projectTypeDao;

    @PostMapping("/student/add/get-type")
    @ResponseBody
    public List<ProjectType> getType(@RequestParam("projectClass") String projectClass){

        ProjectType temp=new ProjectType();
        temp.setProjectClassId(Integer.parseInt(projectClass));
        //System.out.println(projectTypeDao.getListByClass(temp));

        return projectTypeDao.getListByClass(temp);
    }

    @PostMapping("/student/add/get-end-time")
    @ResponseBody
    public String getEndTime(@RequestParam("projectTypeId") String projectTypeId){

        ProjectType temp=new ProjectType();
        temp.setProjectTypeId(Integer.parseInt(projectTypeId));
        //System.out.println(projectTypeDao.query(temp));

        SimpleDateFormat formatter=new SimpleDateFormat("MM/dd/yyyy");

        return formatter.format(projectTypeDao.query(temp).getProjectTypeEndTime());
    }


    @PostMapping("/student/do-add")
    public String doAddProject(@RequestParam("projectName")String projectName,
                               @RequestParam("projectChargePersonId")String projectChargePersonId,
                               @RequestParam("otherMembers")String otherMembers,
                               @RequestParam("fundsBudget")String fundsBudget,
                               @RequestParam("projectAbout")String projectAbout,
                               @RequestParam("projectClass")String projectClass,
                               @RequestParam("startTime")String startTime,
                               @RequestParam("endTime")String endTime,
                               //@RequestParam("fileList")String fileList,
                               Model model) throws ParseException {



        Project project=new Project();

        int cnt=projectDao.count(project);
        SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy");

        project.setProjectId(cnt+1);
        project.setProjectName(projectName);
        project.setProjectChargePersonId(projectChargePersonId);
        project.setProjectOtherPeopleInfo(otherMembers);
        project.setProjectFundsLow(0);
        project.setProjectFundsUp(Integer.parseInt(fundsBudget));
        project.setProjectAbout(projectAbout);
        //paperId=null
        project.setProjectClassId(Integer.valueOf(projectClass));
        project.setProjectStateId(1);
        project.setProjectPrestateId(1);
        project.setProjectStartTime(new java.sql.Date(format.parse(startTime).getTime()));
        project.setProjectEndTime(new java.sql.Date(format.parse(endTime).getTime()));


        //System.out.println(project);
        //System.out.println(fileList);

        projectDao.addProject(project);

        return "success";



    }

    @PostMapping("/student/do-update")
    public String doUpdate(@RequestParam("projectId") String id,
                            @RequestParam("projectName") String projectName,
                           @RequestParam("otherMembers") String otherMembers,
                           @RequestParam("fundsBudget") Integer fundsBudget,
                           @RequestParam("projectAbout") String projectAbout,
                           @RequestParam("endTime") String endTime,
                           @RequestParam("shouldDelay") Boolean shouldDelay,
                           @RequestParam("delayReason") String delayReason,
                           Model model){

        Project project=new Project();
        project.setProjectId(Integer.parseInt(id));
        project.setProjectName(projectName);
        project.setProjectOtherPeopleInfo(otherMembers);
        project.setProjectFundsUp(fundsBudget);
        project.setProjectAbout(projectAbout);


        projectDao.update(project);

        //delay table
        SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy");


        return "success";
    }

    @PostMapping("/student/project/do-delete")
    public String doUpdate(@RequestParam("id") String id){

        Project project=new Project();
        project.setProjectId(Integer.parseInt(id));


        projectDao.delete(project);

        //handle paper table, paper directory...
        //"cascade"


        return "success";
    }


}
