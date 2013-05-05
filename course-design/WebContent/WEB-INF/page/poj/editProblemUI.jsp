<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加题目</title>
<link rel="stylesheet" type="text/css" href="script/poj/css/editProblemUI.css"/>
<link rel="stylesheet" type="text/css" href="script/common/css/common.css"/>
<script type="text/javascript" src="script/common/jquery/jquery.js"></script>

<script type="text/javascript" src="script/poj/js/editProblemUI.js"></script>
</head>
<body>

<!-- 创建和修改题目界面 -->
<form action="${empty pro ? 'poj/saveProblem.html' : 'poj/updateProblem.html'}" method="post" id="exeForm">
	<input type="hidden" name="id" value="${pro.id }">
	<table align="center">
		<tr>
			<td class="title">题目名称</td>
			<td class="content"><input type="text" name="name" id="name" value="${pro.name }"/><span></span></td>
		</tr>
		<tr>
			<td class="title">出题人</td>
			<td class="content"><input type="text" name="author" id="author" value="${pro.author }" title="默认是编辑本页的用户"/><span></span></td>
		</tr>
		<tr>
			<td class="title">难度等级</td>
			<td>
				<input type="radio" name="level" value="初级" ${pf:isEqual("初级", pro.level) ? 'checked' :''}/>初级
				<input type="radio" name="level" value="中级" ${pf:isEqual("中级", pro.level) ? 'checked' :''}/>中级
				<input type="radio" name="level" value="高级" ${pf:isEqual("高级", pro.level) ? 'checked' :''}/>高级
			</td>
		</tr>
		<tr>
			<td class="title">题目描述</td>
			<td class="content">
				<textarea name="description" id="desc">${pro.description }</textarea>
			</td>
		</tr>
		<tr>
			<td class="title">输入示例</td>
			<td class="content">
				<textarea rows="3" name="inputStyle" id="inputStyle">${pro.inputStyle }</textarea>
			</td>
		</tr>
		<tr>
			<td class="title">输出示例</td>
			<td class="content">
				<textarea rows="3" name="outputStyle" id="outputStyle">${pro.outputStyle }</textarea>
			</td>
		</tr>
		<tr>
			<td colspan="2" class="opration" align="right">
			<poj:a href="poj/saveExe.html" cssClass="abtn" onclick="submitForm();return false;">保存</poj:a>
			<input type="reset" value="清空" class="abtn"/> 
			<input type="button" value="返回" class="abtn" onclick="window.history.back();"/>
			<!-- 
				<span id="csubmit" class="button">提交</span>　
				<span id="creset" class="button">清空</span>　
				<span id="cback" class="button">返回</span>
			 -->
			</td>
		</tr>
	</table>
</form>


</body>
</html>

