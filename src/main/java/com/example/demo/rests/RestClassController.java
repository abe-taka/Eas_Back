package com.example.demo.rests;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.demo.entities.TimetableEntity;
import com.example.demo.repositories.ClassIssueRepository;
import com.example.demo.repositories.ClassRepository;
import com.example.demo.repositories.EnterExitRepository;
import com.example.demo.repositories.SessionRepository;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.repositories.TeacherRepository;
import com.example.demo.repositories.TimetableRepository;
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
	@Autowired
	TimetableRepository timetableRepository;

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

	// 退出ログの保存、一時保存のセッションidのデータ削除
	@PostMapping("/rest/exit")
	public String RestExit(@RequestParam("js_enter_id") String enter_id) {
		try {
			// id検索
			EnterExitEntity enterexitEntity = enterexitRepository.findById(Integer.parseInt(enter_id));
			// 退出ログの保存
			if (enterexitEntity.getExittime() == null) {
				// 年月日を取得
				String real_time = datetime.Get_YearMonthDate();
				enterexitEntity.setExittime(real_time);
				// 保存
				enterexitRepository.save(enterexitEntity);
				return "1";
			} else {
				return "0";
			}
		} catch (Exception e) {
			System.out.println("[Rest]RestExit　：　" + e);
			return "0";
		} finally {
			// 学生情報を取得
			String session_mail = session_manage.getSession_mail();
			StudentEntity studentEntity = new StudentEntity();
			studentEntity = studentRepository.SearchStudent(session_mail);
			// 削除
			sessionRepository.deleteByStudent(studentEntity);
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
			// 年月日を取得
			String real_time = datetime.Get_YearMonthDate();
			// Entityにセット
			classIssueEntity.setStudent(studentEntity);
			classIssueEntity.setClassanswercontent(answer);
			classIssueEntity.setClassanswerdate(real_time);
			// 保存
			classIssueRepository.save(classIssueEntity);
			return "1";
		} catch (Exception e) {
			System.out.println("[Rest]RestIssueAnswer　：　" + e);
			return "0";
		}
	}

	// 学生の入室を許可
	@PostMapping(value = "/rest/update_enterflag")
	public String RestUpdateEnterflag(@RequestParam("js_classid") String classid) {
		try {
			// 現在時刻、曜日を取得
			String dayofweek = datetime.Get_DayOfTheWeekShort();
			String current_time = datetime.Get_Time();
			// 10分後のじかんを取得
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			Date dt = sdf.parse(current_time);
			// Calendarクラスのインスタンスを生成
			Calendar cal = Calendar.getInstance();
			// 10分加算
			cal.setTime(dt);
			cal.add(Calendar.MINUTE, 10);
			String tenafter_time = String.valueOf(sdf.format(cal.getTime()));

			// DB検索
			try {
				TimetableEntity time_table = new TimetableEntity();
				time_table = timetableRepository.SearchStartClass(dayofweek, classid, current_time, tenafter_time);
				// フラグを「1」にする
				time_table.setEnter_flag(1);
				// 更新
				timetableRepository.save(time_table);
				
				return jsonConversion.ObjectToJSON(time_table.getTimeid());
			} catch (NullPointerException e) {
				System.out.println("[Rest]RestUpdateEnterflag[Null]　：　" + e);
				return "0";
			}
		} catch (Exception e) {
			System.out.println("[Rest]RestUpdateEnterflag　：　" + e);
			return "0";
		}
	}

	// 学生の入室を拒否
	@PostMapping(value = "/rest/return_enterflag")
	public String RestReturnEnterflag(@RequestParam("js_classid") String classid,@RequestParam("js_timeid") String timeid) {
		try {
			// 更新
			TimetableEntity time_table = new TimetableEntity();
			time_table = timetableRepository.findById(Integer.parseInt(timeid));
			time_table.setEnter_flag(0);
			timetableRepository.save(time_table);
			
			return "1";
		} catch (Exception e) {
			System.out.println("[Rest]RestReturnEnterflag　：　" + e);
			return "0";
		}
	}
}