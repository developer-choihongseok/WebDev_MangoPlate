package com.koreait.mango.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.koreait.mango.HomeMapper;
import com.koreait.mango.model.UserEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{
	
	final HomeMapper mapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {	// 로그인 한 사람에게 정보를 줘야한다. DB처리를 여기서 한다.
		UserEntity param = new UserEntity();
		param.setUid(username);

		return mapper.selUser(param);
	}
	
	public int join(UserEntity param) {
		return mapper.insertUser(param);
	}
}
