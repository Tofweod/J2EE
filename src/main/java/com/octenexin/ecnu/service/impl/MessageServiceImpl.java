package com.octenexin.ecnu.service.impl;

import com.octenexin.ecnu.dao.MessageDao;
import com.octenexin.ecnu.pojo.Message;
import com.octenexin.ecnu.pojo.User;
import com.octenexin.ecnu.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Tofweod
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {
	
	private User user;
	
	@Resource
	MessageDao messageDao;
	
	
	@Override
	public void sendMessage(String content) {
		messageDao.sendMessage(user,content);
	}
	
	@Override
	public List<Message> getMessages() {
		return messageDao.getMessages(user);
	}
	
	@Override
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public int readMessage(Message message) {
		return messageDao.readMessage(message);
	}
	
	@Override
	public int deleteScalar(Message message) {
		return messageDao.deleteScalar(message);
	}
	
	@Override
	public int[] deleteMessages(List<Message> messages) {
		return messageDao.deleteMessages(messages);
	}
	
	@Override
	public int clearAll() {
		return messageDao.clearAll(user);
	}
}