package com.octenexin.ecnu.dao.impl;

import com.octenexin.ecnu.dao.UserDao;
import com.octenexin.ecnu.pojo.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public int addUer(User user) {
        String sql = "INSERT into users (email,user_id,user_name,user_password,major,authority) VALUES(?,?,?,?,?,?);";
        return jdbcTemplate.update(sql, user.getEmail(),user.getUserId(),user.getUserName(),user.getUserPassword(),user.getMajor(),user.getAuthority());
    }
    @Override
    public int update(User user) {
        String sql = "UPDATE users SET email=?,user_id=?,user_name=?,user_password=?,major=?,authority=? WHERE email=?;";
        return jdbcTemplate.update(sql, user.getEmail(),user.getUserId(),user.getUserName(),user.getUserPassword(),user.getMajor(),user.getAuthority(),user.getEmail());
    }
    @Override
    public int delete(User user) {
        String sql = "DELETE FROM users where email=?;";
        return jdbcTemplate.update(sql, user.getEmail());
    }
    @Override
    public int count(User user) {
        String sql = "SELECT COUNT(*) FROM users;";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
    @Override
    public List<User> getList(User user) {
        String sql = "SELECT * FROM users;";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }
    @Override
    public User getUser(User user) {
        String sql = "SELECT * FROM users where email=?;";

        try{
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), user.getEmail());
        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    @Override
    public User getUserById(User user){
        String sql = "SELECT * FROM users where user_id=?;";

        try{
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), user.getUserId());
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public void batchAddUser(List<Object[]> batchArgs) {
        String sql = "INSERT into users (email,user_id,user_name,user_password,major,authority) VALUES(?,?,?,?,?,?);";
        jdbcTemplate.batchUpdate(sql, batchArgs);
    }
}