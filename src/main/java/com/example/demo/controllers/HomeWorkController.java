package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.components.SessionManage;

//宿題
@Controller
public class HomeWorkController {

	@Autowired
	SessionManage session_manage;

	// セッション用変数
	private String session_name = null;

	// 宿題
	@GetMapping(value = "/homework")
	public String Homework(Model model) {
		// セッションがあるかをチェック
		session_name = session_manage.Get_SessionName(session_name);
		if (session_name.equals("anonymousUser")) {
			return "redirect:login/login";
		} else {
			model.addAttribute("session_name", session_name);
			return "homework/homework";
		}
	}

	// 宿題提出
	@GetMapping(value = "/homeworksubmi")
	public String HomeworkSubmission() {
		// セッションがあるかをチェック
		if (!session_manage.Get_SessionFlag(session_name)) {
			return "redirect:login/login";
		} else {
			return "homework/homeworksubmi";
		}
	}

	// 提出状況
	@GetMapping(value = "/homework_submistatus")
	public String HomeworkSubmissionStatus() {
		// セッションがあるかをチェック
		if (!session_manage.Get_SessionFlag(session_name)) {
			return "redirect:login/login";
		} else {
			return "homework/homework_submistatus";
		}
	}
}