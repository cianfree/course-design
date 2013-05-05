<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/include.jsp" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>View Course</title>
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

<link href="script/poj/css/viewCourse.css" type="text/css" rel="stylesheet" />
<link href="script/common/mypaging/css/mypaging.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="script/common/mypaging/jquery.mypaging.js"></script>

<script type="text/javascript" src="script/poj/js/viewCourse.js"></script>
</head>
<body>
<div class="mainContent">
<c:if test="${empty course }">
	<h1 align="center">对不起，现在还没有任何的课程信息</h1>
</c:if>
<c:if test="${!empty course }">
	<div class="courseList">
		<input type="hidden" id="courseId" value="${course.id }">
		<div class="title">课程名称:</div>
		<div class="content">${course.name }</div>
		
		<div class="title">创建者:</div>
		<div class="content">${empty course.owner.name ? course.owner.account : course.owner.name}</div>
		
		<div class="title">课程描述:</div>
		<div class="content">
			${course.description }
		</div>
	</div>
	
	<div class="list">
		<div class="studentList">
			<table style="width: 100%; heigth: 100%;border: 1px solid blue;">
				<caption>该课程的学生列表</caption>
				<thead>
					<tr>
						<td width="10%">编号</td>
						<td width="20%">姓名</td><!-- 如果没有姓名就显示帐号 -->
						<td width="10%">性别</td>
						<td width="60%">操作</td>
					</tr>
				</thead>
				<tbody id="studsBody">
				</tbody>
				<tfoot>
					<tr>
						<td colspan="4">
							<span id="studPagingDiv"></span>
							<div class="manager">
								<c:if test="${pf:isMyCourse(currentUser, course.id) }">
									<poj:a href="poj/studentList.html?courseId=${course.id }" cssClass="abtn">添加</poj:a>
								</c:if>
								<a class="abtn" href="" onclick="javascript: window.history.back();return false;">返　回</a>
							</div>
						</td>
					</tr>
				</tfoot>
			</table>		
		</div>
		<div class="homeworkList">
			<table style="width: 100%; heigth: 100%;border: 1px solid blue;">
				<caption>该课程的作业列表</caption>
				<thead>
					<tr>
						<td width="10%">编号</td>
						<td width="20%">名称</td>
						<td width="10%">描述</td>
						<td width="60%">操作</td>
					</tr>
				</thead>
				<tbody id="hwBody">
				</tbody>
				<tfoot>
					<tr>
						<td colspan="4">
							<span id="hwPagingDiv"></span>
							<div class="manager">
								<c:if test="${pf:isMyCourse(currentUser, course.id) }">
									<poj:a href="poj/editHomeworkUI.html?courseId=${course.id }" cssClass="abtn">布置作业</poj:a>
								</c:if>
								<a class="abtn" href="" onclick="javascript: window.history.back();return false;">返　回</a>
							</div>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
	</div>
</c:if>
</div>
</body>
</html>