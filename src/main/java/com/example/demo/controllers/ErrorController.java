package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//エラーハンドリングコントローラー
@Controller
@RequestMapping(value="/error")
public class ErrorController {

	@GetMapping(value = "/error")
	public String Get_Error() {
		
		return "error/error";
	}
}