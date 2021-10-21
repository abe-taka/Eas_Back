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

	// セッションid
	private String session_id = null;

	// 問題作成
	@GetMapping(value = "/issuecreate")
	public String Get_IssueCreate(Model model) {
		// セッションがあるかをチェック
		if (!session_manage.Check_SessionId(session_id)) {
			return "redirect:login/login";
		} else {
			return "issue/issuecreate";
		}
	}

	// 問題管理
	@GetMapping(value = "/issuemanage")
	public String Get_IssueManage() {
		// セッションがあるかをチェック
		if (!session_manage.Check_SessionId(session_id)) {
			return "redirect:login/login";
		} else {
			return "issue/issuemanage";
		}
	}
}
