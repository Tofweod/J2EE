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
    public List<ProjectType> getList(Integer page) {
        String sql="select * from project_types order by project_type_id desc limit ?,10;";
        return template.query(sql, new BeanPropertyRowMapper<>(ProjectType.class),page*10);
    }

    @Override
    public List<ProjectType> getListByClass(ProjectType type) {
        String sql="select * from project_types where project_class_id=? order by project_type_id desc;";
        return template.query(sql, new BeanPropertyRowMapper<>(ProjectType.class),type.getProjectClassId());
    }

    @Override
    public ProjectType query(ProjectType type){
        String sql="select * from project_types where project_type_id=?;";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(ProjectType.class),type.getProjectTypeId());
    }

    @Override
    public Integer count(){
        String sql="select count(*) from project_types;";
        return template.queryForObject(sql,Integer.class);
    }

    @Override
    public Integer delete(Integer projectTypeId){
        String sql="delete from project_types where project_type_id = ?;";
        return template.update(sql,projectTypeId);
    }

    @Override
    public Integer update(ProjectType projectType){
        String sql="update set project_types project_type_name=?,project_class_id=?,project_type_start_time=?,project_type_end_time=? where project_type_id=?;";

        return template.update(sql,projectType.getProjectTypeName(),projectType.getProjectClassId(),projectType.getProjectTypeStartTime(),projectType.getProjectTypeEndTime(),projectType.getProjectTypeId());
    }

}
