package com.example.demo.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.components.SessionManage;
import com.example.demo.entities.HomeWorkAnswerEntity;
import com.example.demo.entities.HomeWorkManageEntity;
import com.example.demo.entities.HomeWorkSubmissionEntity;
import com.example.demo.entities.SubmissionFlgEntity;
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
		jdbctemplate.update("INSERT INTO homeworkmanage_table"
				+ "(answercolumn_num,homework_filename,teacher_address,homework_subject) Values(?,?,?,?)",
				homeWorkManageEntity.getAnswercolumnnum(),homeWorkManageEntity.getHomeworkfilename(),
				session_manage.getSession_mail(),homeWorkManageEntity.getHomeworksubject());
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
	
	
	/**
	 * homework_submistatus(宿題提出確認画面)でクラス別の生徒一覧取得
	 * @param schoolCode
	 * @return
	 */
	public List studentListfindAll(Integer schoolCode){
		String sql ="SELECT * FROM student_table WHERE class_id = ? ORDER BY class_no ASC;";
		return jdbctemplate.queryForList(sql,schoolCode);
	}
	
	/**
	 * homeworkdetails(宿題提出生徒個別提出状況確認画面)で生徒個別の提出状況確認
	 * @param schoolCode
	 * @param classNo
	 * @return
	 */
	public List homeworkListfindAll(Integer schoolCode,Integer classNo){
		String sql ="SELECT \r\n"
				+ "	homeworkmanage_table.homework_filename,\r\n"
				+ "	homeworkmanage_table.homework_subject,\r\n"
				+ "	homeworkmanage_table.homework_id,\r\n"
				+ "	submission_flg_table.submission_flg,\r\n"
				+ "	submission_flg_table.submission_id,\r\n"
				+ "	homeworkmanage_table.answercolumn_num\r\n"
				+ "FROM \r\n"
				+ "	homeworkmanage_table\r\n"
				+ "	JOIN \r\n"
				+ "	submission_flg_table\r\n"
				+ "	ON homeworkmanage_table.homework_id = submission_flg_table.homework_id\r\n"
				+ "WHERE \r\n"
				+ "	submission_flg_table.class_id = ?\r\n"
				+ "	AND \r\n"
				+ "	submission_flg_table.class_no = ?"
				+ ";";
		return jdbctemplate.queryForList(sql,schoolCode,classNo);
	}
	
	/**
	 * homeworkdetails(宿題提出生徒個別提出状況確認画面)で生徒個別の提出状況確認
	 * @param schoolCode
	 * @param classNo
	 * @return
	 */
	public List homeworkListfindAllJapanese (Integer schoolCode,Integer classNo){
		String sql ="SELECT \r\n"
				+ "	homeworkmanage_table.homework_filename,\r\n"
				+ "	homeworkmanage_table.homework_subject,\r\n"
				+ "	homeworkmanage_table.homework_id,\r\n"
				+ "	submission_flg_table.submission_flg,\r\n"
				+ "	submission_flg_table.submission_id,\r\n"
				+ "	homeworkmanage_table.answercolumn_num\r\n"
				+ "FROM \r\n"
				+ "	homeworkmanage_table\r\n"
				+ "	JOIN \r\n"
				+ "	submission_flg_table\r\n"
				+ "	ON homeworkmanage_table.homework_id = submission_flg_table.homework_id\r\n"
				+ "WHERE \r\n"
				+ "	submission_flg_table.class_id = ?\r\n"
				+ "AND \r\n"
				+ "	submission_flg_table.class_no = ?\r\n"
				+ "AND \r\n"
				+ "	homeworkmanage_table.homework_subject = '国語'\r\n"
				+ ";";
		return jdbctemplate.queryForList(sql,schoolCode,classNo);
	}
	
	/**
	 * homeworkdetails(宿題提出生徒個別提出状況確認画面)で生徒個別の提出状況確認
	 * @param schoolCode
	 * @param classNo
	 * @return
	 */
	public List homeworkListfindAllMath (Integer schoolCode,Integer classNo){
		String sql ="SELECT \r\n"
				+ "	homeworkmanage_table.homework_filename,\r\n"
				+ "	homeworkmanage_table.homework_subject,\r\n"
				+ "	homeworkmanage_table.homework_id,\r\n"
				+ "	submission_flg_table.submission_flg,\r\n"
				+ "	submission_flg_table.submission_id,\r\n"
				+ "	homeworkmanage_table.answercolumn_num\r\n"
				+ "FROM \r\n"
				+ "	homeworkmanage_table\r\n"
				+ "	JOIN \r\n"
				+ "	submission_flg_table\r\n"
				+ "	ON homeworkmanage_table.homework_id = submission_flg_table.homework_id\r\n"
				+ "WHERE \r\n"
				+ "	submission_flg_table.class_id = ?\r\n"
				+ "AND \r\n"
				+ "	submission_flg_table.class_no = ?\r\n"
				+ "AND \r\n"
				+ "	homeworkmanage_table.homework_subject = '算数'\r\n"
				+ ";";
		return jdbctemplate.queryForList(sql,schoolCode,classNo);
	}
	
	/**
	 * homeworkdetails(宿題提出生徒個別提出状況確認画面)で生徒個別の提出状況確認
	 * @param schoolCode
	 * @param classNo
	 * @return
	 */
	public List homeworkListfindAllSocialstudies (Integer schoolCode,Integer classNo){
		String sql ="SELECT \r\n"
				+ "	homeworkmanage_table.homework_filename,\r\n"
				+ "	homeworkmanage_table.homework_subject,\r\n"
				+ "	homeworkmanage_table.homework_id,\r\n"
				+ "	submission_flg_table.submission_flg,\r\n"
				+ "	submission_flg_table.submission_id,\r\n"
				+ "	homeworkmanage_table.answercolumn_num\r\n"
				+ "FROM \r\n"
				+ "	homeworkmanage_table\r\n"
				+ "	JOIN \r\n"
				+ "	submission_flg_table\r\n"
				+ "	ON homeworkmanage_table.homework_id = submission_flg_table.homework_id\r\n"
				+ "WHERE \r\n"
				+ "	submission_flg_table.class_id = ?\r\n"
				+ "AND \r\n"
				+ "	submission_flg_table.class_no = ?\r\n"
				+ "AND \r\n"
				+ "	homeworkmanage_table.homework_subject = '社会'\r\n"
				+ ";";
		return jdbctemplate.queryForList(sql,schoolCode,classNo);
	}
	
	/**
	 * homeworkdetails(宿題提出生徒個別提出状況確認画面)で生徒個別の提出状況確認
	 * @param schoolCode
	 * @param classNo
	 * @return
	 */
	public List homeworkListfindAllScience (Integer schoolCode,Integer classNo){
		String sql ="SELECT \r\n"
				+ "	homeworkmanage_table.homework_filename,\r\n"
				+ "	homeworkmanage_table.homework_subject,\r\n"
				+ "	homeworkmanage_table.homework_id,\r\n"
				+ "	submission_flg_table.submission_flg,\r\n"
				+ "	submission_flg_table.submission_id,\r\n"
				+ "	homeworkmanage_table.answercolumn_num\r\n"
				+ "FROM \r\n"
				+ "	homeworkmanage_table\r\n"
				+ "	JOIN \r\n"
				+ "	submission_flg_table\r\n"
				+ "	ON homeworkmanage_table.homework_id = submission_flg_table.homework_id\r\n"
				+ "WHERE \r\n"
				+ "	submission_flg_table.class_id = ?\r\n"
				+ "AND \r\n"
				+ "	submission_flg_table.class_no = ?\r\n"
				+ "AND \r\n"
				+ "	homeworkmanage_table.homework_subject = '理科'\r\n"
				+ ";";
		return jdbctemplate.queryForList(sql,schoolCode,classNo);
	}
	
	/**
	 * homeworkdetails(宿題提出生徒個別提出状況確認画面)で生徒個別の提出状況確認
	 * @param schoolCode
	 * @param classNo
	 * @return
	 */
	public List homeworkListfindAllEnglish (Integer schoolCode,Integer classNo){
		String sql ="SELECT \r\n"
				+ "	homeworkmanage_table.homework_filename,\r\n"
				+ "	homeworkmanage_table.homework_subject,\r\n"
				+ "	homeworkmanage_table.homework_id,\r\n"
				+ "	submission_flg_table.submission_flg,\r\n"
				+ "	submission_flg_table.submission_id,\r\n"
				+ "	homeworkmanage_table.answercolumn_num\r\n"
				+ "FROM \r\n"
				+ "	homeworkmanage_table\r\n"
				+ "	JOIN \r\n"
				+ "	submission_flg_table\r\n"
				+ "	ON homeworkmanage_table.homework_id = submission_flg_table.homework_id\r\n"
				+ "WHERE \r\n"
				+ "	submission_flg_table.class_id = ?\r\n"
				+ "AND \r\n"
				+ "	submission_flg_table.class_no = ?\r\n"
				+ "AND \r\n"
				+ "	homeworkmanage_table.homework_subject = '英語'\r\n"
				+ ";";
		return jdbctemplate.queryForList(sql,schoolCode,classNo);
	}
	
	/**
	 * homeworkdetails(宿題提出生徒個別提出状況確認画面)で生徒個別の提出状況確認
	 * @param schoolCode
	 * @param classNo
	 * @return
	 */
	public List homeworkListfindAllOthers (Integer schoolCode,Integer classNo){
		String sql ="SELECT \r\n"
				+ "	homeworkmanage_table.homework_filename,\r\n"
				+ "	homeworkmanage_table.homework_subject,\r\n"
				+ "	homeworkmanage_table.homework_id,\r\n"
				+ "	submission_flg_table.submission_flg,\r\n"
				+ "	submission_flg_table.submission_id,\r\n"
				+ "	homeworkmanage_table.answercolumn_num\r\n"
				+ "FROM \r\n"
				+ "	homeworkmanage_table\r\n"
				+ "	JOIN \r\n"
				+ "	submission_flg_table\r\n"
				+ "	ON homeworkmanage_table.homework_id = submission_flg_table.homework_id\r\n"
				+ "WHERE \r\n"
				+ "	submission_flg_table.class_id = ?\r\n"
				+ "AND \r\n"
				+ "	submission_flg_table.class_no = ?\r\n"
				+ "AND \r\n"
				+ "	homeworkmanage_table.homework_subject = 'その他'\r\n"
				+ ";";
		return jdbctemplate.queryForList(sql,schoolCode,classNo);
	}
	
	/**
	 * homeworksubmi(生徒側の宿題提出確認)とhomeworkstudent(生徒宿題)で宿題の名前、宿題の科目、提出したかの有無、宿題ID、解答欄の数の取得
	 * @param schoolCode
	 * @param classNo
	 * @param submission_id
	 * @return
	 */
	public HomeworkForm selectInputText(Integer schoolCode,Integer classNo,Integer submission_id) {
		String sql ="SELECT \r\n"
				+ "	homeworkmanage_table.homework_filename,\r\n"
				+ "	homeworkmanage_table.homework_subject,\r\n"
				+ "	submission_flg_table.submission_flg,\r\n"
				+ "	submission_flg_table.submission_id,\r\n"
				+ "	homeworkmanage_table.answercolumn_num\r\n"
				+ "FROM \r\n"
				+ "	homeworkmanage_table\r\n"
				+ "	JOIN \r\n"
				+ "	submission_flg_table\r\n"
				+ "	ON homeworkmanage_table.homework_id = submission_flg_table.homework_id\r\n"
				+ "WHERE \r\n"
				+ "	submission_flg_table.class_id = ?\r\n"
				+ "	AND \r\n"
				+ "	submission_flg_table.class_no = ?\r\n"
				+ "	AND \r\n"
				+ "	homeworkmanage_table.homework_id = ?"
				+ ";";
		Map<String, Object> selectInputTextresult = jdbctemplate.queryForMap(sql, schoolCode,classNo,submission_id);
		HomeworkForm homeworkForm= new HomeworkForm();
		homeworkForm.setAnswercolumn_num((Integer) selectInputTextresult.get("answercolumn_num"));
		homeworkForm.setHomework_filename((String) selectInputTextresult.get("homework_filename"));
		return homeworkForm;
	}
	
	/**
	 * homeworkstudent(生徒宿題)で宿題を提出した場合宿題提出確認テーブルが提出済みになる
	 * @param session_classno
	 * @param session_classId
	 * @param submission_id
	 */
	public void insertHomework(Integer session_classno,Integer session_classId,Integer submission_id) {
		jdbctemplate.update("UPDATE submission_flg_table\r\n"
							+ "SET submission_flg = 1\r\n"
							+ "WHERE class_no = ?\r\n"
							+ "AND class_id = ?\r\n"
							+ "AND submission_id = ?;",
							session_classno,session_classId,submission_id);
	}
	
	/**
	 * 生徒が宿題を提出した場合解答内容がhomework_answer_tblに保存される
	 * @param homeWorkAnswerEntity
	 * @param homeWorkSubmissionEntity
	 */
	public void insertAnswerContent(HomeWorkAnswerEntity homeWorkAnswerEntity,Integer homework_id) {
		jdbctemplate.update("INSERT INTO homeworkanswer_table \r\n"
						  + "(answer_content,homeworksubmission_id,class_no) \r\n"
						  + "VALUE \r\n"
						  + "(?,?,?)",
				homeWorkAnswerEntity.getAnswer_content(),homework_id,
				homeWorkAnswerEntity.getClassno());
	}
	
	public void insertHomeworksubmission(HomeWorkAnswerEntity homeWorkAnswerEntity,HomeWorkSubmissionEntity homeWorkSubmissionEntity) {
		jdbctemplate.update("INSERT INTO homeworksubmission_table\r\n"
						  + "(class_id,homework_id) \r\n"
						  + "VALUE\r\n"
						  + "(1000200501,3)",
				homeWorkAnswerEntity.getAnswer_content(),homeWorkSubmissionEntity.getHomeworksubmissionid(),
				homeWorkAnswerEntity.getClassno());
	}
	
	public List enterexitListfindAll(Integer schoolCode,Integer classNo){
		String sql ="SELECT\r\n"
					+ "DISTINCT enterexit_table.timetable_id,\r\n"
					+ "enterexit_table.enter_time,\r\n"
					+ "enterexit_table.exit_time\r\n,"
					+ "(SELECT timetabletime_table.time_period FROM timetabletime_table WHERE timetabletime_table.timetable_id = enterexit_table.timetable_id) AS atime_period,\r\n"
					+ "SUBSTRING(enterexit_table.enter_time, 1, 10) AS date\r\n"
					+ "FROM\r\n"
					+ "enterexit_table,student_table,timetabletime_table\r\n"
					+ "WHERE student_table.class_no = ?\r\n"
					+ "AND student_table.class_id = ?;";
		return jdbctemplate.queryForList(sql,classNo,schoolCode);
	}


}
