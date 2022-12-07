package com.octenexin.ecnu.dao.impl;

import com.octenexin.ecnu.dao.ProjectDao;
import com.octenexin.ecnu.pojo.Paper;
import com.octenexin.ecnu.pojo.Project;
import com.octenexin.ecnu.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public void batchAddProject(List<Paper> batchArgs) {
    
    }
    
    @Override
    public void batchDeleteProject(List<Paper> papers) {
    
    }
    
    @Override
    public void batchUpdateProject(List<Paper> papers) {
    
    }
    
    @Override
    public List<Project> filteringQuery(Date startTime, Date endTime,Integer fundLow, Integer fundUp, Boolean hasPaper,Integer classId, Integer stateId) {
        Object[] params = { startTime,startTime,
                            endTime,endTime,
                            fundLow,fundLow,
                            fundUp,fundUp,
                            hasPaper,
                            classId,classId,
                            stateId,stateId,
                          };
        
        String sql = "SELECT * FROM projects WHERE\n" +
                "project_start_time >= (CASE WHEN ? IS NULL THEN project_start_time ELSE ? END) AND\n" +
                "project_end_time <= (CASE WHEN ? IS NULL THEN project_end_time ELSE ? END) AND\n" +
                "project_funds_up >= (CASE WHEN ? IS NULL THEN project_funds_up ELSE ? END) AND\n" +
                "project_funds_up <= (CASE WHEN ? IS NULL THEN project_funds_up ELSE ? END) AND\n" +
                "(CASE ? WHEN TRUE THEN project_paper_id IS NOT NULL WHEN FALSE THEN project_paper_id IS NULL ELSE project_paper_id IS NULL OR project_paper_id IS NOT NULL END) AND\n" +
                "project_class_id = (CASE WHEN ? IS NULL THEN project_class_id ELSE ? END) AND\n" +
                "project_state_id = (CASE WHEN ? IS NULL THEN project_state_id ELSE ? END)";
        return template.query(sql,new BeanPropertyRowMapper<>(Project.class),params);
    }
}
