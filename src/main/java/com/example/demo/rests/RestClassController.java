package com.example.demo.rests;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.components.JsonConversion;
import com.example.demo.components.SessionManage;
import com.example.demo.entities.ClassEntity;
import com.example.demo.entities.TeacherEntity;
import com.example.demo.repositories.ClassRepository;
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
	
	// 組データ取得(授業選択画面)
	@PostMapping("/rest/room_select")
	public String RestRoomSelect(@RequestParam("js_schoolyear") int schoolyear) {
		// 所属学校コード取得
		TeacherEntity teacherEntity = new TeacherEntity();
		teacherEntity = teacherRepository.SearchTeacher(session_manage.getSession_mail());
		int school_code = teacherEntity.getSchool().getSchoolcode();
		//クラスデータを取得
		List<ClassEntity> list_classEntity = classRepository.SearchSchoolclassBySchoolyear(school_code, schoolyear);
		
		return jsonConversion.listToJSON(list_classEntity);
	}
}