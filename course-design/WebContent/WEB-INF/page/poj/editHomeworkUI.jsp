<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加题目</title>
<link type="text/css" rel="stylesheet" href="script/common/css/common.css" />
<link type="text/css" rel="stylesheet" href="script/common/css/tables.css" />
<link type="text/css" rel="stylesheet" href="script/poj/css/pojsys.css" />

<script type="text/javascript" src="script/common/jquery/jquery.js"></script>
<link type="text/css" rel="stylesheet" href="script/common/jqueryui/jqueryui.css" />
<script type="text/javascript" src="script/common/jqueryui/jqueryui.js"></script>
<script type="text/javascript" src="script/common/js/tables.js"></script>
<script type="text/javascript" src="script/poj/js/pojsys.js"></script>

<link href="script/common/mypaging/css/mypaging.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="script/common/mypaging/jquery.mypaging.js"></script>
<script type="text/javascript" src="script/poj/js/editHomeworkUI.js"></script>
</head>

<body>

<!-- 创建和修改题目界面 -->
<form action="poj/saveHomework.html" method="post" id="hwForm">
	<table align="center" style="margin-top: 100px;">
		<tr>
			<td class="title" >当前课程</td>
			<td class="content" style="text-align:left; padding-left: 20px;">
				<input type="hidden" name="courseId" id="courseId" value="${course.id }"/>${course.name }
				<!-- <span onclick="selectCourse()" class="abtn">选择</span> -->
			</td>
		</tr>
		<tr>
			<td class="title">请选择题目</td>
			<td class="content" title="请单击选择题目" style=" padding-left: 20px;cursor: pointer;text-align:left;" onclick="selectProblem()">
				<input type="hidden" name="problemId" id="problemId"/>
				<span id="proIdTd"><span stype="color: gray;">单击此处选择一个题目</span></span> 
			</td>
		</tr>
		<tr>
			<td></td>
			<td class="opration" style="text-align: left; padding-left: 20px;" >
			<poj:a cssClass="abtn" onclick="submitForm();return false;">保存</poj:a>
			<!-- <input type="reset" value="清空" class="abtn"/>--> 
			<input type="button" value="返回" class="abtn" onclick="window.history.back();"/>
			</td>
		</tr>
	</table>
</form>

<div id="courseDiv" style="display: none;">
	<table style="width: 100%; heigth: 100%;border: 1px solid blue;">
		<thead>
			<tr>
				<td width="10%"></td>
				<td width="10%">课程编号</td><!-- 如果没有姓名就显示帐号 -->
				<td width="60%">课程名称</td>
				<td width="20%">所属教师</td>
			</tr>
		</thead>
		<tbody>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="4">
					<div id="coursePagingDiv"></div>
				</td>
			</tr>
		</tfoot>
	</table>
</div>
<div id="problemDiv" style="display: none;">
	<table style="width: 100%; heigth: 100%;border: 1px solid blue;">
		<thead>
			<tr>
				<td width="5%"></td>
				<td width="10%">题目编号</td><!-- 如果没有姓名就显示帐号 -->
				<td width="20%">题目名称</td>
				<td width="65%">题目描述</td>
			</tr>
		</thead>
		<tbody>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="4">
					<div id="problemPagingDiv"></div>
					<div class="manager">
						<a class="abtn" href="" onclick="javascript: window.history.back();return false;">返　回</a>
					</div>
				</td>
			</tr>
		</tfoot>
	</table>
</div>

</body>
</html>

