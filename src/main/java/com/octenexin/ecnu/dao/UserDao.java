package com.octenexin.ecnu.dao;

import com.octenexin.ecnu.pojo.User;

import java.util.List;

public interface UserDao {
    /**
     * 新增一条用户
     *
     * @param user
     * @return
     */
    int addUer(User user);
    /**
     * 更新指定的用户信息
     *
     * @param user
     * @return
     */
    int update(User user);
    /**
     * 删除指定的用户信息
     *
     * @param user
     * @return
     */
    int delete(User user);
    /**
     * 统计用户个数
     *
     * @param user
     * @return
     */
    int count(User user);
    /**
     * 查询用户列表
     *
     * @param user
     * @return
     */
    List<User> getList(User user);
    /**
     * 查询单个用户信息
     *
     * @param user
     * @return
     */
    User getUser(User user);


    /**
     * 通过学号查询单个用户信息
     *
     * @param user
     * @return
     */
    User getUserById(User user);

    /**
     * 批量增加用户
     *
     * @param batchArgs
     */
    void batchAddUser(List<Object[]> batchArgs);
}
