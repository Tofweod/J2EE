package com.octenexin.ecnu.service.impl;


import com.octenexin.ecnu.dao.ProjectDao;
import com.octenexin.ecnu.dao.UserDao;
import com.octenexin.ecnu.pojo.Paper;
import com.octenexin.ecnu.pojo.Project;
import com.octenexin.ecnu.pojo.User;
import com.octenexin.ecnu.service.MessageService;
import com.octenexin.ecnu.service.ProjectService;
import com.octenexin.ecnu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectDao projectDao;

    @Override
    public int addUser(User user) {
        return userDao.addUer(user);
    }
    @Override
    public int updateUser(User user) {
        return userDao.update(user);
    }
    @Override
    public int deleteUser(String id) {

        //delete projects, paper, file
        List<Project> projects=projectDao.getProjectByStu(id);
        for (Project p : projects) {
            projectService.deleteProject(p.getProjectId());
        }

        //delete user
        User user=new User();
        user.setUserId(id);

        return userDao.delete(user);

    }
    @Override
    public int countUser(User user) {
        return userDao.count(user);
    }
    @Override
    public List<User> getUserList(User user) {
        return userDao.getList(user);
    }
    @Override
    public User getUser(User user) {
        return userDao.getUser(user);
    }
    @Override
    public void batchAddUser(List<Object[]> batchArgs) {
        userDao.batchAddUser(batchArgs);
    }

}
