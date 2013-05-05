<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑角色信息</title>
<link rel="stylesheet" type="text/css" href="script/base/css/system.css">
<link rel="stylesheet" type="text/css" href="script/base/css/editRoleUI.css">
<link rel="stylesheet" type="text/css" href="script/common/css/common.css">

<script type="text/javascript" src="script/common/jquery/jquery.js"></script>

<script type="text/javascript" src="script/base/js/editRoleUI.js"></script>

</head>
<body>

<div id="roleInfo" class="roleInfo">
<form action=${empty role ? 'role/saveRole.html' : 'role/updateRole.html'} method="post" name="roleForm" id="roleForm">
	<input type="hidden" name="id" id="roleId" value="${role.id }">
	<table align="center">
		<caption>
			编辑角色基本信息
		</caption>
		<tbody>
			<tr>
				<td align="right" width="200px">角色名称: </td>
				<td><input type="text" name="name" id="roleName" value="${role.name }"></td>
			</tr>
			<tr>
				<td align="right">角色描述: </td>
				<td>
					<textarea name="description" id="roleDesc">${role.description }</textarea>
				</td>
			</tr>
			<tr>
				<td></td>
				<td><span id="tipSpan"></span></td>
			</tr>
		</tbody>
		<tfoot>
			<tr>
				<td></td>
				<td align="right">
					<div class="opration">
						<div class="divButton" id="submitBtn" onclick="javascript:roleForm.submit();">提交</div>
						<div class="divButton" id="returnBtn" onclick="javascript:window.history.back();">返回</div>
					</div>
				</td>
			</tr>
		</tfoot>
	</table>
</form>
</div>

</body>
</html>