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
	
	// FORM 로그인
	@Override
	public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {	// 로그인 한 사람에게 정보를 줘야한다. DB처리를 여기서 한다. 꼭 만들어줘야한다!!
		return loadUserByUsername("mango", uid);
	}
	
	// FORM 로그인 & 소셜 로그인 사용
	public UserDetails loadUserByUsername(String provider, String uid) throws UsernameNotFoundException {
		UserEntity p = new UserEntity();
		p.setProvider(provider);
		p.setUid(uid);	
		
		UserEntity ue = mapper.selUser(p);
		
		if(ue == null) {
			return null;
		}
		return UserPrincipal.create(ue);	// 로그인 성공.
	}
	
	//  소셜 로그인(암호화 할 필요가 없기 때문에 별도의 암호화를 해주지 X), FORM 로그인이 함께 쓰인다.
	public int join(UserEntity param) {
		if(param.getUpw() != null && !"".equals(param.getUpw())) {
			param.setUpw(encoder.encode(param.getUpw()));	// 비밀번호 암호화를 할 때 이것을 꼭 써야한다!!
		}
		return mapper.insertUser(param);
	}
}
