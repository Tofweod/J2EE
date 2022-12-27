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
	void sendMessage(Message content);


	List<Message> getMessages(String uid, Integer page);

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
