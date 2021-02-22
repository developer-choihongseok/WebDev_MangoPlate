package com.koreait.mango;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.koreait.mango.model.UserEntity;
import com.koreait.mango.security.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeService {
	
	final UserDetailsServiceImpl userDetailsService;
	final PasswordEncoder passEncoder;
	
	// Mango 회원가입을 통해서 회원가입을 시키는 역할
	public int join(UserEntity param) {
		param.setProvider("mango");
		
		// 비밀번호 암호화.
		String encodePw = passEncoder.encode(param.getPassword());
		param.setUpw(encodePw);
		
		return userDetailsService.join(param);
	}
	
}
