package com.koreait.mango;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
//	@GetMapping("/home")
//	public void home() {}
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
}