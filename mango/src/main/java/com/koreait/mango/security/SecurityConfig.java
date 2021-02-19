package com.koreait.mango.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

@Configuration	// "설정 파일"을 의미.
@EnableWebSecurity // Spring Security를 활성화.
@RequiredArgsConstructor	// 앞에 final 붙은 친구를 자동으로 @Autowired를 붙여준다.
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	final private UserDetailsService memberService;
	
	/* 기본적인 Security 설정[Start] */
	
	@Bean
	public PasswordEncoder passwordEncoder() {	// PasswordEncoder를 이용해서 자동으로 암호화 시켜준다.
		return new BCryptPasswordEncoder();	// 암호화 떄 쓰는 라이브러리 객체. 암호화 하는 걸 나중에 바꾸고 싶을 때, 여기만 교체하면 된다.
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/res/**");	// antMatchers : "주소 매핑"을 의미. 즉, "시큐리티가 /res/** 의 모든 파일들을 무시하겠다"(관여하지 않겠다)는 의미.
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{	// 설정 파일이기 때문에 한번만 실행이 될 것이다.
		http.csrf().disable();	// 제 3자가 공격하는걸 막아주는 역할을 한다?
		
		http.authorizeRequests()
			.antMatchers("/user/**").hasRole("USER")	// 1차 주소가 user인 친구는 무조건 권한이 USER 이어야 한다.	USER = ROLE_USER
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/**").permitAll();	 // 전부 접근권한 부여(가장 상단에 위치 X)
		
		http.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/home");
//			.permitAll();
		
		http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login")
			.invalidateHttpSession(true);	// "세션을 다 비운다." 의미
		
		http.exceptionHandling()
			.accessDeniedPage("/denied");
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception{	// 설정 파일이기 때문에 한번만 실행이 될 것이다.
		 auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());	// 로그인 시 passwordEncoder()를 실행해서 자동으로 암호화를 한다.
	}
	
	/* 기본적인 Security 설정[End] */
	
}
