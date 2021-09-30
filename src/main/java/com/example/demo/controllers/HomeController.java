package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//ホーム
@Controller
public class HomeController {

	// ホーム画面に遷移
	@GetMapping(value = "/home")
	public String Home() {
		return "home";
	}
}