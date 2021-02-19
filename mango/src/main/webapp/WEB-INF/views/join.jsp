<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JOIN</title>
</head>
<body>
	<h1>회원가입</h1>
	
	<div>
		<form action="/join" method="post">
			<div><input type="text" name="uid" placeholder="아이디 입력"></div>
			<div><input type="password" name="upw" placeholder="비밀번호 입력"></div>
			<div><input type="text" name="email" placeholder="이메일 입력"></div>
			<div><input type="text" name="nm" placeholder="이름 입력"></div>
			<button type="submit">회원가입</button>
			<input type="hidden" name="provider" value="mango">
			<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">	<!-- 꼭 보내주어야 한다! -->
		</form>
	</div>
	
	<div>
		<a href="/login">로그인</a>
	</div>
</body>
</html>