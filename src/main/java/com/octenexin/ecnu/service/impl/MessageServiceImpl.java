package com.octenexin.ecnu.service.impl;

import com.octenexin.ecnu.dao.MessageDao;
import com.octenexin.ecnu.pojo.Message;
import com.octenexin.ecnu.pojo.User;
import com.octenexin.ecnu.service.MessageService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Tofweod
 */
@Service("messageService")
@SessionScope
public class MessageServiceImpl implements MessageService {
	
	private User user;
	
	@Resource
	MessageDao messageDao;
	
	
	@Override
	public void sendMessage(Message content) {
		messageDao.sendMessage(user,content);
	}
	
	@Override
	public List<Message> getMessages() {
		return messageDao.getMessages(user);
	}
	
	@Override
	public synchronized void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public int setRead(Message message) {
		return messageDao.setRead(message);
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
