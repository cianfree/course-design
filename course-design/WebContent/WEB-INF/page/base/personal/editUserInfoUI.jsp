<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人信息</title>
<link type="text/css" rel="stylesheet" href="script/common/css/common.css" />
<link type="text/css" rel="stylesheet" href="script/base/css/editUserInfoUI.css" />

<script type="text/javascript" src="script/common/jquery/jquery.js"></script>

<script type="text/javascript">
$(function(){
	
	$(".userInfoBtn").bind("click", function(){
		switch($(this).html()) {
		case "保存":
			$("#userInfoForm").submit();
			break;
		case "返回":
			window.history.back();
			break;
		default:break;
		}
	});
	
})
</script>

</head>
<body>
	<div class="mainContent">
		<div id="userInfo">
		<form action="personal/updateUserInfo.html" name="userInfoForm" id="userInfoForm" method="post">
			<table align="center" width="700px">
				<caption>用户基本信息</caption>
				<tbody>
					<tr>
						<td width="150px" align="right">帐号(登录名): </td>
						<td align="left">${sessionScope.currentUser.account }</td>
					</tr>
					<tr>
						<td width="150px" align="right">角色: </td>
						<td align="left">
							<c:forEach items="${sessionScope.currentUser.roles }" var="role">
								${role.name } 
							</c:forEach>
						</td>
					</tr>
					<tr>
						<td width="150px" align="right">真实姓名: </td>
						<td align="left">
							<input type="text" name="name" id="name" value="${sessionScope.currentUser.name }">
						</td>
					</tr>
					<tr>
						<td width="150px" align="right">性别: </td>
						<td align="left">
							<input type="radio" name="sex" value="男" id="sex" ${pf:isMan(sessionScope.currentUser) ? 'checked' : ''}>男
							<input type="radio" name="sex" value="女" id="sex" ${pf:isMan(sessionScope.currentUser) ? '' : 'checked'}>女
						</td>
					</tr>
					<tr>
						<td width="150px" align="right">邮箱: </td>
						<td align="left">${sessionScope.currentUser.email }</td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="2" align="center" class="opration">
							<span class="userInfoBtn">保存</span>
							<span class="userInfoBtn">返回</span>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center" style="color:red;">
							${message }
						</td>
					</tr>
				</tfoot>
			</table>
		</form>
		</div>
	</div>
</body>
</html>