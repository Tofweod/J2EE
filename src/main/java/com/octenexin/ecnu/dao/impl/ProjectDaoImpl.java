package com.octenexin.ecnu.dao.impl;

import com.octenexin.ecnu.dao.ProjectDao;
import com.octenexin.ecnu.pojo.Project;
import com.octenexin.ecnu.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProjectDaoImpl implements ProjectDao {

    @Autowired
    JdbcTemplate template;

    @Override
    public int addProject(Project project) {
        String sql="INSERT into projects (" +
                "project_id," +
                "project_name," +
                "project_charge_person_id," +
                "project_other_people_info," +
                "project_funds_low," +
                "project_funds_up," +
                "project_about,"+
                "project_paper_id,"+
                "project_class_id," +
                "project_state_id," +
                "project_prestate_id," +
                "project_start_time," +
                "project_end_time) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?);";
        return template.update(sql,
                project.getProjectId(),
                project.getProjectName(),
                project.getProjectChargePersonId(),
                project.getProjectOtherPeopleInfo(),
                project.getProjectFundsLow(),
                project.getProjectFundsUp(),
                project.getProjectAbout(),
                project.getProjectPaperId(),
                project.getProjectClassId(),
                project.getProjectStateId(),
                project.getProjectPrestateId(),
                project.getProjectStartTime(),
                project.getProjectEndTime());
    }

    @Override
    public int update(Project project) {
        return 0;
    }

    @Override
    public int delete(Project project) {
        return 0;
    }

    @Override
    public int count(Project project) {
        String sql="select count(*) from projects";
        return template.queryForObject(sql, Integer.class);
    }

    @Override
    public List<Project> getList(Project project) {
        String sql="select * from projects where project_charge_person_id=?;";
        return template.query(sql, new BeanPropertyRowMapper<>(Project.class),project.getProjectChargePersonId());
    }

    @Override
    public Project getProject(Project project) {
        String sql="select * from projects where project_id=?;";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(Project.class),project.getProjectId());
    }

    @Override
    public void batchAddProject(List<Object[]> batchArgs) {

    }
}
