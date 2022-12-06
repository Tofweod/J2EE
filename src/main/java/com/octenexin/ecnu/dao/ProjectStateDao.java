package com.octenexin.ecnu.dao;

import com.octenexin.ecnu.pojo.ProjectState;

public interface ProjectStateDao {

    /**
     * 根据状态id查询状态名
     *
     * @param state
     * @return
     */
    ProjectState query(ProjectState state);
}
