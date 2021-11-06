package com.example.demo.components;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.sendsockets.SocketMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//JSON変換コーポネント
@Component
public class JsonConversion {

	@Autowired
	ObjectMapper objectMapper;

	// リストをJSON形式で変換する
	public String listToJSON(List<?> list) {
		// json変換取得変数
		String json = null;

		// 変換処理
		try {
			json = objectMapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			System.err.println(e);
			System.out.println("listToJSON():fail");
		}
		return json;
	}

	// SocketMessageオブジェクトをJSON形式で変換する
	public String SocketToJSON(SocketMessage socketMessage) {
		// json変換取得変数
		String json = null;

		// 変換処理
		try {
			json = objectMapper.writeValueAsString(socketMessage);
		} catch (JsonProcessingException e) {
			System.err.println(e);
			System.out.println("SocketToJSON():fail");
		}
		return json;
	}
}