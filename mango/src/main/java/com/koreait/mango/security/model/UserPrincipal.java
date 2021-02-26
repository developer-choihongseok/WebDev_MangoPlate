package com.koreait.mango.security.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.koreait.mango.model.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPrincipal extends UserEntity implements OAuth2User, UserDetails {	// OAuth2User : 소셜 로그인을 하지 않는다면, 필요가 없다. 하지만 시큐리티 할려면 UserDetails을 implements를 해주어야 한다.
	
	private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;	// 응답되는 곳이 저장되는 곳이다.
    
    private UserPrincipal(UserEntity user) {	// public을 private으로 해도 된다. 이유: create()에서만 쓰기 때문이다.
    	this.setUserPk(user.getUserPk());
    	this.setUid(user.getUid());
    	this.setUpw(user.getUpw());
    	this.setEmail(user.getEmail());
    	this.setAuth(user.getAuth());
    	
    	authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getAuth()));	// 1개의 권한 정보를 만들어준다.
    }
    
    // OAuth2 관련.
    public static UserPrincipal create(UserEntity user, Map<String, Object> attributes) {	// OAuth2User 때문에 만들어졌다.
    	 UserPrincipal userPrincipal = UserPrincipal.create(user);
         userPrincipal.setAttributes(attributes);
         return userPrincipal;
    }
    
    // 시큐리티 폼 로그인 관련.
    public static UserPrincipal create(UserEntity user) {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getAuth()));
        return new UserPrincipal(user);
    }
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {	// 시큐리티에서만 사용한다.
		return authorities;
	}
	
	@Override
	public String getPassword() {	// 시큐리티에서만 사용한다.
		return this.getUpw();
	}
	@Override
	public String getUsername() {	// 시큐리티에서만 사용한다.
		return this.getUid();
	}
	@Override
	public boolean isAccountNonExpired() {	// 시큐리티에서만 사용한다.
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {	// 시큐리티에서만 사용한다.
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {	// 시큐리티에서만 사용한다.
		return true;
	}
	@Override
	public boolean isEnabled() {	// 시큐리티에서만 사용한다.
		return true;
	}

	@Override
	public Map<String, Object> getAttributes() {	// 	OAuth2User에서 사용.
		return attributes;
	}

	@Override
	public String getName() {	// OAuth2User에서 사용.
		return getUid();
	}
}
