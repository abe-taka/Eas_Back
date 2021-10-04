package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entities.LoginEntity;
import com.example.demo.forms.LoginForm;
import com.example.demo.repositories.LoginRepository;

//ログイン
@Controller
public class LoginController {

	@Autowired
	LoginRepository loginRepostitory;

	// ログイン画面
	@GetMapping(value = "/")
	public String Login(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}

	// ログイン処理
	@PostMapping(value = "/loginprocess")
	public String LoginProcess(LoginForm loginForm, RedirectAttributes redirectAttr) {

		// Formで受け取ったデータを変数に変換
		int id = loginForm.getId();
		String name = loginForm.getName();

		LoginEntity loginEntity = new LoginEntity();

		try {
			// DBに検索
			loginEntity = loginRepostitory.Login(id, name);
			// 成功、失敗を判定
			if (String.valueOf(loginEntity.getId()).equals("")) {
				// ない場合→失敗(ログインに戻る)
				return "redirect:/";
			} else {
				// ある場合→成功(ホーム画面に戻る)
				redirectAttr.addFlashAttribute("login_error", "ログイン失敗");
				return "redirect:home";
			}
		} catch (Exception e) {
			System.out.println("ログイン処理エラー" + e);
			redirectAttr.addFlashAttribute("login_error", "ログイン失敗");
			return "redirect:/";
		}
	}
}