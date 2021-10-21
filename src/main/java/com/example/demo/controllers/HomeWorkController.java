package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.components.SessionManage;
import com.example.demo.entities.ClassEntity;
import com.example.demo.entities.TeacherEntity;
import com.example.demo.repositories.ClassRepository;
import com.example.demo.repositories.TeacherRepository;

//宿題
@Controller
public class HomeWorkController {

	@Autowired
	SessionManage session_manage;
	@Autowired
	TeacherRepository teacherRepository;
	@Autowired
	ClassRepository classRepository;

	// セッションid
	private String session_id = null;

	// 宿題
	@GetMapping(value = "/homework")
	public String Get_Homework(Model model) {
		// セッションがあるかをチェック
		if (!session_manage.Check_SessionId(session_id)) {
			return "redirect:login/login";
		} else {
			model.addAttribute("session_mail", session_manage.getSession_mail());
			return "homework/homework";
		}
	}

	// 宿題提出
	@GetMapping(value = "/homeworksubmi")
	public String Get_HomeworkSubmission(Model model) {
		// セッションがあるかをチェック
		if (!session_manage.Check_SessionId(session_id)) {
			return "redirect:login/login";
		} else {
			Boolean teacher_flag;
			// 教師か生徒かを判別
			if (!(teacherRepository.SearchMailAddress(session_manage.getSession_mail()))) {
				//生徒
				teacher_flag = false;
				System.out.println("生徒");
			} else {
				//教師
				teacher_flag = true;
				System.out.println("教師");
				// 所属学校コード取得
				TeacherEntity teacherEntity = new TeacherEntity();
				teacherEntity = teacherRepository.SearchSchoolCode(session_manage.getSession_mail());
				int school_code = teacherEntity.getSchool().getSchoolcode();
				// 1年のクラスデータを取得
				List<ClassEntity> list_classEntity = classRepository.SearchSchoolYear(school_code, 1);
				model.addAttribute("list_classEntity", list_classEntity);
				model.addAttribute("session_mail", session_manage.getSession_mail());
				model.addAttribute("school_code", school_code);
			}
			model.addAttribute("flag", teacher_flag);
			model.addAttribute("session_id", session_manage.Get_SessionId(session_id));
			return "homework/homeworksubmi";
		}
	}

//	// 提出状況
//	@GetMapping(value = "/homework_submistatus")
//	public String Get_HomeworkSubmissionStatus() {
//		// セッションがあるかをチェック
//		if (!session_manage.Check_SessionId(session_id)) {
//			return "redirect:login/login";
//		} else {
//			return "homework/homework_submistatus";
//		}
//	}
}