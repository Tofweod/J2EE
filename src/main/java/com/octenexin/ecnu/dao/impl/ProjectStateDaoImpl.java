package com.octenexin.ecnu.dao.impl;

import com.octenexin.ecnu.dao.ProjectStateDao;
import com.octenexin.ecnu.pojo.ProjectState;
import com.octenexin.ecnu.pojo.ProjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class ProjectStateDaoImpl implements ProjectStateDao {
    @Autowired
    JdbcTemplate template;

    @Override
    public ProjectState query(ProjectState state) {
        String sql="select * from project_states where project_state_id=?;";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(ProjectState.class),state.getProjectStateId());
    }
}
