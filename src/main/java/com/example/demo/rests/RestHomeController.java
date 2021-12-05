package com.example.demo.rests;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.components.DateTimeComponent;
import com.example.demo.components.JsonConversion;
import com.example.demo.components.SessionManage;
import com.example.demo.entities.SessionEntity;
import com.example.demo.entities.StudentEntity;
import com.example.demo.entities.TimetableEntity;
import com.example.demo.repositories.SessionRepository;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.repositories.TimetableRepository;
import com.example.demo.repositories.TimetabletimeRepository;

@RestController
public class RestHomeController {

	@Autowired
	DateTimeComponent date_time;
	@Autowired
	TimetableRepository timetableRepository;
	@Autowired
	JsonConversion json;
	@Autowired
	TimetabletimeRepository timetabletimeRepository;
	@Autowired
	SessionManage session_manage;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	SessionRepository sessionRepository;

	// セッションidの一時保存
	@PostMapping("/rest/session")
	public String RestSession(@RequestParam("js_session_id") String session_id) {
		try {
			// 学生情報を取得
			String session_mail = session_manage.getSession_mail();
			StudentEntity studentEntity = new StudentEntity();
			studentEntity = studentRepository.SearchStudent(session_mail);

			// セッションidが保存されているかの確認
			SessionEntity sessionEntity = new SessionEntity();
			try {
				//DB検索
				sessionEntity = sessionRepository.SearchIdByStudent(session_mail);
				
				//存在する場合
				sessionEntity.setSessionid(session_id);
				//更新
				sessionRepository.save(sessionEntity);
				return "1";
			}
			catch (EmptyResultDataAccessException e) {
				//存在しない場合
				sessionEntity.setStudent(studentEntity);
				sessionEntity.setSessionid(session_id);
				// 保存
				sessionRepository.save(sessionEntity);
				return "1";
			}
		} catch (Exception e) {
			System.out.println("[Rest]RestSession　：　" + e);
			return "0";
		}
	}

	// 授業に参加できるかの確認
	@PostMapping(value = "/rest/confirm_entertime")
	public String RestConfirmEnterTime(@RequestParam("js_classid") String classid) {

		try {
			// 現在時刻、曜日を取得
			String dayofweek = date_time.Get_DayOfTheWeekShort();
			String current_time = date_time.Get_Time();
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
				return json.ObjectToJSON(time_table);
			} catch (NullPointerException e) {
				System.out.println("[Rest]RestConfirmEnterTime[Null]　：　" + e);
				return "0";
			}
		} catch (Exception e) {
			System.out.println("[Rest]RestConfirmEnterTime　：　" + e);
			return "0";
		}
	}

	// 時限数の取得
	@PostMapping(value = "/rest/time_period")
	public String RestTimePeriod(@RequestParam("js_time_id") String time_id) {
		try {
			// DB検索
			TimetableEntity time_table = new TimetableEntity();
			time_table = timetableRepository.findById(Integer.parseInt(time_id));
			int time_period = time_table.getTimetabletime().getTimeperiod();
			return json.ObjectToJSON(time_period);
		} catch (Exception e) {
			System.out.println("[Rest]RestTimePeriod　：　" + e);
			return "0";
		}
	}

	// 入室許可が下りているかを確認
	@PostMapping(value = "/rest/check_enterflag")
	public String RestCheckEnterflag(@RequestParam("js_periodid") String periodid,
			@RequestParam("js_classid") String classid) {
		try {
			String dayofweek = date_time.Get_DayOfTheWeekShort();
			TimetableEntity time_table = new TimetableEntity();
			time_table = timetableRepository.SearchClass(dayofweek, classid, periodid);
			int time_period = time_table.getEnter_flag();
			return json.ObjectToJSON(time_period);
		} catch (Exception e) {
			System.out.println("[Rest]RestCheckEnterflag　：　" + e);
			return "error";
		}
	}
}