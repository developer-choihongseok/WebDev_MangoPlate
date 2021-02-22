package com.koreait.mango.security;

public enum SocialType {
	FACEBOOK("facebook"),	// "생성자"로 생각하면 좋다.
	GOOGLE("google"),
	KAKAO("kakao"),
	NAVER("naver");
	
	private final String ROLE_PREFIX = "ROLE_";
	private String name;
	
	SocialType(String name) {
		this.name = name;
	}
	
	public String getRoleType() {
		return ROLE_PREFIX + name.toUpperCase();	// ex) ROLE_FACEBOOK, ROLE_GOOGLE..
	}
	
	public String getValue() {
		return name;
	}
	
	public boolean isEquals(String authority) {
		return this.getRoleType().equals(authority);
	}
}
