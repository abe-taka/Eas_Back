package com.example.demo.rests;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//Restコントローラー(授業)
@RestController
public class RestClassController {

	@Autowired
	ObjectMapper objectMapper;
	
	// 組データ取得処理(授業選択画面)
	@PostMapping("/rest/room_select")
	public String RestRoomSelect(@RequestParam("js_grade") int grade) {
		// 受け取り
		System.out.println("js_grade" + String.valueOf(grade));

		return String.valueOf(grade);
	}
}