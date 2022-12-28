package com.octenexin.ecnu.dao.impl;

import com.octenexin.ecnu.dao.ProjectDelayDao;
import com.octenexin.ecnu.pojo.Project;
import com.octenexin.ecnu.pojo.ProjectDelay;
import com.octenexin.ecnu.pojo.User;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.security.SecureRandom;
import java.util.List;


@Repository
public class ProjectDelayDaoImpl implements ProjectDelayDao {

    @Autowired
    JdbcTemplate template;

    @Override
    public int addDelay(ProjectDelay delay) {
        String sql="insert into project_delays(project_id,project_old_end_time,project_new_end_time,project_delay_reason,project_delay_state) values(?,?,?,?,0);";
        return template.update(sql,delay.getProjectId(),delay.getProjectOldEndTime(),delay.getProjectNewEndTime(),delay.getProjectDelayReason());
    }

    @Override
    public int handleDelay(Integer projectDelayId, Integer oper) {
        String sql="update project_delays set project_delay_state = ? where project_delay_id = ?;";

        return template.update(sql,oper,projectDelayId);
    }

    @Override
    public int deleteDelay(Integer projectDelayId) {
        String sql="delete from project_delays where project_delay_id = ?;";

        return template.update(sql,projectDelayId);
    }

    @Override
    public ProjectDelay getDelayById(Integer projectDelayId){
        String sql="select * from project_delays where project_delay_id = ?;";

        return template.queryForObject(sql, new BeanPropertyRowMapper<>(ProjectDelay.class),projectDelayId);
    }

    @Override
    public Integer countAll(){
        String sql="select count(*) from project_delays;";
        return template.queryForObject(sql,Integer.class);
    }

    @Override
    public List<ProjectDelay> getDelays(Integer page){
        String sql="select * from project_delays order by project_delay_id desc limit "+page*10+",10;";
        return template.query(sql,new BeanPropertyRowMapper<>(ProjectDelay.class));
    }

    @Override
    public List<ProjectDelay> autoQuery(String sql) {
        return template.query(sql,new BeanPropertyRowMapper<>(ProjectDelay.class));
    }
}
