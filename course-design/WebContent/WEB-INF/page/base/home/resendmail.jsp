<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>重新发送激活邮件</title>
</head>
<body>
<h1 align="center">您好，激活失败，激活邮件已经重新发送，请登录您的邮箱：${requestScope.reguser.email}激活您的帐号！</h1>
<hr size="5" color="blue">
以下是您的注册信息：<br/>
帐号：${requestScope.reguser.account }<br/>
邮箱：${requestScope.reguser.email }<br/>
<hr color="blue"/>
注：如果您的邮件不存在，那么系统将删除本次注册信息
<hr color="blue"/>
<a href="loginUI.html"><<返回登录页面</a>
</body>
</html>