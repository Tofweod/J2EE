package com.octenexin.ecnu.controller;


import com.octenexin.ecnu.dao.ProjectClassDao;
import com.octenexin.ecnu.dao.ProjectDao;
import com.octenexin.ecnu.dao.ProjectStateDao;
import com.octenexin.ecnu.dao.UserDao;
import com.octenexin.ecnu.pojo.Project;
import com.octenexin.ecnu.pojo.ProjectClass;
import com.octenexin.ecnu.pojo.ProjectState;
import com.octenexin.ecnu.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class StudentProjectPageController {

    @Autowired
    ProjectClassDao projectClassDao;

    @Autowired
    ProjectDao projectDao;

    @Autowired
    ProjectStateDao projectStateDao;

    @Autowired
    UserDao userDao;


    @RequestMapping("/student/add")
    public String toAddProject(Model model){

        List<ProjectClass> list=projectClassDao.queryAll();
        model.addAttribute("project_classes",list);

        return "/student/project/project-add";
    }

    @RequestMapping("/student/project/project-list")
    public String toProjectList(Model model, HttpSession session){

        Project p=new Project();
        p.setProjectChargePersonId((String) session.getAttribute("loginUserId"));

        System.out.println(session.getAttribute("loginUserId"));

        List<Project> list=projectDao.getList(p);
        model.addAttribute("projects",list);


        List<String> classNames=new ArrayList<>();
        List<ProjectState> states=new ArrayList<>();
        List<ArrayList<String>> members=new ArrayList<>();

        for(Project l: list){
            int sid=l.getProjectStateId();
            ProjectState state=new ProjectState();
            state.setProjectStateId(sid);
            ArrayList<String> member=new ArrayList<>(Arrays.asList(l.getProjectOtherPeopleInfo().split(";")));


            String className;

            switch (sid){
                case 1:{
                    className="badge align-text-bottom ml-1 badge-primary";
                }break;
                case 2:{
                    className="badge align-text-bottom ml-1 badge-info";
                }break;
                case 3:{
                    className="badge align-text-bottom ml-1 badge-danger";
                }break;
                case 4:{
                    className="badge align-text-bottom ml-1 badge-success";
                }break;
                case 5:{
                    className="badge align-text-bottom ml-1 badge-secondary";
                }break;
                default:{
                    className="badge align-text-bottom ml-1 badge-dark";
                }
            }

            classNames.add(className);
            states.add(projectStateDao.query(state));
            members.add(member);
        }

        model.addAttribute("states",states);
        model.addAttribute("classNames",classNames);
        model.addAttribute("members",members);


        return "/student/project/project-list";
    }

    @RequestMapping("/student/project/details")
    public String toDetail(@RequestParam Integer id, Model model){
        Project project=new Project();
        project.setProjectId(id);
        Project realProject=projectDao.getProject(project);

        User user=new User();
        user.setUserId(realProject.getProjectChargePersonId());
        User realUser=userDao.getUserById(user);

        System.out.println(realUser);

        ArrayList<String> member=new ArrayList<>(Arrays.asList(realProject.getProjectOtherPeopleInfo().split(";")));

        int sid=realProject.getProjectStateId();
        ProjectState state=new ProjectState();
        state.setProjectStateId(sid);
        ProjectState realState=projectStateDao.query(state);

        String className;

        switch (sid){
            case 1:{
                className="badge badge-primary mb-3";
            }break;
            case 2:{
                className="badge mb-3 badge-info";
            }break;
            case 3:{
                className="badge mb-3 badge-danger";
            }break;
            case 4:{
                className="badge mb-3 badge-success";
            }break;
            case 5:{
                className="badge mb-3 badge-secondary";
            }break;
            default:{
                className="badge mb-3 badge-dark";
            }
        }

        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");



        model.addAttribute("project",realProject);
        model.addAttribute("chargePerson",realUser);
        model.addAttribute("member",member);
        model.addAttribute("state",realState);
        model.addAttribute("className",className);
        model.addAttribute("startTime",formatter.format(realProject.getProjectStartTime()));
        model.addAttribute("endTime",formatter.format(realProject.getProjectEndTime()));

        return "/student/project/project-details";
    }

    @RequestMapping("/student/project/update")
    public String toUpdate(@RequestParam Integer id, Model model){
        Project project=new Project();
        project.setProjectId(id);
        Project realProject=projectDao.getProject(project);

        ProjectState state=new ProjectState();
        state.setProjectStateId(realProject.getProjectStateId());
        ProjectState realState=projectStateDao.query(state);


        SimpleDateFormat formatter=new SimpleDateFormat("MM/dd/yyyy");



        model.addAttribute("project",realProject);
        model.addAttribute("state",realState);
        model.addAttribute("startTime",formatter.format(realProject.getProjectStartTime()));
        model.addAttribute("endTime",formatter.format(realProject.getProjectEndTime()));

        return "/student/project/project-update";
    }


}
