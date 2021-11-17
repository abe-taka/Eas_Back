package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.components.SessionManage;
import com.example.demo.entities.ClassEntity;
import com.example.demo.entities.StudentEntity;
import com.example.demo.entities.TeacherEntity;
import com.example.demo.repositories.ClassRepository;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.repositories.TeacherRepository;

//授業
@Controller
@RequestMapping(value = "/class")
public class ClassController {
	@Autowired
	SessionManage session_manage;
	@Autowired
	TeacherRepository teacherRepository;
	@Autowired
	ClassRepository classRepository;
	@Autowired
	StudentRepository studentRepository;

	// セッションid
	private String session_id = null;

	// 授業選択
	@GetMapping(value = "/roomselect")
	public String Get_RoomSelect(Model model) {
		// セッションがあるかをチェック
		if (session_manage.getSession_mail() == null) {
			return "redirect:/";
		} else {
			try {
				// 所属学校コード取得
				TeacherEntity teacherEntity = new TeacherEntity();
				teacherEntity = teacherRepository.SearchTeacher(session_manage.getSession_mail());
				int school_code = teacherEntity.getSchool().getSchoolcode();
				
				//所属学校の学年のデータを取得
				System.out.println("学校コード" + school_code);
				List<String> list_object = classRepository.findBySchoolyear(school_code);
				//System.out.println(list_object.get(0));
				for (int i=0; i < list_object.size(); i++) {
					System.out.println("####" + list_object.get(i).toString());
				}
				model.addAttribute("list_schoolyear", list_object);
				
				// 1年のクラスデータを取得
				List<ClassEntity> list_classEntity = classRepository.SearchSchoolclassBySchoolyear(school_code, 1);
				model.addAttribute("list_classEntity", list_classEntity);
				model.addAttribute("session_mail", session_manage.getSession_mail());
				model.addAttribute("school_code", school_code);
				
				//名前を取得
				String session_name = session_manage.getSession_name();
				model.addAttribute("session_name", session_name);

				return "class/roomselect";
			} catch (Exception e) {
				System.out.println(e);
				return "access-denied/access-denied";
			}
		}
	}

	// 授業(先生側)
	@GetMapping(value = "/teacherclass")
	public String Get_TeacherClass(Model model,String classid) {
		// セッションがあるかをチェック
		if (session_manage.getSession_mail() == null) {
			return "redirect:/";
		} else {
			//セッションidをthymeleafに渡す
			model.addAttribute("session_id", session_manage.Get_SessionId(session_id));
			
			//名前を取得
			String session_name = session_manage.getSession_name();
			model.addAttribute("session_name", session_name);
			
			//フラグ生成
			model.addAttribute("flag", true);
			
			//クラスid
			model.addAttribute("classid", classid);
			
			return "class/teacherclass";
		}
	}
	

	// 授業(学生側)
	@GetMapping(value = "/studentclass")
	public String Get_StudentClass(Model model,String classid) {
		// セッションがあるかをチェック
		if (session_manage.getSession_mail() == null) {
			return "redirect:/";
		} else {
			//セッションidをthymeleafに渡す
			model.addAttribute("session_id", session_manage.Get_SessionId(session_id));
			
			//名前を取得
			String session_name = session_manage.getSession_name();
			model.addAttribute("session_name", session_name);
			
			//フラグ生成
			model.addAttribute("flag", false);
			
			//クラスid
			model.addAttribute("classid", classid);
			
			//出席番号を取得
			StudentEntity studentEntity = new StudentEntity();
			studentEntity = studentRepository.SearchStudent(session_manage.getSession_mail());
			model.addAttribute("student_classno", studentEntity.getClassno());
			
			return "class/studentclass";
		}
	}
}