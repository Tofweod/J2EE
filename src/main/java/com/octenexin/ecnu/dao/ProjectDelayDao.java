package com.octenexin.ecnu.dao;


import com.octenexin.ecnu.pojo.Project;
import com.octenexin.ecnu.pojo.ProjectDelay;

import java.util.List;

public interface ProjectDelayDao {

    int addDelay(ProjectDelay delay);

    int handleDelay(Integer projectDelayId,Integer oper);

    List<ProjectDelay> getDelays(Integer page);

    List<ProjectDelay> autoQuery(String sql);

}
