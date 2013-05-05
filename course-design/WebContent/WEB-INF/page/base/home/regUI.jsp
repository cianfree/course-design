<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/include.jsp" %>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户注册</title>
<link rel="stylesheet" type="text/css" href="script/base/css/home.css"/>
<script type="text/javascript" src="script/common/jquery/jquery.js"></script>
<script type="text/javascript" src="script/base/js/home.js"></script>
<script type="text/javascript" src="script/base/js/regUI.js"></script>
<script type="text/javascript" src="script/common/validator.js"></script>

</head>
<body class="loginBody">

<div class="videoDiv divHCenter">
<object	classid=clsid:d27cdb6e-ae6d-11cf-96b8-444553540000 width="987" height="218">
	<param name="movie" value="script/home/images/s.swf">
	<param name="quality" value="high">
	<embed src="script/base/css/images/s.swf" quality="high"
		pluginspage="http://www.macromedia.com/go/getflashplayer"
		type="application/x-shockwave-flash" width="987" height="218"></embed>
</object>
</div>

<div class="mainDiv divHCenter">
	<div class="subjectDiv"></div>
	<div class="regFormDiv">
		<form action="user/reg.html" method="post" id="regForm">
		<div class="regDiv">
			<span>帐    号:&nbsp;<input type="text" name="account" id="account"/><font color=red>*</font></span><br/>
			<span>密    码:&nbsp;<input type="password" name="password" id="password"/><font color=red>*</font></span><br/>
			<span>密码确认:&nbsp;<input type="password" name="repassword" id="repassword"/><font color=red>*</font></span><br/>
			<span>邮    箱:&nbsp;<input type="text" name="email" id="email"/><font color=red>*</font></span><br/>
			<div class="message" id="validateMsg">${message }</div>
		</div>
		<div class="loginOpration">
			<div class="loginRegister" title="用户登录"><a href="user/loginUI.html">已有帐号？登录~</a></div>
			<div class="loginSubmit"><img id="regImage" src="script/base/css/images/userReg_button.gif" title="点击注册"/></div>
		</div>
		</form>
	</div>
</div>
<div class="copyright divHCenter">
2013 <span class="name">夏集球</span> all right reserved&nbsp;&nbsp;&nbsp;&nbsp;技术电邮：<span class="email">xiajiqiu1990@163.com</span>
</div>
</body>
</html>