<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
	<title>教师注册</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="keywords" content="Baxster Responsive web template, Bootstrap Web Templates, Flat Web Templates, Android Compatible web template,
SmartPhone Compatible web template, free WebDesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web design" />
	<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
	<!-- Bootstrap Core CSS -->
	<link href="css/bootstrap.css" rel='stylesheet' type='text/css' />
	<!-- Custom CSS -->
	<link href="css/style.css" rel='stylesheet' type='text/css' />
	<!-- font CSS -->
	<link rel="icon" href="favicon.ico" type="image/x-icon" >
	<!-- font-awesome icons -->
	<link href="css/font-awesome.css" rel="stylesheet">
	<!-- //font-awesome icons -->
	<!-- js -->
	<script src="js/jquery-1.11.1.min.js"></script>
	<!-- //js -->
</head>
<body class="login-bg">
<div class="login-body">
	<div class="login-heading">
		<h1>教师注册</h1>
	</div>
	<div class="login-info">
		<form action="<c:url value='/user.action!tearegist'/>" method="post">
			<input type="text" class="user" name="user_no" value="${requestScope.user_no }" placeholder="职工号" required="">
			<input type="text" class="user" name="name" value="${requestScope.name }" placeholder="姓名" required="">
			<input type="text" class="user" name="email" value="${requestScope.email }" placeholder="邮箱" required="">
			<input type="password" name="password" value="${requestScope.password }" class="lock" placeholder="密码">
			<input type="password" name="repassword" value="${requestScope.repassword }" class="lock" placeholder="确认密码">
			<input type="submit" name="Sign In" value="注册">
			<div class="signup-text">
				<a href="<c:url value='/user.action!tologin'/>">返回登陆</a>
			</div>
		</form>
	</div>
</div>
<div class="go-back login-go-back">
	<a href="<c:url value='/user.action!toregist'/>">学生注册</a>
</div>
<div class="copyright login-copyright">
	<p>Copyright &copy; 2018 &copy;
		更多链接: <a href="http://www.jxau.edu.cn">江西农业大学官网</a>
		<a href="http://www.baidu.com">百度一下</a> FLYFISH
	</p>
</div>
</body>
</html>