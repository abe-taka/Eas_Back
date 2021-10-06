package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.components.Session_manage;
import com.example.demo.forms.LoginForm;

//ホーム
@Controller
public class HomeController {
	@Autowired
	Session_manage session_manage;

	// セッション用変数
	private String session_name = null;
			
	// ホーム画面
	@GetMapping(value = "/home")
	public String Home(Model model) {
		// セッションがあるかをチェック
		if (!session_manage.Get_SessionName(session_name)) {
			model.addAttribute("loginForm", new LoginForm());
			return "redirect:login";
		}
		else {
			return "home";
		}
	}

	// ホーム画面
	@PostMapping(value = "/home")
	public String Post_Home() {
		return "home";
	}
}