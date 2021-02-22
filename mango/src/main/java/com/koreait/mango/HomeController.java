package com.koreait.mango;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.koreait.mango.model.UserEntity;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
	
	final HomeService service;
	
//	@GetMapping("/home")
//	public void home() {}
	
	@GetMapping({"/", "/home"})
	public String home() {
		return "home";
	}
	
	@GetMapping("/denied")
	public void denied() {}
	
	@GetMapping("/login")
	public void login() {}
	
	@GetMapping("/join")
	public void join() {}
	
	@PostMapping("/join")
	public String join(UserEntity param) { // param : 회원가입에서 필요한 4개의 정보를 받아 올 것이다.
		service.join(param);
		return "redirect:/login";
	}
}
