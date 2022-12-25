package com.octenexin.ecnu.dao.impl;

import com.octenexin.ecnu.dao.ProjectDelayDao;
import com.octenexin.ecnu.pojo.Project;
import com.octenexin.ecnu.pojo.ProjectDelay;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ProjectDelayDaoImpl implements ProjectDelayDao {

    @Autowired
    JdbcTemplate template;

    @Override
    public int addDelay(ProjectDelay delay) {
        String sql="insert into project_delays(project_id,project_old_end_time,project_new_end_time,projectDelayReason,project_delay_state) values(?,?,?,0);";
        return template.update(sql,delay.getProjectId(),delay.getProjectOldEndTime(),delay.getProjectDelayReason(),delay.getProjectNewEndTime());
    }

    @Override
    public int handleDelay(Integer projectDelayId, Integer oper) {
        String sql="update project_delays set project_delay_state = ? where project_delay_id = ?;";

        return template.update(sql,oper,projectDelayId);
    }

    @Override
    public List<ProjectDelay> getDelays(Integer page){
        String sql="select * from project_delays limit "+page*10+",10;";
        return template.query(sql,new BeanPropertyRowMapper<>(ProjectDelay.class));
    }

    @Override
    public List<ProjectDelay> autoQuery(String sql) {
        return template.query(sql,new BeanPropertyRowMapper<>(ProjectDelay.class));
    }
}
