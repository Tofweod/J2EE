package com.octenexin.ecnu.dao.impl;

import com.octenexin.ecnu.dao.ProjectTypeDao;
import com.octenexin.ecnu.pojo.ProjectType;
import com.octenexin.ecnu.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ProjectTypeDaoImpl implements ProjectTypeDao {

    @Autowired
    JdbcTemplate template;

    @Override
    public List<ProjectType> getList(String page) {
        String sql="select * from project_types limit"+page+",10;";
        return template.query(sql, new BeanPropertyRowMapper<>(ProjectType.class));
    }

    @Override
    public List<ProjectType> getListByClass(ProjectType type) {
        String sql="select * from project_types where project_class_id=?;";
        return template.query(sql, new BeanPropertyRowMapper<>(ProjectType.class),type.getProjectClassId());
    }

    @Override
    public ProjectType query(ProjectType type){
        String sql="select * from project_types where project_type_id=?;";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(ProjectType.class),type.getProjectTypeId());
    }

}
