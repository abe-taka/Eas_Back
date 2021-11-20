package com.example.demo.components;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//JSON変換コーポネント
@Component
public class JsonConversion {

	@Autowired
	ObjectMapper objectMapper;

	// リストをJSON形式で変換
	public String listToJSON(List<?> list) {
		String json = null;
		// 変換処理
		try {
			json = objectMapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			System.out.println("リストJson変換失敗" + e);
		}
		return json;
	}

	// オブジェクトをJSON形式に変換
	public String ObjectToJSON(Object object) {
		String json = null;
		// 変換処理
		try {
			json = objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			System.out.println("オブジェクトJson変換失敗" + e);
		}
		return json;
	}
}