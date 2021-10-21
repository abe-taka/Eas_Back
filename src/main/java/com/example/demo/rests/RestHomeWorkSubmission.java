package com.example.demo.rests;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.components.JsonConversion;
import com.example.demo.entities.ClassEntity;
import com.example.demo.repositories.ClassRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

//Restコントローラー(宿題提出)
@RestController
public class RestHomeWorkSubmission {

	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	ClassRepository classRepository;
	@Autowired
	JsonConversion jsonConversion;
	
	// 組データ取得処理(授業選択画面)
//	@PostMapping("/rest/room_select")
//	public String RestRoomSelect(@RequestParam("js_schoolyear") int schoolyear,@RequestParam("js_schoolcode") int schoolcode) {
//		//宿題提出状況を取得
//		List<ClassEntity> list_classEntity = classRepository.SearchSchoolYear(schoolcode, schoolyear);
//		
//		return jsonConversion.listToJSON(list_classEntity);
//	}
}