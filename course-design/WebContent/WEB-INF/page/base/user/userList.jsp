<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>

<link type="text/css" rel="stylesheet" href="script/common/css/common.css" />
<link type="text/css" rel="stylesheet" href="script/common/css/tables.css" />
<link type="text/css" rel="stylesheet" href="script/base/css/system.css" />

<script type="text/javascript" src="script/common/jquery/jquery.js"></script>
<link type="text/css" rel="stylesheet" href="script/common/jqueryui/jqueryui.css" />
<script type="text/javascript" src="script/common/jqueryui/jqueryui.js"></script>

<script type="text/javascript" src="script/common/js/tables.js"></script>
<script type="text/javascript" src="script/base/js/system.js"></script>

<!-- 引入自定义的分页插件 -->
<link href="script/common/mypaging/css/mypaging.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="script/common/mypaging/jquery.mypaging.js"></script>

<script type="text/javascript">
	$(function(){
		$("#padingDiv").myPaging({
			currentPage: ${requestScope.pb.currentPage},
			pageCount: ${requestScope.pb.pageCount},
			pageSize: ${requestScope.pb.pageSize},
			totalRecord: ${requestScope.pb.recordCount},
			showSize: 10,
			callback: function(currentPage, pageSize){
				//alert("currentPage: " + currentPage + "\npageSize: " + pageSize);
				// 添加到form中
				$("#queryForm").append('<input type="hidden" name="currentPage" value="'+currentPage+'"/>');
				$("#queryForm").append('<input type="hidden" name="pageSize" value="'+pageSize+'"/>');
				$("#queryForm").submit();
			}
		});
		
		// 给search按钮注册事件
		$("#searchBtn").bind("click", defaultSubmit);
		$("#roleId").bind("change", defaultSubmit);
		
		function defaultSubmit() {
			$("#queryForm").append('<input type="hidden" name="currentPage" value="1"/>');
			$("#queryForm").append('<input type="hidden" name="pageSize" value="12"/>');
			$("#queryForm").submit();
		}
		
		// 添加Enter事件
		$(document).bind("keydown", function(event){
			if(event.keyCode == 13) {
				$("#searchBtn").click();
			}
		});
	});
</script>

</head>
<body>

<div class="mainContent" id="mainContent">
<form action="user/userList.html" method="post" name="queryForm" id="queryForm">
	<table>
		<caption>
			<div id="conditionDiv" class="conditionDiv">
					<div id="conditions" class="conditions">
						帐号：<input type="text" class="queryAttr" id="account" name="account" value="${account }"/>
						姓名：<input type="text" class="queryAttr" id="name" name="name" value="${name }"/>
						<!-- 查询条件设置 
						角色：<select class="roleId" id="roleId" name="roleId">
							<c:forEach items="${requestScope.roles }" var="role">
								<option value="${role.id }" ${role.id eq roleId ? 'selected' : '' }>${role.name }</option>
							</c:forEach>
						</select>
						-->
					</div>
					<div id="searchBtn" class="searchBtn">
						查　询
					</div>
				</div>
		</caption>
		<thead>
			<tr>
				<td>用户账户</td>
				<td>真实姓名</td>
				<td>用户角色</td>
				<td>相关操作</td>
			</tr>
		</thead>
		
		<tbody>
		<c:forEach items="${requestScope.pb.recordList}" var="user">
			<tr>
				<td>${user.account }</td>
				<td>${user.name }</td>
				<td>
					<c:forEach items="${user.roles }" var="r">
						${r.name} 
					</c:forEach>
				</td>
				<td>
					<c:if test="${not pf:isAdmin(user) }">
						<poj:a href="user/deleteUser.html" onclick="javascript:deleteUserConfirm('user/deleteUser.html?id=${user.id}');return false;">删除</poj:a>
						<poj:a href="user/editUserUI.html?id=${user.id }">修改</poj:a>
						<poj:a href="" onclick="javascript:initPassword(${user.id}, null, ${defPwd}); return false; ">初始化密码</poj:a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
		
		<tfoot>
			<tr>
				<td colspan="4" id="pagingTd">
					<div id="padingDiv"></div>
					<div id="userMgr" class="oprationDiv">
						<poj:a cssClass="abtn" href="user/editUserUI.html">新　建</poj:a>
					</div>
				</td>
			</tr>
		</tfoot>
	</table>
</form>
</div>

</body>
</html>