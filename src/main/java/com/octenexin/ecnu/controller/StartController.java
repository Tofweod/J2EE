package com.octenexin.ecnu.controller;

import com.octenexin.ecnu.pojo.PaperState;
import com.octenexin.ecnu.pojo.ProjectClass;
import com.octenexin.ecnu.pojo.ProjectState;
import com.octenexin.ecnu.pojo.ProjectType;
import com.octenexin.ecnu.service.MessageService;
import com.octenexin.ecnu.util.IdManageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tofweod
 * 启动设置，开启某些服务
 */
@Controller
public class StartController {
	@Autowired
	MessageService messageService;
	
	@Resource
	private JdbcTemplate jdbcTemplate;

	@RequestMapping("/")
	public String start(HttpSession session){
		// 添加消息服务至会话
		if(session.getAttribute("messageService") == null)
			session.setAttribute("messageService",messageService);
		
		Map<Integer, ProjectClass> classMap = new HashMap<>();
		String sql1 = "select * from project_classes;";
		List<ProjectClass> classes = jdbcTemplate.query(sql1, new BeanPropertyRowMapper<>(ProjectClass.class));
		for (ProjectClass aClass : classes) {
			classMap.put(aClass.getProjectClassId(),aClass);
		}

		try{
			IdManageUtils.setClassMap(classMap);
		}catch (Exception e){
			e.printStackTrace();
		}

		
		Map<Integer, ProjectState> stateMap = new HashMap<>();
		String sql2 = "select * from project_states;";
		List<ProjectState> states = jdbcTemplate.query(sql2, new BeanPropertyRowMapper<>(ProjectState.class));
		for (ProjectState state : states) {
			stateMap.put(state.getProjectStateId(),state);
		}
		try {
			IdManageUtils.setStateMap(stateMap);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		Map<Integer, ProjectType> typeMap = new HashMap<>();
		String sql3 = "select * from project_types;";
		List<ProjectType> types = jdbcTemplate.query(sql3, new BeanPropertyRowMapper<>(ProjectType.class));
		for (ProjectType type : types) {
			typeMap.put(type.getProjectTypeId(),type);
		}
		try {
			IdManageUtils.setTypeMap(typeMap);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		Map<Integer, PaperState> paperStateMap = new HashMap<>();
		String sql4 = "select * from paper_states;";
		List<PaperState> ps = jdbcTemplate.query(sql4, new BeanPropertyRowMapper<>(PaperState.class));
		for (PaperState p : ps) {
			paperStateMap.put(p.getPaperStateId(),p);
		}
		try {
			IdManageUtils.setPaperStateMap(paperStateMap);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}


		Map<Integer, String> paperStateColorMap = new HashMap<>();
		paperStateColorMap.put(-1,"badge badge-danger mb-3");
		paperStateColorMap.put(-2,"badge badge-warning mb-3");
		paperStateColorMap.put(0,"badge badge-primary mb-3");
		paperStateColorMap.put(1,"badge badge-success mb-3");
		paperStateColorMap.put(2,"badge badge-info mb-3");
		try {
			IdManageUtils.setPaperStateColorMap(paperStateColorMap);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		Map<Integer,String> projectStateColorMap=new HashMap<>();
		projectStateColorMap.put(1,"badge badge-primary mb-3");
		projectStateColorMap.put(2,"badge mb-3 badge-info");
		projectStateColorMap.put(3,"badge mb-3 badge-danger");
		projectStateColorMap.put(4,"badge mb-3 badge-success");
		projectStateColorMap.put(5,"badge mb-3 badge-secondary");
		projectStateColorMap.put(6,"badge mb-3 badge-dark");
		try {
			IdManageUtils.setProjectStateColorMap(projectStateColorMap);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}



		return "/login";
	}
}
