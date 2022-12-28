package com.octenexin.ecnu.service.impl;

import com.octenexin.ecnu.dao.PaperDao;
import com.octenexin.ecnu.dao.ProjectClassDao;
import com.octenexin.ecnu.dao.ProjectDao;
import com.octenexin.ecnu.dao.ProjectTypeDao;
import com.octenexin.ecnu.pojo.Paper;
import com.octenexin.ecnu.pojo.Project;
import com.octenexin.ecnu.service.PaperService;
import com.octenexin.ecnu.service.ProjectService;
import com.octenexin.ecnu.util.FileSaveUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectDao projectDao;

    @Autowired
    PaperDao paperDao;

    @Autowired
    PaperService paperService;


    public void deleteProject(Integer id){
        Project project=new Project();
        project.setProjectId(id);

        Project realProject=projectDao.getProject(project);

        //paper, file
        if(realProject.getProjectPaperId()!=null){
            paperService.deletePaper(realProject.getProjectPaperId());
        }

        projectDao.delete(project);
    }
}
