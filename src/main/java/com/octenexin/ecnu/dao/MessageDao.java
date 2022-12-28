package com.octenexin.ecnu.dao;

import com.octenexin.ecnu.pojo.Message;
import com.octenexin.ecnu.pojo.User;

import java.util.List;

/**
 * @author Tofweod
 */
public interface MessageDao {
  /**
   * 获取该用户messages，分页10
   * @param user
   * @param page
   * @return
   */
  List<Message> getMessages(String uid,Integer page);


  /**
   * 根据用户id获取消息数量
   * */
  Integer getMsgCntByUser(String uid);

  Integer getMsgUnreadCntByUser(String uid);
  
  /**
   * 向用户发送message
   * @param user
   * @param message
   */
  int sendMessage(User user,Message message);

  int addMessage(Message message);
  /**
   * 设置消息已读
   * @param message
   */
  int setRead(Message message);
  
  /**
   * 删除单个message
   * @param message
   */
  int deleteScalar(Message message);
  
  /**
   * 删除所选messages
   * @param messages
   */
  int[] deleteMessages(List<Message> messages);
  
  /**
   * 清空该用户所有message
   * 该方法一般在注销用户时，通过session获取到用户对应messageService来执行
   */
  int clearAll(String uid);
  
  
}
