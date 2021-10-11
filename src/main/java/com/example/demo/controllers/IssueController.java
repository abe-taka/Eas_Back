package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.components.SessionManage;

//問題
@Controller
public class IssueController {

	@Autowired
	SessionManage session_manage;

	// セッション用変数
	private String session_name = null;

	// 問題作成
	@GetMapping(value = "/issuecreate")
	public String IssueCreate(Model model) {
		// セッションがあるかをチェック
		session_name = session_manage.Get_SessionName(session_name);
		if (session_name.equals("anonymousUser")) {
			return "redirect:login/login";
		} else {
			model.addAttribute("session_name", session_name);
			return "issue/issuecreate";
		}
	}

	// 問題管理
	@GetMapping(value = "/issuemanage")
	public String IssueManage() {
		// セッションがあるかをチェック
		if (!session_manage.Get_SessionFlag(session_name)) {
			return "redirect:login/login";
		} else {
			return "issue/issuemanage";
		}
	}
}
