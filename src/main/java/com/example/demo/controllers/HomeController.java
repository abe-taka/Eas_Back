package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.components.SessionManage;
import com.example.demo.forms.LoginForm;
import com.example.demo.repositories.TeacherRepository;

//ホーム111111
@Controller
public class HomeController {
	@Autowired
	SessionManage session_manage;
	@Autowired
	TeacherRepository teacherRepository;

	// セッションid
	private String session_id = null;
			
	// ホーム画面(ログイン成功時の画面)
	@GetMapping(value = "/home")
	public String Get_Home(Model model,@AuthenticationPrincipal OidcUser user) {
		System.out.println("認証メールアドレス" + user.getEmail());
		String session_mail = user.getEmail();
		//セッションにメールアドレスを格納
		session_manage.setSession_mail(session_mail);
		// セッションがあるかをチェック
		if (!session_manage.Check_SessionId(session_id)) {
			model.addAttribute("loginForm", new LoginForm());
			return "redirect:login/login";
		}
		else {
			//教師か生徒かを調べる
			if(teacherRepository.SearchMailAddress(session_mail)) {
				//教師
				model.addAttribute("flag", "1");
			}else {
				//生徒
				model.addAttribute("flag", "0");
			}
			
			return "home/home";
		}
	}

	// ホーム画面
	@PostMapping(value = "/home")
	public String Post_Home() {
		return "home/home";
	}
}