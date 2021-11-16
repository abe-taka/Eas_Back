package com.example.demo.rests;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.components.DateTimeComponent;
import com.example.demo.components.JsonConversion;
import com.example.demo.components.SessionManage;
import com.example.demo.entities.ClassEntity;
import com.example.demo.entities.ClassIssueEntity;
import com.example.demo.entities.EnterExitEntity;
import com.example.demo.entities.SessionEntity;
import com.example.demo.entities.StudentEntity;
import com.example.demo.entities.TeacherEntity;
import com.example.demo.repositories.ClassIssueRepository;
import com.example.demo.repositories.ClassRepository;
import com.example.demo.repositories.EnterExitRepository;
import com.example.demo.repositories.SessionRepository;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.repositories.TeacherRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

//Restコントローラー(授業)
@RestController
public class RestClassController {

	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	ClassRepository classRepository;
	@Autowired
	JsonConversion jsonConversion;
	@Autowired
	TeacherRepository teacherRepository;
	@Autowired
	SessionManage session_manage;
	@Autowired
	ClassIssueRepository classIssueRepository;
	@Autowired
	DateTimeComponent datetime;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	EnterExitRepository enterexitRepository;
	@Autowired
	SessionRepository sessionRepository;
//	@Autowired
//	BCryptPasswordEncoder password;

	// 組データ取得(授業選択画面)
	@PostMapping("/rest/room_select")
	public String RestRoomSelect(@RequestParam("js_schoolyear") int schoolyear) {
		// 所属学校コード取得
		TeacherEntity teacherEntity = new TeacherEntity();
		teacherEntity = teacherRepository.SearchTeacher(session_manage.getSession_mail());
		int school_code = teacherEntity.getSchool().getSchoolcode();
		// クラスデータを取得
		List<ClassEntity> list_classEntity = classRepository.SearchSchoolclassBySchoolyear(school_code, schoolyear);

		return jsonConversion.listToJSON(list_classEntity);
	}

	// 入室ログを登録
	@PostMapping("/rest/enter")
	public String RestEnter() {
		try {
			EnterExitEntity enterexitEntity = new EnterExitEntity();
			// 学生情報を取得
			String session_mail = session_manage.getSession_mail();
			StudentEntity studentEntity = new StudentEntity();
			studentEntity = studentRepository.SearchStudent(session_mail);
			// 現在時刻を取得
			String real_time = null;
			real_time = datetime.Get_RealTime(real_time);
			// Entityにセット
			enterexitEntity.setStudent(studentEntity);
			enterexitEntity.setEntertime(real_time);
			// 保存
			enterexitRepository.save(enterexitEntity);
			return "1";
		} catch (Exception e) {
			System.out.println("RestIssueAnswer：" + e);
			return "0";
		}
	}

	// 退出ログを登録
	@PostMapping("/rest/exit")
	public String RestExit() {
		try {
			EnterExitEntity enterexitEntity = new EnterExitEntity();
			// 学生情報を取得
			String session_mail = session_manage.getSession_mail();
			StudentEntity studentEntity = new StudentEntity();
			studentEntity = studentRepository.SearchStudent(session_mail);
			// 現在時刻を取得
			String real_time = null;
			real_time = datetime.Get_RealTime(real_time);
			// Entityにセット
			enterexitEntity.setStudent(studentEntity);
			enterexitEntity.setExittime(real_time);
			// 保存
			enterexitRepository.save(enterexitEntity);
			return "1";
		} catch (Exception e) {
			System.out.println("RestIssueAnswer：" + e);
			return "0";
		}
	}

	// セッションidの一時保存
	@PostMapping("/rest/session")
	public String RestSession(@RequestParam("js_session_id") String session_id) {
		try {
			SessionEntity sessionEntity = new SessionEntity();
			// 学生情報を取得
			String session_mail = session_manage.getSession_mail();
			StudentEntity studentEntity = new StudentEntity();
			studentEntity = studentRepository.SearchStudent(session_mail);
			
			// Entityにセット
			sessionEntity.setStudent(studentEntity);
			sessionEntity.setSessionid(session_id);
			// 保存
			sessionRepository.save(sessionEntity);
			return "1";
		} catch (Exception e) {
			System.out.println("RestIssueAnswer：" + e);
			return "0";
		}
	}

	// 授業内問題解答登録処理
	@PostMapping("/rest/issue_answer")
	public String RestIssueAnswer(@RequestParam("js_answer") String answer) {
		try {
			ClassIssueEntity classIssueEntity = new ClassIssueEntity();
			// 学生情報を取得
			String session_mail = session_manage.getSession_mail();
			StudentEntity studentEntity = new StudentEntity();
			studentEntity = studentRepository.SearchStudent(session_mail);
			// 現在時刻を取得
			String real_time = null;
			real_time = datetime.Get_RealTime(real_time);
			// Entityにセット
			classIssueEntity.setStudent(studentEntity);
			classIssueEntity.setClassanswercontent(answer);
			classIssueEntity.setClassanswerdate(real_time);
			// 保存
			classIssueRepository.save(classIssueEntity);
			return "1";
		} catch (Exception e) {
			System.out.println("RestIssueAnswer：" + e);
			return "0";
		}
	}
}