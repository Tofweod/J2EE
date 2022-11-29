package com.octenexin.ecnu.controller;

import com.octenexin.ecnu.pojo.Paper;
import com.octenexin.ecnu.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author Tofweod
 */
@Controller
public class PaperController {
	
	@Autowired
	PaperService paperService;
	
	@RequestMapping("/testForm") // 此处映射自行设置，注意修改html文件内uploadURL，跳转操作在html文件内
	public void update(@RequestParam("title")String title,
					   @RequestParam("author")String author,
					   @RequestParam("summary")String summary,
					   @RequestParam("keywords")String keywords,
					   @RequestParam("file") MultipartFile file){
		Paper paper = new Paper();
		try {
			// 此处修改papers表主键自增，方案不唯一
			paper.setPaperId(null);
			paper.setPaperTitle(title);
			paper.setPaperAuthor(author);
			paper.setPaperSummary(summary);
			paper.setPaperKeywords(keywords);
			paper.setPaperStateId(0); // 待审核
			paper.setPaperPrestateId(null);
			paper.setPaperRawdata(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		paperService.addPaper(paper);
	}
	
	/**
	 * 分页实现
	 * 此处responcebody仅仅出于显示数据，可修改
	 */
	@RequestMapping("/show_papers/*")
	@ResponseBody
	public List<Paper> showPapers(HttpServletRequest req){
		String url = req.getRequestURI();
		int idx = url.lastIndexOf("/")+1;
		int page = Integer.parseInt(url.substring(idx)); // 获取*内代表的页数,从1开始
		// 假设每页显示10条数据
		return paperService.getPaperPage((page-1) * 10, 10);
	}
}
