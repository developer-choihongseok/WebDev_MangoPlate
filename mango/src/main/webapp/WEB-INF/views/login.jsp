<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LOGIN</title>
</head>
<body>
	<h1>로그인</h1>
	
	<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION }">
		<div>로그인에 실패하였습니다.</div>
		<c:remove var="SPRING_SECURITY_LAST_EXCEPTION" scope="session" />
	</c:if>
	
	<div>
		<form action="/login" method="post">	<!-- 컨트롤러에 login을 따로 만들 필요없이, 시큐리티가 인식을 해서 로그인처리를 한다. -->
			<div><input type="text" name="username" placeholder="아이디 입력"></div>	<!-- username 을 id로 인식한다. -->
			<div><input type="password" name="password" placeholder="비밀번호 입력"></div>
			<button type="submit">로그인</button>
			<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
		</form>
	</div>
	
	<div>
		<a href="javascript:;" class="btn_social" data-social="facebook">페이스북 로그인</a>
		<a href="javascript:;" class="btn_social" data-social="google">구글 로그인</a>
		<a href="javascript:;" class="btn_social" data-social="kakao">카카오톡 로그인</a>
		<a href="javascript:;" class="btn_social" data-social="naver">네이버 로그인</a>
	</div>
	
	<div>
		<a href="/join">회원가입</a>
	</div>

	<script> 
		let socials = document.getElementsByClassName('btn_social')
		
		for(let social of socials) { 
			social.addEventListener('click', function(){ 
				let socialType = this.getAttribute('data-social')
				location.href="/oauth2/authorization/" + socialType 
			}) 
		} 
	</script>
</body>
</html>