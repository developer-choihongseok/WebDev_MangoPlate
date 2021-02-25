package com.koreait.mango.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.koreait.mango.HomeMapper;
import com.koreait.mango.model.UserEntity;
import com.koreait.mango.security.model.UserPrincipal;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private HomeMapper mapper;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {	// 로그인 한 사람에게 정보를 줘야한다. DB처리를 여기서 한다.
		return loadUserByUsername("mango", uid);
	}
	
	public UserDetails loadUserByUsername(String provider, String uid) throws UsernameNotFoundException {
		UserEntity p = new UserEntity();
		p.setProvider(provider);
		p.setUid(uid);	
		
		UserEntity ue = mapper.selUser(p);
		
		if(ue == null) {
			return null;
		}
		return new UserPrincipal(ue);
	}
	
	public int join(UserEntity param) {
		if(param.getUpw() != null && !"".equals(param.getUpw())) {
			param.setUpw(encoder.encode(param.getUpw())); 
		}
		return mapper.insertUser(param);
	}
}
