package com.example.demo.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.components.SessionManage;
import com.example.demo.entities.HomeWorkManageEntity;
import com.example.demo.forms.HomeworkForm;

@Repository
public class HomeworkRepository<Homework> {

	@Autowired
	JdbcTemplate  jdbctemplate;
	
	@Autowired
	SessionManage session_manage;
	
	/**
	 * 宿題管理テーブル情報(homeworkmanage_table) 全検索
	 * @return 検索結果
	*/
	@SuppressWarnings("rawtypes")
	public List findAll(){
		String sql ="SELECT * FROM homeworkmanage_table";
		return jdbctemplate.queryForList(sql);
	}

	/**
	 * 宿題管理テーブル(homework_management_tbl) 追加
	 * @param homeWorkManageEntity
	 */
	public void insertPDF(HomeWorkManageEntity homeWorkManageEntity) {
		jdbctemplate.update("INSERT INTO homeworkmanage_table(answercolumn_num,homework_filename,teacher_address) Values(?,?,?)",
				homeWorkManageEntity.getAnswercolumnnum(),homeWorkManageEntity.getHomeworkfilename(),session_manage.getSession_mail());
	}
	
	/**
	 *  宿題管理テーブル(homework_management_tbl) 指定したファイル削除
	 * @param homework_id
	 */
	public void deleteHomeworkList(Integer homework_id) {
		jdbctemplate.update("DELETE FROM homeworkmanage_table WHERE homework_id = ?",
				homework_id);
	}
	
	/**
	 * 宿題管理テーブル(homework_management_tbl) ローカルファイルの削除をする際にファイル名を取得するため
	 * @param homework_id
	 * @return 
	 */
	public HomeworkForm selectFileName(Integer homework_id) {
		String sql = "SELECT * FROM homeworkmanage_table WHERE homework_id = ?";
		Map<String, Object> Homeworkresult = jdbctemplate.queryForMap(sql, homework_id);
		HomeworkForm homeworkForm= new HomeworkForm();
		homeworkForm.setHomework_id((Integer) Homeworkresult.get("homework_id"));
		homeworkForm.setHomework_filename((String) Homeworkresult.get("homework_filename"));
		return homeworkForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List findAllHomeworkSubmiStatus(){
		String sql ="SELECT * FROM homeworkanswer_table;";
		return jdbctemplate.queryForList(sql);
	}

}
