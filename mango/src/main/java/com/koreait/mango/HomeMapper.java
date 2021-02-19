package com.koreait.mango;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.mango.model.UserEntity;

@Mapper
public interface HomeMapper {
	int insertUser(UserEntity param);	// 회원가입
	UserEntity selUser(UserEntity param);	// 로그인
}
