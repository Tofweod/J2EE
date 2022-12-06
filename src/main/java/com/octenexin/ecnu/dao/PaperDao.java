package com.octenexin.ecnu.dao;

import com.octenexin.ecnu.pojo.Paper;

import java.util.List;

/**
 * @author Tofweod
 */
public interface PaperDao {
	
	int addPaper(Paper paper);
	
	/**
	 * 修改paper内容
	 */
	int updatePaper(Paper paper);
	
	/**
	 * 仅仅修改paper状态
	 */
	int updatePaperState(Paper paper);
	
	int deletePaper(Paper paper);
	
	int countPapers(Paper paper);
	
	/**
	 * 通过papers列表获取论文列表
	 *
	 * @return paper list
	 */
	List<Paper> getPapers(List<Paper> papers);
	
	/**
	 * 获取论文列表
	 * 用于实现分页显示
	 * 从start+1个数据开始查询nums条数据
	 */
	List<Paper> getPaperPage(int start, int nums);
	
	/**
	 * 获取论文
	 */
	Paper getPaper(Paper paper);
	
	
}