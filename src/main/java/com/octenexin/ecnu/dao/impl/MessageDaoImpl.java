package com.octenexin.ecnu.dao.impl;

import com.octenexin.ecnu.dao.MessageDao;
import com.octenexin.ecnu.pojo.Message;
import com.octenexin.ecnu.pojo.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.sql.Timestamp;


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
  public List<Message> getMessages(String uid,Integer page) {
    String sql = "select * from messages where message_user_id = ? limit ?,10;";
    return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Message.class),uid,page*10);
  }

  @Override
  public Integer getMsgCntByUser(String uid){
    String sql = "select count(*) from messages where message_user_id = ?";
    return jdbcTemplate.queryForObject(sql,Integer.class,uid);
  }

  @Override
  public Integer getMsgUnreadCntByUser(String uid){
    String sql = "select count(*) from messages where message_user_id = ? and message_hasread = 0";
    return jdbcTemplate.queryForObject(sql,Integer.class,uid);
  }

  @Override
  public int sendMessage(User user,Message message) {
    String sql = "INSERT INTO messages(message_topic,message_user_id,message_raw_data,message_time,message_hasread) VALUES(?,?,?,?,0)";
    return jdbcTemplate.update(sql,message.getMessageTopic(),user.getUserId(),message.getMessageRawData(),new Timestamp(System.currentTimeMillis()));
  }

  @Override
  public int addMessage(Message message){
    String sql = "INSERT INTO messages(message_topic,message_user_id,message_raw_data,message_time,message_hasread) VALUES(?,?,?,?,0)";
    return jdbcTemplate.update(sql,message.getMessageTopic(),message.getMessageUserId(),message.getMessageRawData(),new Timestamp(System.currentTimeMillis()));
  }
  
  /**
   * message的update只有修改可读状态，因此特殊化其方法
   * todo:描述message具体实现
   * @param message
   */
  @Override
  public int setRead(Message message) {
    String sql = "UPDATE messages SET message_hasread = 1 WHERE message_id = ?";
    return jdbcTemplate.update(sql,message.getMessageId());
  }
  
  @Override
  public int deleteScalar(Message message) {
    // 删除
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
  public int clearAll(String uid) {
    String sql = "DELETE FROM messages WHERE message_user_id = ?";
    return jdbcTemplate.update(sql,uid);
  }
}
