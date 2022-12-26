package com.octenexin.ecnu.util;

import com.octenexin.ecnu.pojo.PaperState;
import com.octenexin.ecnu.pojo.ProjectClass;
import com.octenexin.ecnu.pojo.ProjectState;
import com.octenexin.ecnu.pojo.ProjectType;

import java.util.Map;

/**
 * @author Tofweod
 * 管理外表与值对应的hashmap
 * 所有根据id获取值的操作均存放其中
 */
public class IdManageUtils{
	
	// 所有的map均不允许add方法，唯一一次设置map位于StartController中
	public static Map<Integer, ProjectClass> projectClassMap;
	public static Map<Integer, ProjectState> projectStateMap;
	public static Map<Integer, ProjectType> projectTypeMap;

	public static Map<Integer, PaperState> paperStateMap;

	public static Map<Integer, String> paperStateColorMap;
	
	public static void setClassMap(Map<Integer,ProjectClass> map){
		if(projectClassMap != null){
			//throw new RuntimeException("map has been set!");
			System.out.println("[Warning: map has been set!]");
			return;
		}
		projectClassMap = map;
	}
	
	public static void setStateMap(Map<Integer,ProjectState> map){
		if(projectStateMap != null){
			//throw new RuntimeException("map has been set!");
			System.out.println("[Warning: map has been set!]");
			return;
		}
		projectStateMap = map;
	}
	
	public static void setTypeMap(Map<Integer, ProjectType> map){
		if(projectTypeMap != null){
			//throw new RuntimeException("map has been set!");
			System.out.println("[Warning: map has been set!]");
			return;
		}
		projectTypeMap = map;
	}

	public static void setPaperStateMap(Map<Integer, PaperState> map){
		if(paperStateMap != null){
			//throw new RuntimeException("map has been set!");
			System.out.println("[Warning: map has been set!]");
			return;
		}
		paperStateMap = map;
	}

	public static void setPaperStateColorMap(Map<Integer, String> map){
		if(paperStateColorMap != null){
			//throw new RuntimeException("map has been set!");
			System.out.println("[Warning: map has been set!]");
			return;
		}
		paperStateColorMap = map;
	}

	public static ProjectClass getProjectClass(int id){
		ProjectClass result = projectClassMap.get(id);
		if(result == null){
			throw new RuntimeException("id error!");
		}
		return result;
	}
	
	public static ProjectState getProjectState(int id){
		ProjectState result = projectStateMap.get(id);
		if(result == null){
			throw new RuntimeException("id error!");
		}
		return result;
	}
	
	public static ProjectType getProjectType(int id){
		ProjectType result = projectTypeMap.get(id);
		if(result == null){
			throw new RuntimeException("id error!");
		}
		return result;
	}
	
}
