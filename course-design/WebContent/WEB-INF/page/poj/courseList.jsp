<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/include.jsp" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link type="text/css" rel="stylesheet" href="script/common/css/common.css" />
<link type="text/css" rel="stylesheet" href="script/common/css/tables.css" />
<link type="text/css" rel="stylesheet" href="script/poj/css/pojsys.css" />

<script type="text/javascript" src="script/common/jquery/jquery.js"></script>
<link type="text/css" rel="stylesheet" href="script/common/jqueryui/jqueryui.css" />
<script type="text/javascript" src="script/common/jqueryui/jqueryui.js"></script>
<script type="text/javascript" src="script/common/js/tables.js"></script>
<script type="text/javascript" src="script/poj/js/pojsys.js"></script>

<!-- 使用自定义对话框要引入的文件 -->
<script type="text/javascript" src="script/common/js/common.js"></script>

<link href="script/common/mypaging/css/mypaging.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="script/common/mypaging/jquery.mypaging.js"></script>

<script type="text/javascript"><!--
	$(function(){
		// 添加显示对话框的DIV
		$("body").append('<div id="dialogModal"></div>');
		
		if(${requestScope.pb.recordCount} > 0) {
			$("#pagingDiv").myPaging({
				currentPage: ${requestScope.pb.currentPage},
				pageCount: ${requestScope.pb.pageCount},
				pageSize: ${requestScope.pb.pageSize},
				totalRecord: ${requestScope.pb.recordCount},
				callback: function(currentPage, pageSize){
					// 添加到form中
					$("#queryForm").append('<input type="hidden" name="currentPage" value="'+currentPage+'"/>');
					$("#queryForm").append('<input type="hidden" name="pageSize" value="'+pageSize+'"/>');
					$("#queryForm").submit();
				}
			});
		}
		
		// 给search按钮注册事件
		$("#searchBtn").bind("click", defaultSubmit);
		$("#orderRule").bind("change", defaultSubmit);

		function defaultSubmit() {
			$("#queryForm").append('<input type="hidden" name="currentPage" value="1"/>');
			$("#queryForm").append('<input type="hidden" name="pageSize" value="12"/>');
			$("#queryForm").submit();
		}
		
	});
	
	// 删除一个Problem
	function removeCourseConfirm(action) {
		if(!action)
			return false;
		sysComfirm("您确定要删除吗？", //
			function(){	// OK handler
				$(this).dialog('close');
				$(window).attr("location", action);
			},
			function(){	// cancel handler
				$(this).dialog('close');
			}
		);
	}
//--></script>
</head>
<body>
	<div id="mainContent" class="mainContent">
		<div id="courseList" class="divList">
		<form action="poj/courseList.html" method="post" name="queryForm" id="queryForm">
		<table class="customerTable">
			<caption>
				<div id="conditionDiv" class="conditionDiv">
					<div id="conditions" class="conditions">
						<!-- 查询条件设置 -->
						课程名称：<input type="text" id="courseName" name="courseName" value="${condition.courseName }"/>
						教师名：<input type="text" id="userName" name="userName" value="${condition.userName }"/>
						排序规则：
						<input type="radio" name="orderRule" value="ASC" ${'ASC' eq condition.orderRule ? 'checked' : ''}/><span class="radioSpan" onclick="checkSiblingRadio(0)"> 升序</span>
						<input type="radio" name="orderRule" value="DESC" ${'DESC' eq condition.orderRule ? 'checked' : ''}/><span class="radioSpan" onclick="checkSiblingRadio(1)"> 降序</span>
					</div>
					<div id="searchBtn" class="searchBtn">
						查　询
					</div>
				</div>
			</caption>
			<thead>
				<tr>
					<td width="5%">编号</td>
					<td width="20%">名称</td>
					<td width="25%">简介</td>
					<td width="10%">创建日期</td>
					<td width="10%">创建人</td>
					<td width="30%">操作</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${pb.recordList }" var="course">
				<tr>
					<td><poj:a href="poj/viewCourse.html?id=${course.id }" title="详细信息">${course.id }</poj:a></td>
					<td><poj:a href="poj/viewCourse.html?id=${course.id }" title="详细信息">${course.name }</poj:a></td>
					<td>${pf:fixLen(course.description, 15) }</td>
					<td>
						<fmt:formatDate value="${course.buildTime }" pattern="yyyy-MM-dd"/>
					</td>
					<td>${empty course.owner.name ? course.owner.account : course.owner.name}</td>
					<td>
						<poj:a href="poj/removeCourse.html" onclick="removeCourseConfirm('poj/removeCourse.html?id=${course.id }');return false;">删除</poj:a>
						<poj:a href="poj/editCourseUI.html?id=${course.id }">修改</poj:a>
						<poj:a href="poj/viewCourse.html?id=${course.id }">详细信息</poj:a>
						<c:if test="${!pf:isAdmin(currentUser) && pf:isStudent(currentUser) && !pf:canAttend(currentUser, course.id)}">
							<poj:a href="poj/joinCourse.html?id=${course.id }">参加</poj:a>
						</c:if>
						<%-- ${pf:isMyStudent(currentUser, course.id) }
						${pf:isMyCourse(currentUser, course.id) } --%>
						<c:if test="${pf:isMyStudent(currentUser, course.id) || pf:isMyCourse(currentUser, course.id) }">
							<poj:a href="poj/viewCourseHomeworks.html?id=${course.id }">课程作业</poj:a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="6">
						<div id="pagingDiv"></div>
						<div class="manager">
							<poj:a cssClass="abtn" href="poj/editCourseUI.html">新　建</poj:a>
							<a class="abtn" href="" onclick="javascript: window.history.back();return false;">返　回</a>
						</div>
					</td>
				</tr>
			</tfoot>
		</table>
		</form>
		</div>
	</div>
</body>
</html>