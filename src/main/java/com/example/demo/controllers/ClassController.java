package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.components.DateTimeComponent;
import com.example.demo.components.SessionManage;
import com.example.demo.entities.ClassEntity;
import com.example.demo.entities.EnterExitEntity;
import com.example.demo.entities.StudentEntity;
import com.example.demo.entities.TeacherEntity;
import com.example.demo.entities.TimetabletimeEntity;
import com.example.demo.forms.VoiceRecognitionForm;
import com.example.demo.repositories.ClassRepository;
import com.example.demo.repositories.EnterExitRepository;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.repositories.TeacherRepository;
import com.example.demo.repositories.TimetabletimeRepository;

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
	@Autowired
	DateTimeComponent datetime;
	@Autowired
	EnterExitRepository enterexitRepository;
	@Autowired
	TimetabletimeRepository timetabletimeRepository;
	@Autowired
	VoiceRecognitionForm voice_Form;
	
	// 授業選択
	@GetMapping(value = "/roomselect")
	public String Get_RoomSelect(Model model,RedirectAttributes redir) {
		// セッションがあるかをチェック
		if (session_manage.getSession_mail() == null) {
			return "redirect:/";
		}
		else {
			try {
				// 所属学校コード取得
				TeacherEntity teacherEntity = new TeacherEntity();
				teacherEntity = teacherRepository.SearchTeacher(session_manage.getSession_mail());
				int school_code = teacherEntity.getSchool().getSchoolcode();
				model.addAttribute("school_code", school_code);
				
				//所属学校の学年のデータを取得
				List<String> list_schoolyear = classRepository.findBySchoolyear(school_code);
				model.addAttribute("list_schoolyear", list_schoolyear);
				
				// 1年のクラスデータを取得
				List<ClassEntity> list_classEntity = classRepository.SearchSchoolclassBySchoolyear(school_code, 1);
				model.addAttribute("list_classEntity", list_classEntity);
				
				//名前、メールアドレスを取得
				String session_name = session_manage.getSession_name();
				String session_mail = session_manage.getSession_mail();
				model.addAttribute("session_name", session_name);
				model.addAttribute("session_mail", session_mail);
				
				return "class/roomselect";
			} catch (Exception e) {
				//エラーハンドリング
				System.out.println("[ClassController]Get_RoomSelect ： " + e);
				return "redirect:/error/error";
			}
		}
	}

	// 授業(先生側)
	@GetMapping(value = "/teacherclass")
	public String Get_TeacherClass(Model model,String classid) {
		// セッションがあるかをチェック
		if (session_manage.getSession_mail() == null) {
			return "redirect:/";
		}
		else {
			//セッションidを取得
			String session_id = session_manage.Get_SessionId();
			model.addAttribute("session_id", session_id);
			
			//名前を取得
			String session_name = session_manage.getSession_name();
			model.addAttribute("session_name", session_name);
			
			//クラスid
			model.addAttribute("classid", classid);
			//フォームにセットする
			voice_Form.setClassid(classid);
			voice_Form.setTeacher_sessionid(session_id);
			
			return "class/teacherclass";
		}
	}
	

	// 授業(学生側)
	@GetMapping(value = "/studentclass")
	public String Get_StudentClass(Model model,String classid,String enterid) {
		// セッションがあるかをチェック
		if (session_manage.getSession_mail() == null) {
			return "redirect:/";
		}
		else {
			//セッションidを取得
			String session_id = session_manage.Get_SessionId();
			model.addAttribute("session_id", session_id);
			
			//名前を取得
			String session_name = session_manage.getSession_name();
			model.addAttribute("session_name", session_name);
			
			//クラスid
			model.addAttribute("classid", classid);
			
			//入室ログのid
			model.addAttribute("enterid", enterid);
			
			//出席番号を取得
			StudentEntity studentEntity = new StudentEntity();
			studentEntity = studentRepository.SearchStudent(session_manage.getSession_mail());
			model.addAttribute("student_classno", studentEntity.getClassno());
			
			return "class/studentclass";
		}
	}
	
	
	//入室ログ、授業画面に遷移
	@PostMapping(value="/enterprocess")
	public String Post_EnterProcess(String classid,String time_period,RedirectAttributes redir) {
		try {
			// 学生情報を取得
			String session_mail = session_manage.getSession_mail();
			StudentEntity studentEntity = new StudentEntity();
			studentEntity = studentRepository.SearchStudent(session_mail);
			
			//学校コードの取得
			Optional<ClassEntity> classEntity = classRepository.findById(studentEntity.getClassentity().getClassid());
			
			// 年月日を取得
			String real_time = datetime.Get_YearMonthDate();
			
			//時間割時間Entityデータの取得
			TimetabletimeEntity timetabletimeEntity = new TimetabletimeEntity();
			int int_time_period = Integer.parseInt(time_period);
			timetabletimeEntity = timetabletimeRepository.SearchIdByPeriodAndSchoolCode(int_time_period, classEntity.get().getSchool().getSchoolcode());

			// Entityにセット
			EnterExitEntity enterexitEntity = new EnterExitEntity();
			enterexitEntity.setStudent(studentEntity);
			enterexitEntity.setEntertime(real_time);
			enterexitEntity.setTimetabletime(timetabletimeEntity);
			
			// 保存
			enterexitRepository.save(enterexitEntity);
			
			//退出ログ用に保存したidを取得
			enterexitEntity = enterexitRepository.SearchIdAndTimeForEnterTime(session_mail);
			String enterid = String.valueOf(enterexitEntity.getEnterexitid());
			
			redir.addAttribute("classid", classid);
			redir.addAttribute("enterid", enterid);
			return "redirect:/class/studentclass";
		} catch (Exception e) {
			//エラーハンドリング
			System.out.println("[ClassController]Post_EnterProcess ： " + e);
			return "redirect:/error/error";
		}
	}
}