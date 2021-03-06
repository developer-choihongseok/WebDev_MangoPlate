package com.koreait.mango;

import org.springframework.stereotype.Service;

import com.koreait.mango.model.UserEntity;
import com.koreait.mango.model.security.UserPrincipal;
import com.koreait.mango.security.IAuthenticationFacade;
import com.koreait.mango.security.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeService {
	
	final HomeMapper mapper;	
	final UserDetailsServiceImpl userDetailsService;
	final IAuthenticationFacade authenticationFacade;
	
	// FORM LOGIN
	public void home() {		
		UserPrincipal user = authenticationFacade.getUserPrincipal();	// 항상 권한을 검사.
		System.out.println("userPk(2) : " + user.getUserPk());
	}
	
	public int mangoJoin(UserEntity p) {		
		return userDetailsService.join(p);
	}
	
	/*
	public UserEntity selUser(UserEntity p) {
		return mapper.selUser(p);
	}
	*/
}
