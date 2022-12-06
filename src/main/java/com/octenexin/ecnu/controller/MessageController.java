package com.octenexin.ecnu.controller;

import com.octenexin.ecnu.pojo.Message;
import com.octenexin.ecnu.pojo.User;
import com.octenexin.ecnu.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Tofweod
 */
@RestController
public class MessageController {

	@Autowired
	MessageService messageService;
	
	@RequestMapping("/message")
	@ResponseBody
	public List<Message> getMessages(HttpSession session){

		User user=new User();
		String userId= (String) session.getAttribute("loginUserID");
		user.setUserId(userId);

		messageService.setUser(user);
		return messageService.getMessages();
	}
}
