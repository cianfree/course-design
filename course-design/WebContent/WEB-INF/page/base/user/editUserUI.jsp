<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑用户信息</title>
<link rel="stylesheet" type="text/css" href="script/base/css/system.css">
<link rel="stylesheet" type="text/css" href="script/base/css/editUserUI.css">
<link type="text/css" rel="stylesheet" href="script/common/jqueryui/jqueryui.css" />

<script type="text/javascript" src="script/common/jquery/jquery.js"></script>
<script type="text/javascript" src="script/common/jqueryui/jqueryui.js"></script>

<script type="text/javascript" src="script/base/js/editUserUI.js"></script>
<script type="text/javascript" src="script/common/js/validator.js"></script>

</head>
<body>

<div id="userInfo" class="userInfo">
<form action="${empty requestScope.user ? 'user/saveUser.html' : 'user/updateUser.html'}" method="post" name="userForm" id="userForm">
	<input type="hidden" name="id" id="userId" value="${requestScope.user.id }">
	<table align="center">
		<caption>
			创建用户——填写用户基本信息
		</caption>
		<tbody>
			<tr>
				<td align="right" width="200px">用户帐号:　</td>
				<td><input type="text" name="account" id="account" value="${requestScope.user.account }"><span></span></td>
			</tr>
			<tr>
				<td align="right">邮箱:　</td>
				<td><input type="text" name="email" id="email" value="${requestScope.user.email }"><span></span></td>
			</tr>
			<tr>
				<td align="right">真实姓名:　</td>
				<td><input type="text" name="name" id="name" value="${requestScope.user.name }"><span></span></td>
			</tr>
			<tr>
				<td align="right">性别:　</td>
				<td>
					<input type="radio" name="sex" value="男" ${pf:isMan(requestScope.user) ? 'checked' : ''}>男
					<input type="radio" name="sex" value="女" ${pf:isMan(requestScope.user) ? '' : 'checked'}>女
				</td>
			</tr>
			<tr>
				<td align="right">用户角色:　</td>
				<td>
					<select name="roleIds" id="roleIds" multiple="multiple" class="roleTextarea">
						<c:forEach items="${roles }" var="role">
								<option value="${role.id }" ${pf:hasRole(requestScope.user, role) ? 'selected' : ''}>${role.name }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</tbody>
		
		<tfoot>
			<tr>
				<td colspan="2" align="center">
					<div style="float: left;color:red;">${message }</div>
					<div class="opration">
						<div class="oprationButton" id="submitBtn">提交</div>
						<div class="oprationButton" id="resetBtn">重置</div>
						<div class="oprationButton" id="returnBtn">返回</div>
					</div>
				</td>
			</tr>
		</tfoot>
	</table>
</form>
</div>

</body>
</html>