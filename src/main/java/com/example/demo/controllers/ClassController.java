package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.components.SessionManage;

//授業
@Controller
public class ClassController {
	@Autowired
	SessionManage session_manage;

	// セッション用変数
	private String session_name = null;

	// 授業選択
	@GetMapping(value = "/roomselect")
	public String RoomSelect(Model model) {
		// セッションがあるかをチェック
		session_name = session_manage.Get_SessionName(session_name);
		if (session_name.equals("anonymousUser")) {
			return "redirect:login/login";
		} else {
			model.addAttribute("session_name", session_name);
			return "class/roomselect";
		}
	}

	// 音声認識ページ
	@GetMapping(value = "/class")
	public String Class() {
		// セッションがあるかをチェック
		if (!session_manage.Get_SessionFlag(session_name)) {
			return "redirect:login/login";
		} else {
			return "class/class";
		}
	}

	// 1対1
	@GetMapping(value = "/class2")
	public String Class2(Model model) {
		// セッションがあるかをチェック
		session_name = session_manage.Get_SessionName(session_name);
		if (session_name.equals("anonymousUser")) {
			return "redirect:login/login";
		} else {
			model.addAttribute("session_name", session_name);
			return "class/class2";
		}
	}

}