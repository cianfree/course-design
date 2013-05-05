<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色列表</title>

<link type="text/css" rel="stylesheet" href="script/common/css/common.css" />
<link type="text/css" rel="stylesheet" href="script/common/css/tables.css" />
<link type="text/css" rel="stylesheet" href="script/base/css/system.css" />

<script type="text/javascript" src="script/common/jquery/jquery.js"></script>
<link type="text/css" rel="stylesheet" href="script/common/jqueryui/jqueryui.css" />
<script type="text/javascript" src="script/common/jqueryui/jqueryui.js"></script>
<!-- 
<script type="text/javascript" src="script/itniwo/itniwo.min.js"></script>
<link rel="stylesheet" type="text/css" href="script/itniwo/itniwo.css" />
 -->

<script type="text/javascript" src="script/common/js/tables.js"></script>
<script type="text/javascript" src="script/base/js/system.js"></script>

<!-- 引入自定义的分页插件 -->
<link href="script/common/mypaging/css/mypaging.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="script/common/mypaging/jquery.mypaging.js"></script>

<script type="text/javascript">
jQuery(function(){
	jQuery("#padingDiv").myPaging({
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
	
});
</script>

</head>
<body>
<div class="mainContent" id="mainContent">
<form action="role/roleList.html" method="post" name="queryForm" id="queryForm">
	<table>
		<thead>
			<tr>
				<td width="150px">角色名称</td>
				<td>角色描述</td>
				<td width="200px">相关操作</td>
			</tr>
		</thead>
		
		<tbody>
			<c:forEach items="${requestScope.pb.recordList }" var="role">
				<tr>
					<td>${role.name }</td>
					<td>${role.description }</td>
					<td>
						<c:if test="${role.type eq 0 }">
							<poj:a onclick="javascript:deleteRoleConfirm('role/deleteRole.html?id=${role.id}');return false;">删除</poj:a>
						</c:if>
						<poj:a href="role/editRoleUI.html?id=${role.id }">修改</poj:a>
						<poj:a href="role/privTreeUI.html?id=${role.id }">权限设置</poj:a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
		
		<tfoot>
			<tr>
				<td colspan="4" id="pagingTd">
					<div id="padingDiv"></div>
					<div class="oprationDiv">
						<poj:a cssClass="abtn" href="role/editRoleUI.html">新　建</poj:a>
					</div>
				</td>
			</tr>
		</tfoot>
	</table>
</form>
</div>

</body>
</html>