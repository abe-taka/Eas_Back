package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.components.DateTimeComponent;
import com.example.demo.components.SessionManage;
import com.example.demo.entities.StudentEntity;
import com.example.demo.entities.TeacherEntity;
import com.example.demo.entities.TimetableEntity;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.repositories.TeacherRepository;
import com.example.demo.repositories.TimetableRepository;

//ホーム
@Controller
@RequestMapping(value = "/home")
public class HomeController {
	@Autowired
	SessionManage session_manage;
	@Autowired
	TeacherRepository teacherRepository;
	@Autowired
	DateTimeComponent datetime;
	@Autowired
	TimetableRepository timetableRepository;
	@Autowired
	StudentRepository studentRepository;

	// 判別処理
	@GetMapping(value = "/identification")
	public String Get_HomeIdentification(Model model, @AuthenticationPrincipal OidcUser user,RedirectAttributes redir) {
		// メールアドレスを取得
		String session_mail = user.getEmail();

		// 先生か学生かを判別
		if (teacherRepository.CheckTeacher(session_mail)) {
			// 先生
			TeacherEntity teacherEntity = new TeacherEntity();
			teacherEntity = teacherRepository.SearchTeacher(session_mail);
			// セッションにメールアドレス、名前を格納
			session_manage.setSession_mail(session_mail);
			session_manage.setSession_name(teacherEntity.getTeachername());
			session_manage.setSession_schoolcode(teacherEntity.getSchool().getSchoolcode());

			return "redirect:/home/teacherhome";
		} else if (studentRepository.CheckStudent(session_mail)) {
			// 学生
			StudentEntity studentEntity = new StudentEntity();
			studentEntity = studentRepository.SearchStudent(session_mail);
			// セッションにメールアドレス、名前、クラスID、出席番号を格納
			session_manage.setSession_mail(session_mail);
			session_manage.setSession_name(studentEntity.getStudentname());
			session_manage.setSession_classid(studentEntity.getClassentity().getClassid());
			session_manage.setSession_calss_no(studentEntity.getClassno());
			return "redirect:/home/studenthome";
		} else {
			// DBに存在しない
			redir.addFlashAttribute("loginerror", "ログイン失敗");
			return "redirect:/";
		}
	}

	// 先生ホーム画面
	@GetMapping(value = "/teacherhome")
	public String Get_TeacherHome(Model model) {
		// セッションがあるかをチェック
		if (session_manage.getSession_mail() == null) {
			return "redirect:/";
		}
		else {
			// 現在日付を取得
			String date = null;
			date = datetime.Get_Monthdate(date);
			model.addAttribute("date", date);

			// 曜日を取得
			String dayofweek = null;
			dayofweek = datetime.getDayOfTheWeekShort();

			// メールドレスを取得
			String mailaddress = null;
			mailaddress = session_manage.getSession_mail();

			// スケジュールを取得
			List<TimetableEntity> timetableentity = timetableRepository.SearchTodayTeacherSchedule(dayofweek,mailaddress);
			//クラスidを「○年○組」の形式に切り替える
			for (int i = 0; i < timetableentity.size(); i++) {
				timetableentity.get(i).getClassentity().setClassid(String.valueOf(timetableentity.get(i).getClassentity().getSchoolyear()) + "年" + String.valueOf(timetableentity.get(i).getClassentity().getSchoolclass()) + "組");
			}
			model.addAttribute("TimetableList", timetableentity);

			// 名前を取得
			String session_name = session_manage.getSession_name();
			model.addAttribute("session_name", session_name);

			return "home/teacherhome";
		}
	}

	// 学生ホーム画面
	@GetMapping(value = "/studenthome")
	public String Get_StudentHome(Model model) {
		// セッションがあるかをチェック
		if (session_manage.getSession_mail() == null) {
			return "redirect:/";
		} else {
			// 現在日付を取得
			String date = null;
			date = datetime.Get_Monthdate(date);
			model.addAttribute("date", date);

			// 曜日を取得
			String dayofweek = null;
			dayofweek = datetime.getDayOfTheWeekShort();

			// クラスidを取得
			String classid = session_manage.getSession_classid();
			model.addAttribute("classid", classid);

			// スケジュールを取得
			List<TimetableEntity> timetableentity = timetableRepository.SearchTodayStudentSchedule(dayofweek, classid);
			model.addAttribute("TimetableList", timetableentity);

			// 名前を取得
			String session_name = session_manage.getSession_name();
			model.addAttribute("session_name", session_name);

			return "home/studenthome";
		}
	}
}