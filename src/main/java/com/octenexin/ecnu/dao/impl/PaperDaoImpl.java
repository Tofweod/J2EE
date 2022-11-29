package com.octenexin.ecnu.dao.impl;

import com.octenexin.ecnu.dao.PaperDao;
import com.octenexin.ecnu.pojo.Paper;
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
	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public int addPaper(Paper paper) {
		String sql = "INSERT INTO papers (paper_id,paper_title,paper_author,paper_summary,paper_keywords,paper_state_id,paper_prestate_id,paper_rawdata)" +
				"VALUES(?,?,?,?,?,?,?,?)";
		return jdbcTemplate.update(sql,paper.getPaperId(),paper.getPaperTitle(),paper.getPaperAuthor(),paper.getPaperSummary(),
				paper.getPaperKeywords(),paper.getPaperStateId(),paper.getPaperPrestateId(),paper.getPaperRawdata());
	}
	
	@Override
	public int updatePaper(Paper paper) {
		String sql = "UPDATE papers SET paper_id = ?,paper_title = ?,paper_author = ?,paper_summary = ?," +
				"paper_keywords = ?,paper_state_id = ?,paper_prestate_id = ?,paper_rawdata = ? WHERE paper_id = ?";
		return jdbcTemplate.update(sql,paper.getPaperId(),paper.getPaperTitle(),paper.getPaperAuthor(),paper.getPaperSummary(),
				paper.getPaperKeywords(),paper.getPaperStateId(),paper.getPaperPrestateId(),paper.getPaperRawdata(),paper.getPaperId());
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
		String sql = "SELECT paper_id,paper_title,paper_author,paper_summary,paper_keywords,paper_state_id,paper_prestate_id FROM papers WHERE paper_id = ?";
		List<Paper> result = new ArrayList<>();
		for (Paper paper : papers) {
			result.add(jdbcTemplate.queryForObject(sql,Paper.class,paper.getPaperId()));
		}
		return result;
	}
	
	@Override
	public List<Paper> getPaperPage(int start, int nums) {
		String sql = "SELECT paper_id,paper_title,paper_author,paper_summary,paper_keywords,paper_state_id,paper_prestate_id FROM papers" +
				"LIMIT ?,?";
		return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Paper.class),start,nums);
	}
	
	@Override
	public Paper getPaper(Paper paper) {
		String sql = "SELECT paper_id,paper_title,paper_author,paper_summary,paper_keywords,paper_state_id,paper_prestate_id FROM papers WHERE paper_id = ?";
		try{
			return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Paper.class), paper.getPaperId());
		}catch (EmptyResultDataAccessException e){
			return null;
		}
	}
	
	@Override
	public List<Paper> getPapersWithData(List<Paper> papers) {
		String sql = "SELECT * FROM papers WHERE paper_id = ?";
		List<Paper> result = new ArrayList<>();
		for (Paper paper : papers) {
			result.add(jdbcTemplate.queryForObject(sql,Paper.class,paper.getPaperId()));
		}
		return result;
	}
	
	@Override
	public Paper getPaperWithData(Paper paper) {
		String sql = "SELECT * FROM papers WHERE paper_id = ?";
		try{
			return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Paper.class), paper.getPaperId());
		}catch (EmptyResultDataAccessException e){
			return null;
		}
	}
}
