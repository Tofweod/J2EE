package com.octenexin.ecnu.controller;

import com.octenexin.ecnu.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author Tofweod
 * 启动设置，开启某些服务
 */
@Controller
public class StartController {
	@Autowired
	MessageService messageService;

	@RequestMapping("/")
	public String start(HttpSession session){
		// 添加邮件服务至会话
		if(session.getAttribute("messageService") == null)
			session.setAttribute("messageService",messageService);

		return "/login";
	}
}
