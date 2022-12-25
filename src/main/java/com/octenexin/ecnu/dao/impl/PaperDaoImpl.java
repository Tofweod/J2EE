package com.octenexin.ecnu.dao.impl;

import com.octenexin.ecnu.dao.PaperDao;
import com.octenexin.ecnu.pojo.Paper;
import com.octenexin.ecnu.pojo.Project;
import com.octenexin.ecnu.pojo.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tofweod
 */
@Repository
public class PaperDaoImpl implements PaperDao {
	
	@Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	public int addPaper(Paper paper) {
		String sql = "INSERT INTO papers (paper_id,paper_title,paper_author,paper_summary,paper_keywords,paper_state_id,paper_prestate_id,paper_url)" +
				"VALUES(?,?,?,?,?,?,?,?)";
		return jdbcTemplate.update(sql,paper.getPaperId(),paper.getPaperTitle(),paper.getPaperAuthor(),paper.getPaperSummary(),
				paper.getPaperKeywords(),paper.getPaperStateId(),paper.getPaperPrestateId(),paper.getPaperUrl());
	}
	
	@Override
	public int updatePaper(Paper paper) {
		String sql = "UPDATE papers SET paper_id = ?,paper_title = ?,paper_author = ?,paper_summary = ?," +
				"paper_keywords = ?,paper_state_id = ?,paper_prestate_id = ?,paper_url = ? WHERE paper_id = ?";
		return jdbcTemplate.update(sql,paper.getPaperId(),paper.getPaperTitle(),paper.getPaperAuthor(),paper.getPaperSummary(),
				paper.getPaperKeywords(),paper.getPaperStateId(),paper.getPaperPrestateId(),paper.getPaperUrl(),paper.getPaperId());
	}
	
	@Override
	public int updatePaperState(Paper paper) {
		String sql = "UPDATE papers SET paper_id = ?,paper_title = ?,paper_author = ?,paper_summary = ?," +
				"paper_keywords = ?,paper_state_id = ?,paper_prestate_id = ? WHERE paper_id = ?";
		return jdbcTemplate.update(sql,paper.getPaperId(),paper.getPaperTitle(),paper.getPaperAuthor(),paper.getPaperSummary(),
				paper.getPaperKeywords(),paper.getPaperStateId(),paper.getPaperPrestateId(),paper.getPaperId());
	}
	
	@Override
	public int deletePaper(Paper paper) {
		String sql = "DELETE FROM papers WHERE paper_id = ?";
		return jdbcTemplate.update(sql,paper.getPaperId());
	}
	
	@Override
	public int countPapers(Paper paper) {
		String sql = "SELECT COUNT(*) FROM papers";
		return jdbcTemplate.queryForObject(sql,Integer.class);
	}
	
	
	@Override
	public List<Paper> getPapers(List<Paper> papers) {
		String sql = "SELECT paper_id,paper_title,paper_author,paper_summary,paper_keywords,paper_state_id,paper_prestate_id,paper_url FROM papers WHERE paper_id = ?";
		List<Paper> result = new ArrayList<>();
		for (Paper paper : papers) {
			result.add(jdbcTemplate.queryForObject(sql,Paper.class,paper.getPaperId()));
		}
		return result;
	}
	
	@Override
	public List<Paper> getPaperPage(int start, int nums) {
		String sql = "SELECT * FROM papers" +
				"limit ?,?;";
		return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Paper.class),start,nums);
	}
	
	@Override
	public Paper getPaper(Paper paper) {
		String sql = "SELECT paper_id,paper_title,paper_author,paper_summary,paper_keywords,paper_state_id,paper_prestate_id,paper_url FROM papers WHERE paper_id = ?";
		try{
			return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Paper.class), paper.getPaperId());
		}catch (EmptyResultDataAccessException e){
			return null;
		}
	}
	@Override
	public Integer getLastId(){
		String sql="SELECT LAST_INSERT_ID();";

		return jdbcTemplate.queryForObject(sql,Integer.class);
	}


	@Override
	public List<Paper> autoQuery(String sql){
		return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Paper.class));
	}

	
}
