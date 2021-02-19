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
		<form action="/login" method="post">
			<div><input type="text" name="username" placeholder="아이디 입력" value="microform"></div>
			<div><input type="password" name="password" placeholder="비밀번호 입력" value="1212"></div>
			<button type="submit">로그인</button>
			<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
		</form>
	</div>
</body>
</html>