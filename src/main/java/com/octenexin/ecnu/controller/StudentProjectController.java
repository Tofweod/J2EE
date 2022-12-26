package com.octenexin.ecnu.controller;

import com.octenexin.ecnu.EcnuApplication;
import com.octenexin.ecnu.dao.*;
import com.octenexin.ecnu.pojo.*;
import com.octenexin.ecnu.service.MessageService;
import com.octenexin.ecnu.service.ProjectService;
import com.octenexin.ecnu.util.FileLoadUtil;
import com.octenexin.ecnu.util.FileSaveUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
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

    @Autowired
    PaperDao paperDao;

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectDelayDao projectDelayDao;

    @Autowired
    MessageService messageService;


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
                           @RequestParam("oldEndTime") String oldEndTime,
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

        //add delay table
        if(shouldDelay){
            ProjectDelay delay=new ProjectDelay();
            //primary key inc
            delay.setProjectId(Integer.parseInt(id));
            try{
                SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy");
                delay.setProjectOldEndTime(new java.sql.Date(format.parse(oldEndTime).getTime()));
                delay.setProjectNewEndTime(new java.sql.Date(format.parse(endTime).getTime()));
            }catch (ParseException e){
                e.printStackTrace();
            }

            delay.setProjectDelayReason(delayReason);

            projectDelayDao.addDelay(delay);
        }


        return "success";
    }

    @PostMapping("/admin/project/do-update")
    public String doAdminUpdate(@RequestParam("projectId") String id,
                                @RequestParam("projectName") String projectName,
                                @RequestParam("projectChargePersonId") String projectChargePersonId,
                                @RequestParam("otherMembers") String otherMembers,
                                @RequestParam("fundsBudget") Integer fundsBudget,
                                @RequestParam("projectAbout") String projectAbout,
                                @RequestParam("projectClass") String projectClass,
                                @RequestParam("startTime") String startTime,
                                @RequestParam("endTime") String endTime){


        Project project=new Project();
        project.setProjectId(Integer.valueOf(id));
        project.setProjectName(projectName);
        project.setProjectChargePersonId(projectChargePersonId);
        project.setProjectOtherPeopleInfo(otherMembers);
        project.setProjectFundsUp(fundsBudget);
        project.setProjectAbout(projectAbout);
        project.setProjectClassId(Integer.valueOf(projectClass));
        try{
            SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy");
            project.setProjectStartTime(new java.sql.Date(format.parse(startTime).getTime()));
            project.setProjectEndTime(new java.sql.Date(format.parse(endTime).getTime()));
        }catch (ParseException e){
            e.printStackTrace();
        }

        projectDao.adminUpdate(project);

        return "success";

    }

    @PostMapping("/student/project/do-delete")
    public String doDelete(@RequestParam("id") String id){

        projectService.deleteProject(Integer.valueOf(id));


        return "success";
    }

    @PostMapping("/admin/project/project-delay/do-delete")
    public String doDeleteDelay(@RequestParam("id")String id){
        projectDelayDao.deleteDelay(Integer.parseInt(id));

        return "success";
    }

    @PostMapping("/admin/project/project-delay/do-operate")
    public String doOperateDelay(@RequestParam("id")String id,@RequestParam("oper")String oper,@RequestParam("reason")String reason){
        projectDelayDao.handleDelay(Integer.parseInt(id),Integer.parseInt(oper));

        ProjectDelay d=projectDelayDao.getDelayById(Integer.parseInt(id));
        Project p=new Project();
        p.setProjectId(d.getProjectId());

        Project realProject=projectDao.getProject(p);

        //update new end time
        if(oper.equals("1")){
            realProject.setProjectEndTime(d.getProjectNewEndTime());
            projectDao.adminUpdate(realProject);
        }

        //send message to user
        String userId=realProject.getProjectChargePersonId();

        Message message=new Message();
        message.setMessageTopic("延期申请"+(oper.equals("1") ?"通过":"驳回")+"通知");
        message.setMessageUserId(userId);
        message.setMessageRawData("您的项目\""+realProject.getProjectName()+"\"的延期申请已"+(oper.equals("1") ?"通过！":("被驳回，原因为："+reason)));

        messageService.sendMessage(message);

        return "success";
    }
    
    // 下载xlsx文件
    public void downloadXLSX(HttpServletResponse response,List<Project> projects){
        
        // 文件名未定，类型为xlsx
        String fileName="";
    
        response.setHeader("content-disposition","attachment;fileName="+fileName);
        try(OutputStream out = response.getOutputStream()) {
            FileLoadUtil.generateXLSX(projects).write(out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
