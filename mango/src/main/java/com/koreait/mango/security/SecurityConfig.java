package com.koreait.mango.security;

import static com.koreait.mango.security.SocialType.FACEBOOK;
import static com.koreait.mango.security.SocialType.GOOGLE;
import static com.koreait.mango.security.SocialType.KAKAO;
import static com.koreait.mango.security.SocialType.NAVER;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

@Configuration	// "설정 파일"을 의미.
@EnableWebSecurity // Spring Security를 활성화.
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor	// 앞에 final 붙은 친구를 자동으로 @Autowired를 붙여준다.
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	final UserDetailsService memberService;
	final CustomOAuth2UserService customOAuth2UserService;
	
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
			.antMatchers("/**").permitAll()	 // 전부 접근권한 부여(가장 상단에 위치 X)
		
			.antMatchers("/facebook").hasAuthority(FACEBOOK.getRoleType())
	        .antMatchers("/google").hasAuthority(GOOGLE.getRoleType())
	        .antMatchers("/kakao").hasAuthority(KAKAO.getRoleType())
	        .antMatchers("/naver").hasAuthority(NAVER.getRoleType())
	        .anyRequest().authenticated()
	        	.and()
		    .oauth2Login()
		    	.userInfoEndpoint()
		    		.userService(customOAuth2UserService)
		    		.and()
	    		.defaultSuccessUrl("/home")
		        .failureUrl("/login")
		        .and()
		    .exceptionHandling() 
				.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));
		
		http.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/home");
//			.permitAll();
		
		http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login")		// 로그인 성공하면 로그인 페이지로 넘어간다.
			.invalidateHttpSession(true);	// "세션을 다 비운다." 의미.
		
		http.exceptionHandling()
			.accessDeniedPage("/denied");
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception{	// 설정 파일이기 때문에 한번만 실행이 될 것이다.
		 auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());	// 로그인 시 passwordEncoder()를 실행해서 자동으로 암호화를 한다.
	}
	
	/* 기본적인 Security 설정[End] */
	
	@Bean
	public ClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties oAuth2ClientProperties,
			@Value("${custom.oauth2.kakao.client-id}") String kakaoClientId,
			@Value("${custom.oauth2.kakao.client-secret}") String kakaoClientSecret,
			@Value("${custom.oauth2.naver.client-id}") String naverClientId,
			@Value("${custom.oauth2.naver.client-secret}") String naverClientSecret) {
		
		List<ClientRegistration> registrations = oAuth2ClientProperties.getRegistration().keySet().stream()
				.map(client -> getRegistration(oAuth2ClientProperties, client))
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
		registrations.add(CustomOAuth2Provider.KAKAO.getBuilder("kakao")
				.clientId(kakaoClientId)
				.clientSecret(kakaoClientSecret)				
				.build()
		);		
		registrations.add(CustomOAuth2Provider.NAVER.getBuilder("naver")
				.clientId(naverClientId)
				.clientSecret(naverClientSecret)				
				.build()
		);
		return new InMemoryClientRegistrationRepository(registrations);
	}

	private ClientRegistration getRegistration(OAuth2ClientProperties clientProperties, String client) {
		if ("google".equals(client)) {
			OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("google");
			return CommonOAuth2Provider.GOOGLE.getBuilder(client)
					.clientId(registration.getClientId())
					.clientSecret(registration.getClientSecret())
					.scope("email", "profile")
					.build();
		} else if ("facebook".equals(client)) {
			OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("facebook");
			return CommonOAuth2Provider.FACEBOOK.getBuilder(client)
					.clientId(registration.getClientId())
					.clientSecret(registration.getClientSecret())
					.userInfoUri("https://graph.facebook.com/me?fields=id,name,email,link")
					.scope("email")
					.build();
		}
		return null;
	}
}
