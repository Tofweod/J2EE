package com.octenexin.ecnu.dao;

import com.octenexin.ecnu.pojo.Paper;

import java.util.List;

/**
 * @author Tofweod
 * papers表中rawdata数据量过大，因此设计接口时应考虑是否须要该字段的情况
 * 即获取paper对象的方法都应该指明是否获取rawdata并尊享该规定
 * 否则内存占用严重超标
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
	 * 通过papers列表获取论文列表(无rawdata)
	 * @return paper list
	 */
	List<Paper> getPapers(List<Paper> papers);
	
	/**
	 * 获取论文列表(无rawdata)
	 * 用于实现分页显示
	 * 从start+1个数据开始查询nums条数据
	 */
	List<Paper> getPaperPage(int start,int nums);
	
	/**
	 * 获取论文(无rawdata)
	 */
	Paper getPaper(Paper paper);
	
	/**
	 * 获取论文列表(附rawdata)
	 * @param papers
	 * @return
	 */
	List<Paper> getPapersWithData(List<Paper> papers);
	
	/**
	 * 获取论文(附rawdata)
	 * 一般用于论文下载
	 */
	Paper getPaperWithData(Paper paper);
	
	/**
	 * 获取论文原生数据
	 * 下载论文使用
	 */
	byte[] getPaperData(int paper_id);
}
