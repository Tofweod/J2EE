package com.octenexin.ecnu.controller;

import com.octenexin.ecnu.pojo.Paper;
import com.octenexin.ecnu.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @author Tofweod
 */
@Controller
public class PaperController {
	
	@Autowired
	PaperService paperService;
	
	// 论文下载路径，根据application配置获取
	@Value("${upload.path}")
	String uploadPath;
	
	@RequestMapping("/testForm") // 此处映射自行设置，注意修改html文件内uploadURL，跳转操作在html文件内
	public String update(@RequestParam("title")String title,
					   @RequestParam("author")String author,
					   @RequestParam("summary")String summary,
					   @RequestParam("keywords")String keywords,
					   @RequestParam("file") MultipartFile file,HttpServletRequest req) throws IOException {
		Paper paper = new Paper();
		// 设置论文的保存路径
		String pathName = System.getProperty("user.dir")+uploadPath; // 动态获取项目路径
		String fileName = title+"-"+author+".pdf";
		try(OutputStream out = new FileOutputStream(pathName+fileName)) {
			// 此处修改papers表主键自增，方案不唯一
			paper.setPaperId(null);
			paper.setPaperTitle(title);
			paper.setPaperAuthor(author);
			paper.setPaperSummary(summary);
			paper.setPaperKeywords(keywords);
			paper.setPaperStateId(0); // 待审核
			paper.setPaperPrestateId(null);
			paper.setPaperUrl(fileName);
			out.write(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		paperService.addPaper(paper);
		return "paperForm";
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
	@RequestMapping("/download")
	public String downLoad(HttpServletResponse response, Paper paper, Model model){
		String pathName = System.getProperty("user.dir")+uploadPath;
		
		// 下载文件名规定为：标题-作者.pdf,
		String fileName = paper.getPaperUrl();
		
		// 设置请求头为下载文件
		response.setHeader("content-disposition","attachment;fileName="+fileName);

		// 从uploads中传输文件到响应流中
		File file = new File(pathName + fileName);
		try(InputStream in = new FileInputStream(file);
			ServletOutputStream out = response.getOutputStream()) {
			byte[] buf = new byte[1024];
			while(in.read(buf) != -1){
				out.write(buf);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 设置返回信息
		model.addAttribute("fileName",fileName);
		model.addAttribute("fileSize",file.length()); // 返回单位：b
		return "需要跳转页面";
	}
}
