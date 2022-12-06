package com.octenexin.ecnu.service;

import com.octenexin.ecnu.pojo.Message;
import com.octenexin.ecnu.pojo.User;

import java.util.List;

/**
 * @author Tofweod
 */
public interface MessageService {
	
	/**
	 * 发送消息
	 */
	void sendMessage(String content);
	
	/**
	 * 获取所有消息
	 */
	List<Message> getMessages();
	
	
	/**
	 * 设置该服务的user
	 * 建议每次操作消息前的因考虑service内的user
	 * @param user
	 */
	void setUser(User user);
	
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
	int clearAll();
}
