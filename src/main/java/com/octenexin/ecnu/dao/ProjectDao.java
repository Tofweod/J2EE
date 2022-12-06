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
     * 更新指定的项目信息
     *
     * @param project
     * @return
     */
    int update(Project project);
    /**
     * 删除指定的项目
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
