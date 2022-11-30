package com.octenexin.ecnu.service;

import com.octenexin.ecnu.pojo.Paper;

import java.util.List;

/**
 * @author Tofweod
 */
public interface PaperService {
	
	/*
		注意：在操作论文时，如果不是对文件进行操作(如添加，修改等），应将传入paper的paperRawdata属性设置为null，否则对象过大，严重侵占内存
	 */
	int addPaper(Paper paper);
	
	int updatePaper(Paper paper);
	
	int deletePaper(Paper paper);
	
	List<Paper> getPapers(List<Paper> papers);
	
	/**
	 * 分页显示
	 * 从start+1个数据开始查询nums条数据
	 * 只显示审核通过论文
	 */
	List<Paper> getPaperPage(int start,int nums);
	
	Paper getPaper(Paper paper);
	
	/**
	 * 该方法未实现，待扩展
	 */
	List<Paper> getPapersWithData(List<Paper> papers);
	
	Paper getPaperWithData(Paper paper);
	
	byte[] getPaperData(int paper_id);
	
	byte[] getPaperData(Paper paper);
}
