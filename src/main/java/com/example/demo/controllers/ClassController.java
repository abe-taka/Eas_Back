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

//授業
@Controller
public class ClassController {
	@Autowired
	SessionManage session_manage;
	@Autowired
	TeacherRepository teacherRepository;
	@Autowired
	ClassRepository classRepository;

	// セッションid
	private String session_id = null;

	// 授業選択
	@GetMapping(value = "/roomselect")
	public String Get_RoomSelect(Model model) {
		// セッションがあるかをチェック
		if (!(session_manage.Check_SessionId(session_id))) {
			return "redirect:login/login";
		} else {
			try {
				// 所属学校コード取得
				TeacherEntity teacherEntity = new TeacherEntity();
				teacherEntity = teacherRepository.SearchSchoolCode(session_manage.getSession_mail());
				int school_code = teacherEntity.getSchool().getSchoolcode();
				// 1年のクラスデータを取得
				List<ClassEntity> list_classEntity = classRepository.SearchSchoolYear(school_code, 1);
				model.addAttribute("list_classEntity", list_classEntity);
				model.addAttribute("session_mail", session_manage.getSession_mail());
				model.addAttribute("school_code", school_code);
	
				return "class/roomselect";
			}catch(Exception e) {
				System.out.println(e);
				return "access-denied/access-denied";
			}
		}
	}

	// 授業
	@GetMapping(value = "/class")
	public String Get_Class(Model model) {
		// セッションがあるかをチェック
		if (!(session_manage.Check_SessionId(session_id))) {
			return "redirect:login/login";
		} else {
			Boolean teacher_flag;
			// 教師かどうかを判別
			if (!(teacherRepository.SearchMailAddress(session_manage.getSession_mail()))) {
				//ユーザーは生徒
				teacher_flag = false;
			} else {
				//ユーザーは教師
				teacher_flag = true;
			}
			model.addAttribute("flag", teacher_flag);
			model.addAttribute("session_id", session_manage.Get_SessionId(session_id));
			return "class/class";
		}
	}
}