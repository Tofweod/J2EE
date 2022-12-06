package com.octenexin.ecnu.dao;

import com.octenexin.ecnu.pojo.ProjectClass;
import com.octenexin.ecnu.pojo.User;

import java.util.List;

public interface ProjectClassDao {

    //TODO
    int add(ProjectClass projectClass);

    //TODO
    int update(ProjectClass projectClass);

    //TODO
    int delete(ProjectClass projectClass);

    int count();

    List<ProjectClass> queryAll();
}
