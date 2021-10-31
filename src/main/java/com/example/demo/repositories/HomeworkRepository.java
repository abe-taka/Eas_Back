package com.example.demo.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.HomeWorkManageEntity;

@Repository
public class HomeworkRepository<Homework> {

	@Autowired
	JdbcTemplate  jdbctemplate;
	

	/**
	 * 宿題管理テーブル情報(homeworkmanage_table) 全検索
	 * @return 検索結果
	*/
	public List findAll(){
		String sql ="SELECT * FROM homeworkmanage_table";
		return jdbctemplate.queryForList(sql);
	}
	
	public void insertPDF(HomeWorkManageEntity homeWorkManageEntity) {
		jdbctemplate.update("INSERT INTO homeworkmanage_table(answercolumn_num,homework_filename) Values(?,?)",
				homeWorkManageEntity.getAnswercolumnnum(),homeWorkManageEntity.getHomeworkfilename());
	}
	
	public void deleteHomeworkList(Integer homework_id) {
		jdbctemplate.update("DELETE FROM homeworkmanage_table WHERE homework_id = ?",
				homework_id);
	}
	

}
