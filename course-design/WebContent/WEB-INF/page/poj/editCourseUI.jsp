<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加题目</title>
<link rel="stylesheet" type="text/css" href="script/poj/css/editCourseUI.css"/>
<link rel="stylesheet" type="text/css" href="script/common/css/common.css"/>
<script type="text/javascript" src="script/common/jquery/jquery.js"></script>

<script type="text/javascript" src="script/poj/js/editCourseUI.js"></script>
<script type="text/javascript">
function submitForm() {
	$("#courseForm").trigger("submit");
}

</script>

</head>
<body>

<!-- 创建和修改课程界面 -->
<form action="${empty course ? 'poj/saveCourse.html' : 'poj/updateCourse.html'}" method="post" id="courseForm">
	<input type="hidden" name="id" value="${course.id }">
	<table align="center">
		<tr>
			<td class="title">课程名称</td>
			<td class="content"><input type="text" name="name" id="name" value="${course.name }"/><span></span></td>
		</tr>
		<tr>
			<td class="title">课程描述</td>
			<td class="content">
				<textarea name="description" id="description">${course.description }</textarea>
			</td>
		</tr>
		<tr>
			<td colspan="2" class="opration" align="right">
			<poj:a cssClass="abtn" onclick="submitForm();return false;">保存</poj:a>
			<input type="reset" value="清空" class="abtn"/> 
			<input type="button" value="返回" class="abtn" onclick="window.history.back();"/>
			</td>
		</tr>
	</table>
</form>
</body>
</html>

