package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

// 不正アクセスハンドリング
@Controller
public class AccessDeniedController {

	@GetMapping(value = "/access-denied")
	public String Get_AccessDenied() {

		return "access-denied/access-denied";
	}

	@PostMapping(value = "/access-denied")
	public String Post_AccessDenied() {

		return "access-denied/access-denied";
	}
}