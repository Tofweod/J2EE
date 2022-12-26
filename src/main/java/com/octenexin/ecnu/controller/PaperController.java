package com.octenexin.ecnu.controller;

import com.octenexin.ecnu.EcnuApplication;
import com.octenexin.ecnu.dao.PaperDao;
import com.octenexin.ecnu.dao.ProjectDao;
import com.octenexin.ecnu.pojo.Paper;
import com.octenexin.ecnu.pojo.Project;
import com.octenexin.ecnu.service.PaperService;
import com.octenexin.ecnu.util.FileSaveUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Tofweod
 */
@RestController
public class PaperController {
	
	@Autowired
	PaperService paperService;

	@Autowired
	PaperDao paperDao;

	@Autowired
	ProjectDao projectDao;
	

	// 论文下载路径，根据application配置获取
	@Value("${upload.path}")
	String uploadPath;
	
	@PostMapping("/student/paper/do-add") // 此处映射自行设置，注意修改html文件内uploadURL，跳转操作在html文件内
	public String doAddPaper(@RequestParam("projectId")String projectId,
						 @RequestParam("title")String title,
					   @RequestParam("author")String author,
					   @RequestParam("summary")String summary,
					   @RequestParam("keywords")String keywords,
					   @RequestParam("file") MultipartFile file,
						 HttpServletRequest req){

		Paper paper = new Paper();



		try{
			// 此处修改papers表主键自增，方案不唯一
			paper.setPaperId(null);
			paper.setPaperTitle(title);
			paper.setPaperAuthor(author);
			paper.setPaperSummary(summary);
			paper.setPaperKeywords(keywords);
			paper.setPaperStateId(0); // 待审核
			paper.setPaperPrestateId(null);

			String filePath="project"+projectId+"/"+file.getOriginalFilename();// project1/afhvaur.pdf
			paper.setPaperUrl(filePath);

			FileSaveUtil.savePaper(file, projectId);

			paperService.addPaper(paper);

			//bind project

			Project project=new Project();
			project.setProjectId(Integer.valueOf(projectId));
 			project.setProjectPaperId(paperDao.getLastId());


			projectDao.updatePaper(project);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "success";
	}

	@RequestMapping("/student/paper/do-update") // 此处映射自行设置，注意修改html文件内uploadURL，跳转操作在html文件内
	public String doUpdatePaper(@RequestParam("projectId")String projectId,
							 @RequestParam("paperId")String paperId,
							 @RequestParam("title")String title,
							 @RequestParam("author")String author,
							 @RequestParam("summary")String summary,
							 @RequestParam("keywords")String keywords,
							 @RequestParam("file") MultipartFile file,
							 HttpServletRequest req){

		Paper paper = new Paper();



		try{
			// 此处修改papers表主键自增，方案不唯一
			paper.setPaperId(Integer.valueOf(paperId));
			paper.setPaperTitle(title);
			paper.setPaperAuthor(author);
			paper.setPaperSummary(summary);
			paper.setPaperKeywords(keywords);
			paper.setPaperStateId(0); // 待审核
			paper.setPaperPrestateId(null);

			String filePath="project"+projectId+"/"+file.getOriginalFilename();
			paper.setPaperUrl(filePath);

			FileSaveUtil.savePaper(file, projectId);

			paperService.updatePaper(paper);

			//bind project

			Project project=new Project();
			project.setProjectId(Integer.valueOf(projectId));
			project.setProjectPaperId(paperDao.getLastId());


			projectDao.updatePaper(project);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "success";
	}

	@PostMapping("paper/do-delete")
	public String doDeletePaper(@RequestParam("paperId")String paperId){

		try{
			paperService.deletePaper(paperId);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "success";
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
	
	/**
	 * 论文下载功能，具体获取paper_id方法未提供
	 * 通过<a></a>标签提供的url进行下载
	 */
	@RequestMapping("/paper/download")
	public String downLoad(@RequestParam("paper") String paper,HttpServletResponse response, Model model){
		//String pathName = System.getProperty("user.dir")+uploadPath;

		String url=FileSaveUtil.getFileLoadRootUrl();

		Paper paper1=new Paper();
		paper1.setPaperId(Integer.parseInt(paper));
		Paper realPaper=paperDao.getPaper(paper1);


		// 下载文件名规定为：标题-作者.pdf,
		String fileName = realPaper.getPaperUrl();


		String originalFileName="paper1.pdf";
		
		// 设置请求头为下载文件
		response.setHeader("content-disposition","attachment;fileName="+originalFileName);

		// 从uploads中传输文件到响应流中
		File file = new File(url+fileName);
		try(InputStream in = Files.newInputStream(file.toPath());
			ServletOutputStream out = response.getOutputStream()) {
			byte[] buf = new byte[1024];
			while(in.read(buf) != -1){
				out.write(buf);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		// 设置返回信息
//		model.addAttribute("fileName",fileName);
//		model.addAttribute("fileSize",file.length()); // 返回单位：b
//		return "需要跳转页面";

		return "success";
	}
}
