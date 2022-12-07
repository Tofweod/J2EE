package com.octenexin.ecnu.util;

import com.octenexin.ecnu.pojo.ProjectClass;
import com.octenexin.ecnu.pojo.ProjectState;
import com.octenexin.ecnu.pojo.ProjectType;

import java.util.Map;

/**
 * @author Tofweod
 * 管理外表与值对应的hashmap
 * 所有根据id获取值的操作均存放其中
 */
public class IdManageUtils {
	
	// 所有的map均不允许add方法，唯一一次设置map位于StartController中
	private static Map<Integer, ProjectClass> projectClassMap;
	private static Map<Integer, ProjectState> projectStateMap;
	private static Map<Integer, ProjectType> projectTypeMap;
	
	public static void setClassMap(Map<Integer,ProjectClass> map){
		if(projectClassMap != null){
			throw new RuntimeException("map has been set!");
		}
		projectClassMap = map;
	}
	
	public static void setStateMap(Map<Integer,ProjectState> map){
		if(projectStateMap != null){
			throw new RuntimeException("map has been set!");
		}
		projectStateMap = map;
	}
	
	public static void setTypeMap(Map<Integer, ProjectType> map) {
		if(projectTypeMap != null){
			throw new RuntimeException("map has been set!");
		}
		projectTypeMap = map;
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
