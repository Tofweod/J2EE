package com.octenexin.ecnu.service;

import com.octenexin.ecnu.pojo.User;

import java.util.List;

public interface UserService {

    /**
     * 新增用户数据
     *
     * @param user
     * @return
     */
    public int addUser(User user);
    /**
     * 更新用户数据
     *
     * @param user
     * @return
     */
    public int updateUser(User user);
    /**
     * 删除用户数据
     *
     * @param user
     * @return
     */
    public int deleteUser(User user);
    /**
     * 统计用户数量
     *
     * @param user
     * @return
     */
    public int countUser(User user);
    /**
     * 查询用户数据
     *
     * @param user
     * @return
     */
    public List<User> getUserList(User user);
    /**
     * 查询单个用户信息
     *
     * @param user
     * @return
     */
    public User getUser(User user);
    /**
     * 批量添加用户
     *
     * @param batchArgs
     */
    public void batchAddUser(List<Object[]> batchArgs);
}
