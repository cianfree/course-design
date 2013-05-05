<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/include.jsp" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改用户密码</title>

<script type="text/javascript" src="script/common/jquery/jquery.js"></script>
<script type="text/javascript" src="script/base/js/editUserPwdUI.js"></script>

<style type="text/css">
tbody td input {
	width: 100%;
	border-top: 0px;
	border-left: 0px;
	border-right: 0px;
	border-bottom: 2px solid gray;
	background-color: transparent;
}

tfoot td input {
	border: 1px solid blue;
	background-color: #F3F9FD;
	width: 80px;
}

tfoot td input:hover {
	color: white;
	cursor: pointer;
	background-color: blue;
}

h2 {
	border-bottom: 2px solid gray;
	padding-bottom: 15px;
}
</style>

</head>
<body>
<h2 align="center" style="color:red;">修改密码</h2>
<form action="personal/updateUserPassword.html" method="post" id="pwdForm">
<table align="center">
	<tbody>
		<tr>
			<td align="right" width="150px">请输入旧密码：</td>
			<td width="200px"><input type="password" name="oldPwd" id="oldPwd"></td>
		</tr>
		<tr>
			<td align="right" width="150px">请输入新密码：</td>
			<td width="200px"><input type="password" name="newPwd" id="newPwd"></td>
		</tr>
		<tr>
			<td align="right" width="150px">再次输入新密码：</td>
			<td width="200px"><input type="password" name="rePwd" id="rePwd"></td>
		</tr>
		<tr>
			<td></td>
			<td><span id="tips" style="color: red;">${requestScope.message } </span></td>
		</tr>
	</tbody>
	<tfoot>
		<tr align="right">
			<td></td>
			<td>
				<input type="submit" value="更新">
				<input type="reset" value="重置">
			</td>
		</tr>
	</tfoot>
</table>
</form>
</body>
</html>