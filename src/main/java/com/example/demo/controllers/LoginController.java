package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.components.SessionManage;
import com.example.demo.entities.LoginEntity;
import com.example.demo.forms.LoginForm;
import com.example.demo.repositories.LoginRepository;

//ログイン
@Controller
public class LoginController {
	@Autowired
	LoginRepository loginRepostitory;
	@Autowired
	SessionManage session_manage;

	// セッション用変数
	private String session_name = null;

	// ログイン画面
	@GetMapping(value = "/")
	public String Login(Model model) {
		// セッションがあるかをチェック
		if (!session_manage.Get_SessionFlag(session_name)) {
			model.addAttribute("loginForm", new LoginForm());
			return "login/login";
		} else {
			return "redirect:home/home";
		}
	}

	// 新規登録画面
	@GetMapping(value = "/signup")
	public String Signup(Model model) {
		// セッションがあるかをチェック
		if (!session_manage.Get_SessionFlag(session_name)) {
			model.addAttribute("loginForm", new LoginForm());
			return "login/signup";
		} else {
			return "redirect:home/home";
		}
	}

	// 新規登録処理
	@PostMapping(value = "/signupprocess")
	public String SignupProcess(Model model, LoginForm loginForm) {
		// パスワードのハッシュ化
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		try {
			LoginEntity loginEntity = new LoginEntity();
			loginEntity.setName(loginForm.getName());
			loginEntity.setPassword(encoder.encode(loginForm.getPassword()));
			// 保存
			loginRepostitory.save(loginEntity);
			return "redirect:login/login";
		} catch (Exception e) {
			System.out.println(e);
			return "redirect:login/signup";
		}
	}
}