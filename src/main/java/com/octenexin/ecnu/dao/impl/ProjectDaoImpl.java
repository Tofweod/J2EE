package com.octenexin.ecnu.dao.impl;

import com.octenexin.ecnu.dao.PaperDao;
import com.octenexin.ecnu.dao.ProjectDao;
import com.octenexin.ecnu.pojo.Paper;
import com.octenexin.ecnu.pojo.Project;
import com.octenexin.ecnu.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class ProjectDaoImpl implements ProjectDao {


    @Resource
    PaperDao paperDao;
    
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    JdbcTemplate template;

    @Override
    public int addProject(Project project) {
        String sql="INSERT into projects ("+
                "project_name," +
                "project_charge_person_id," +
                "project_other_people_info," +
                "project_funds_up," +
                "project_about,"+
                "project_paper_id,"+
                "project_class_id," +
                "project_state_id," +
                "project_prestate_id," +
                "project_start_time," +
                "project_end_time) VALUES(?,?,?,?,?,?,?,?,?,?,?);";
        return template.update(sql,
                project.getProjectName(),
                project.getProjectChargePersonId(),
                project.getProjectOtherPeopleInfo(),
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
        String sql="UPDATE projects SET " +
                "project_name=?," +
                "project_other_people_info=?," +
                "project_funds_up=?," +
                "project_about=? where project_id=?";
        return template.update(sql,
                project.getProjectName(),
                project.getProjectOtherPeopleInfo(),
                project.getProjectFundsUp(),
                project.getProjectAbout(),
                project.getProjectId());
    }

    @Override
    public int adminUpdate(Project project) {
        String sql="UPDATE projects SET " +
                "project_name=?," +
                "project_charge_person_id=?," +
                "project_other_people_info=?," +
                "project_funds_up=?," +
                "project_about=?, " +
                "project_class_id=?," +
                "project_start_time=?," +
                "project_end_time=? " +
                "where project_id=?;";
        return template.update(sql,
                project.getProjectName(),
                project.getProjectChargePersonId(),
                project.getProjectOtherPeopleInfo(),
                project.getProjectFundsUp(),
                project.getProjectAbout(),
                project.getProjectClassId(),
                project.getProjectStartTime(),
                project.getProjectEndTime(),
                project.getProjectId());
    }

    @Override
    public int updatePaper(Project project){
        String sql="UPDATE projects SET " +
                "project_paper_id=? where project_id=?";
        return template.update(sql,
                project.getProjectPaperId(),
                project.getProjectId()
                );
    }

    @Override
    public int delete(Project project) {
        String sql="delete from projects where project_id=?";

        return template.update(sql,project.getProjectId());
    }

    @Override
    public int count(Project project) {
        String sql="select count(*) from projects";
        return template.queryForObject(sql, Integer.class);
    }

    @Override
    public int countByUser(Project project){
        String sql="select count(*) from projects where project_charge_person_id=?;";
        return template.queryForObject(sql, Integer.class,project.getProjectChargePersonId());
    }

    @Override
    public int countByFinish(Project project){
        String sql="select count(*) from projects where project_state_id=6;";
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
   public List<Project> getProjectByStu(String chargePersonId){
       String sql="select * from projects where project_charge_person_id=?;";
       return template.query(sql, new BeanPropertyRowMapper<>(Project.class),chargePersonId);
   }

   @Override
   public Project getProjectById(Integer id){
       String sql="select * from projects where project_id=?;";
       return template.queryForObject(sql, new BeanPropertyRowMapper<>(Project.class),id);
   }

    @Override
    public List<Project> getProjectByStu5(String chargePersonId){
        String sql="select * from projects where project_charge_person_id=? order by project_id desc limit 0,5;";
        return template.query(sql, new BeanPropertyRowMapper<>(Project.class),chargePersonId);
    }

    @Override
    public List<Project> getProjectByPaper(Integer paperId){
        String sql="select * from projects where project_paper_id=?;";
        return template.query(sql, new BeanPropertyRowMapper<>(Project.class),paperId);
    }
    
    @Override
    public void batchDeleteProject(List<Project> projects) {
        // todo:删除papers中对应论文以及文件
    }
    
    @Override
    public void batchUpdateProject(List<Project> projects) {
        String sql="UPDATE projects SET " +
                "project_id = ?,"+
                "project_name=?," +
                "project_other_people_info=?," +
                "project_funds_up=?," +
                "project_about=?," +
                "project_paper_id=?,"+
                "project_class_id=?,"+
                "project_state_id=?,"+
                "project_prestate_id=?,"+
                "project_start_time=?,"+
                "project_end_time=?"+
                "where project_id=?";
        template.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1,projects.get(i).getProjectId());
                ps.setString(2,projects.get(i).getProjectName());
                ps.setString(3,projects.get(i).getProjectOtherPeopleInfo());
                ps.setInt(4,projects.get(i).getProjectFundsUp());
                ps.setString(5,projects.get(i).getProjectAbout());
                ps.setInt(6,projects.get(i).getProjectClassId());
                ps.setInt(7,projects.get(i).getProjectStateId());
                ps.setInt(8,projects.get(i).getProjectPrestateId());
                ps.setDate(10,new java.sql.Date(projects.get(i).getProjectStartTime().getTime()));
                ps.setDate(11,new java.sql.Date(projects.get(i).getProjectEndTime().getTime()));
                ps.setInt(12,projects.get(i).getProjectId());
            }
    
            @Override
            public int getBatchSize() {
                return projects.size();
            }
        });
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

    @Override
    public List<Project> autoQuery(String sql){
        return template.query(sql,new BeanPropertyRowMapper<>(Project.class));
    }
}
