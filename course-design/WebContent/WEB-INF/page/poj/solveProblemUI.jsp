<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/include.jsp" %>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>解题</title>
<link type="text/css" rel="stylesheet" href="script/common/css/common.css"/>
<link type="text/css" rel="stylesheet" href="script/poj/css/pojsys.css"/>
<link type="text/css" rel="stylesheet" href="script/common/jqueryui/jqueryui.css" />

<script type="text/javascript" src="script/common/jquery/jquery.js"></script>
<script type="text/javascript" src="script/common/jqueryui/jqueryui.js"></script>

<script type="text/javascript" src="script/poj/js/solveProblemUI.js"></script>

</head>
<body>
<div id="divContainer" class="divContainer">
	<div style="float:left;width:32%;height: 90%;">
	<div id="exeDesc" class="exeDesc">
	<input type="hidden" id="proId" value="${pro.id }">
		<div class="titleBar">题目说明</div>
		<div class="mainDesc">
			<table>
				<tr>
					<td class="project">题目名称</td>
					<td class="content">${pro.name }</td>
				</tr>
				<tr>
					<td class="project">题目描述</td>
					<td class="content">${pro.description }</td>
				</tr>
				<tr>
					<td class="project">输入示例</td>
					<td class="content">${pro.inputStyle }</td>
				</tr>
				<tr>
					<td class="project">输出示例</td>
					<td class="content">${pro.outputStyle }</td>
				</tr>
			</table>
		</div>
	</div>
	<div id="executeInfo" class="excuteInfo">
		<div class="titleBar">运行情况</div>
		<div class="info" id="info">未运行任何代码......</div>
	</div>
	</div>
	
	<div id="sourceEdit" class="sourceEdit">
		<div class="titleBar">编辑源代码</div>
		<div class="language">
			语言：
			<c:forEach items="${languages }" var="lan">
				<input type="radio" name="language" value="${lan.name }"/>${lan.name }
			</c:forEach>
			<!-- <input type="radio" name="language" value="JAVA"/>Java(1.6.37) -->
		</div>
		<div class="source">
			<textarea id="source" name="source" class="sourceArea"></textarea>
		</div>
		<div class="oprationBar">
			<div id="clearSource" class="clearSource">清空代码</div>
			<div id="submitSource" class="submitSource">提交代码</div>
			<div class="submitSource" onclick="javascript: window.history.back();">返　回</div>
		</div>
	</div>
	
	<div id="dialogModal"></div>
</div>
</body>
</html>