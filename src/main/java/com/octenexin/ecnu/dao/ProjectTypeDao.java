package com.octenexin.ecnu.dao;

import com.octenexin.ecnu.pojo.Project;
import com.octenexin.ecnu.pojo.ProjectType;

import java.util.List;

public interface ProjectTypeDao {

    /**
     * 无条件查询项目类型列表
     */
    List<ProjectType> getList(Integer page);


    /**
     * 根据ProjectClass查询项目类型列表
     */
    List<ProjectType> getListByClass(ProjectType type);


    /**
     * 根据主键查询单个项目类型
     */
    ProjectType query(ProjectType type);


    Integer count();


    Integer delete(Integer projectTypeId);

    Integer update(ProjectType projectType);
}
