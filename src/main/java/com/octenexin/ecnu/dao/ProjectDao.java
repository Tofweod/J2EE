package com.octenexin.ecnu.dao;

import com.octenexin.ecnu.pojo.Project;
import com.octenexin.ecnu.pojo.User;

import java.util.List;

public interface ProjectDao {
    /**
     * 新增一个项目
     *
     * @param project
     * @return
     */
    int addProject(Project project);
    /**
     * 根据id更新指定的项目信息的一些位
     * project_name
     * project_other_people_info
     * project_funds_up
     * project_about
     *
     * @param project
     * @return
     */
    int update(Project project);
    /**
     * 根据id删除指定的项目
     *
     * @param project
     * @return
     */
    int delete(Project project);
    /**
     * 统计项目个数
     *
     * @param project
     * @return
     */
    int count(Project project);

    /**
     * 根据负责人id统计项目个数
     *
     * @param project
     * @return
     */
    int countByUser(Project project);

    /**
     * 统计已完成项目个数
     *
     * @param project
     * @return
     */
    int countByFinish(Project project);

    /**
     * 根据负责人id查询单个用户项目列表
     *
     * @param project
     * @return
     */
    List<Project> getList(Project project);
    /**
     * 根据主键查询单个项目信息
     *
     * @param project
     * @return
     */
    Project getProject(Project project);
    /**
     * 批量增加项目
     *
     * @param batchArgs
     */
    void batchAddProject(List<Object[]> batchArgs);
}
