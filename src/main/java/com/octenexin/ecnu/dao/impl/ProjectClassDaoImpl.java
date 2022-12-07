package com.octenexin.ecnu.dao.impl;

import com.octenexin.ecnu.dao.ProjectClassDao;
import com.octenexin.ecnu.pojo.ProjectClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ProjectClassDaoImpl implements ProjectClassDao {

    @Autowired
    JdbcTemplate template;

    @Override
    public int add(ProjectClass projectClass) {
        return 0;
    }

    @Override
    public int update(ProjectClass projectClass) {
        return 0;
    }

    @Override
    public int delete(ProjectClass projectClass) {
        return 0;
    }

    @Override
    public int count() {
        String sql="select count(*) from project_classes";
        return template.queryForObject(sql, Integer.class);
    }

    @Override
    public List<ProjectClass> queryAll() {

        String sql="select * from project_classes;";
        List<ProjectClass> res=template.query(sql,new BeanPropertyRowMapper<>(ProjectClass.class));
        return res;
    }

    @Override
    public ProjectClass queryByid(ProjectClass projectClass) {

        String sql="select * from project_classes where project_class_id=?;";
        return template.queryForObject(sql,new BeanPropertyRowMapper<>(ProjectClass.class),projectClass.getProjectClassId());

    }
}
