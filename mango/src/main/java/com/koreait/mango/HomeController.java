package com.koreait.mango;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.koreait.mango.model.UserEntity;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
	
	final HomeService service;
	
//	@GetMapping("/home")
//	public void home() {}
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/home")
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	public void home2() {
		service.home();
	}
	
	@GetMapping("/denied")
	public void denied() {}
	
	@GetMapping("/login")
	public void login(@ModelAttribute("userEntity") UserEntity userEntity) {
		userEntity.setUid("admin");	// login.html에 name의 value값이 들어간다.
	}
	
	@GetMapping("/join")
	public void join() {}
	
	@PostMapping("/join")
	public String join(UserEntity param) { // param : 회원가입에서 필요한 4개의 정보를 받아 올 것이다.
		int result = service.mangoJoin(param);
		System.out.println("result : " + result);
		
		return "redirect:/login";
	}
}
