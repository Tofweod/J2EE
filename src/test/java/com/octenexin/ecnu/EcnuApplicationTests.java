package com.octenexin.ecnu;

import com.octenexin.ecnu.pojo.Paper;
import com.octenexin.ecnu.service.PaperService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class EcnuApplicationTests {

	@Autowired
	PaperService paperService;
	
	@Test
	void contextLoads() {
		Paper paper = new Paper();
		paper.setPaperId(1);
		Paper paper1 = paperService.getPaper(paper);
	}

}
