package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
	
	// /auth를 붙이는 이유는 인증이 안된 사용자들이 출입할수 있는 경로를 정하기 위함 (/auth/**)
	// 그냥 주소가 / 이면 index.jsp 허용
	// static 이하에 있는 /js/**, /css/**, /image/**
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
}
