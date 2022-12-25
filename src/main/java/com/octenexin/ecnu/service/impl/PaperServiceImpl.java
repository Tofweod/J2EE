package com.octenexin.ecnu.service.impl;

import com.octenexin.ecnu.dao.PaperDao;
import com.octenexin.ecnu.dao.ProjectDao;
import com.octenexin.ecnu.pojo.Paper;
import com.octenexin.ecnu.pojo.Project;
import com.octenexin.ecnu.service.PaperService;
import com.octenexin.ecnu.util.FileSaveUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Tofweod
 */
@Service("paperService")
public class PaperServiceImpl implements PaperService {

	@Autowired
	ProjectDao projectDao;

	@Resource
	PaperDao paperDao;
	
	@Override
	public int addPaper(Paper paper) {
		return paperDao.addPaper(paper);
	}
	
	@Override
	public int updatePaper(Paper paper) {
		return paperDao.updatePaper(paper);
	}
	
	@Override
	public int deletePaper(String paperId) {

		//unbind project
		List<Project> list=projectDao.getProjectByPaper(paperId);
		for (Project p : list) {
			p.setProjectPaperId(null);
		}

		//delete paper
		Paper paper=new Paper();
		paper.setPaperId(Integer.valueOf(paperId));


			//delete file
			Paper realPaper=paperDao.getPaper(paper);
			FileSaveUtil.deletePaper(realPaper.getPaperUrl());

		return paperDao.deletePaper(paper);
	}
	
	
	@Override
	public List<Paper> getPapers(List<Paper> papers) {
		return paperDao.getPapers(papers);
	}
	
	
	@Override
	public List<Paper> getPaperPage(int start, int nums) {
		return paperDao.getPaperPage(start,nums);
	}
	
	@Override
	public Paper getPaper(Paper paper) {
		return paperDao.getPaper(paper);
	}
	
}
