package com.octenexin.ecnu.dao.impl;

import com.octenexin.ecnu.dao.MessageDao;
import com.octenexin.ecnu.pojo.Message;
import com.octenexin.ecnu.pojo.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Tofweod
 */
@Repository
public class MessageDaoImpl implements MessageDao {
  
  @Resource
  private JdbcTemplate jdbcTemplate;
  @Resource
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  
  @Override
  public List<Message> getMessages(User user) {
    String sql = "select * from messages where message_user_id = ?";
    return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Message.class),user.getUserId());
  }
  
  @Override
  public int sendMessage(User user,String content) {
    String sql = "INSERT INTO messages(message_user_id,message_raw_data,message_time,message_hasread) VALUES(?,?,NULL,0)";
    return jdbcTemplate.update(sql,user.getUserId(),content);
  }
  
  /**
   * message的update只有修改可读状态，因此特殊化其方法
   * @param message
   */
  @Override
  public int readMessage(Message message) {
    String sql = "UPDATE messages SET message_hasread = 1 WHERE message_id = ?";
    return jdbcTemplate.update(sql,message.getMessageId());
  }
  
  @Override
  public int deleteScalar(Message message) {
    String sql = "DELETE FROM messages WHERE message_id = ?";
    return jdbcTemplate.update(sql,message.getMessageId());
  }
  
  @Override
  public int[] deleteMessages(List<Message> messages) {
    String sql = "DELETE FROM messages WHERE message_id = ?;";
    List<Object[]> messagesId = messages.stream().map(message -> new Object[]{message.getMessageId()}).collect(Collectors.toList());
    return jdbcTemplate.batchUpdate(sql,messagesId);
  }
  
  @Override
  public int clearAll(User user) {
    String sql = "DELETE FROM messages WHERE message_user_id = ?";
    return jdbcTemplate.update(sql,user.getUserId());
  }
}
