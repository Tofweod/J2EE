package com.octenexin.ecnu;

import com.octenexin.ecnu.pojo.Paper;
import com.octenexin.ecnu.service.PaperService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

@SpringBootTest
class EcnuApplicationTests {

	@Autowired
	PaperService paperService;
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Test
	void contextLoads() throws IOException {
//		String sql = "SELECT paper_rawdata from papers WHERE paper_id = ?";
//		byte[] bytes = jdbcTemplate.queryForObject(sql, byte[].class, 2);

		final byte[] bytes = paperService.getPaperData(2);
		System.out.println(Arrays.toString(bytes));
		FileOutputStream out = new FileOutputStream("test.pdf");
		out.write(bytes);
		out.close();
	}

}
