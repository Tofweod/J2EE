package com.octenexin.ecnu.dao;

import com.octenexin.ecnu.pojo.Paper;
import com.octenexin.ecnu.pojo.Project;
import com.octenexin.ecnu.pojo.User;

import java.util.Date;
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

    int adminUpdate(Project project);


    int updatePaper(Project project);

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
     * 根据负责人id查询项目
     * */
    List<Project> getProjectByStu(String chargePersonId);

    List<Project> getProjectByPaper(String paperId);

    /**
     * 批量删除项目
     */
    void batchDeleteProject(List<Project> projects);
    
    /**
     * 批量更新项目
     */
    void batchUpdateProject(List<Project> projects);
    
    /**
     * 指定条件筛选满足所选条件的projects,包括起止时间，经费范围，类型，状态
     * 如果不需要筛选某状态则置null
     * e.g. 查询经费大于2000的所有不含论文项目 filteringQuery(null,null,2000,null,null,false,null,null);
     * @param startTime 项目起始时间
     * @param endTime 项目结束时间
     * @param fundLow 经费下限
     * @param fundUp 经费上限
     * @param hasPaper 是否有论文
     * @param classId 类型id
     * @param stateId 状态id
     */
    List<Project> filteringQuery(Date startTime, Date endTime,
                                 Integer fundLow,Integer fundUp,Boolean hasPaper,
                                 Integer classId,Integer stateId);

    /**
     * query all.
     * */
    List<Project> autoQuery(String sql);
    
}
