<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/include.jsp" %>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登录</title>
<link rel="stylesheet" type="text/css" href="script/base/css/home.css"/>
<script type="text/javascript" src="script/common/jquery/jquery.js"></script>
<script type="text/javascript" src="script/base/js/home.js"></script>
<script type="text/javascript" src="script/base/js/loginUI.js"></script>

</head>
<body class="loginBody">

<div class="videoDiv divHCenter">
<object	classid=clsid:d27cdb6e-ae6d-11cf-96b8-444553540000 width="987" height="218">
	<param name="movie" value="script/base/css/images/s.swf">
	<param name="quality" value="high">
	<embed src="script/base/css/images/s.swf" quality="high"
		pluginspage="http://www.macromedia.com/go/getflashplayer"
		type="application/x-shockwave-flash" width="987" height="218"></embed>
</object>
</div>

<div class="mainDiv divHCenter">
	<div class="subjectDiv"></div>
	<div class="loginFormDiv">
		<form action="user/login.html" method="post" id="loginForm">
		<div class="loginDiv">
			帐&nbsp;&nbsp;号:&nbsp;<input type="text" name="account" id="account"/><br/>
			<div class="message" id="accountMsg"></div>
			密&nbsp;&nbsp;码:&nbsp;<input type="password" name="password" id="password"/><br/>
			<div class="message" id="validateMsg">${message }</div>
		</div>
		<div class="loginOpration">
			<div class="loginRegister" title="注册帐号"><a href="user/regUI.html">还没帐号？注册~</a></div>
			<div class="loginSubmit"><img id="loginImage" src="script/base/css/images/userLogin_button.gif" title="点击登录"/></div>
		</div>
		</form>
	</div>
</div>
<div class="copyright divHCenter">
2013 <span class="name">夏集球</span> all right reserved&nbsp;&nbsp;&nbsp;&nbsp;技术电邮：<span class="email">xiajiqiu1990@163.com</span>
</div>
</body>
</html>