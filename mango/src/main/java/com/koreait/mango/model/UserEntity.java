package com.koreait.mango.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEntity {	// Entity - 테이블과 1:1 매칭
	
	private int userPk;
	private String provider;
	private String uid;
	private String upw;
	private String email;
	private String profileImg;
	private String nm;
	private String regDt;
}
