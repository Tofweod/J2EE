package com.octenexin.ecnu.controller;

import com.octenexin.ecnu.pojo.Message;
import com.octenexin.ecnu.service.MessageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Tofweod
 */
@RestController
public class MessageController {
	
	@RequestMapping("student/message")
	@ResponseBody
	public List<Message> showMessages(HttpSession session){
		MessageService messageService = (MessageService) session.getAttribute("messageService");
		return messageService.getMessages();
	}
}
