<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>解题列表</title>
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

<script type="text/javascript">
	$(function(){
		$("#pagingDiv").myPaging({
			currentPage: ${requestScope.pb.currentPage},
			pageCount: ${requestScope.pb.pageCount},
			pageSize: ${requestScope.pb.pageSize},
			totalRecord: ${requestScope.pb.recordCount},
			callback: function(currentPage, pageSize){
				alert("currentPage: " + currentPage + "\npageSize: " + pageSize);
				// 添加到form中
				$("#queryForm").append('<input type="hidden" name="currentPage" value="'+currentPage+'"/>');
				$("#queryForm").append('<input type="hidden" name="pageSize" value="'+pageSize+'"/>');
				$("#queryForm").submit();
			}
		});
		
		// 给search按钮注册事件
		$("#searchBtn").bind("click", defaultSubmit);
		$("#orderRule").bind("click", defaultSubmit);
		$("#status").bind("click", defaultSubmit);
		
		function defaultSubmit() {
			$("#queryForm").append('<input type="hidden" name="currentPage" value="1"/>');
			$("#queryForm").append('<input type="hidden" name="pageSize" value="12"/>');
			$("#queryForm").submit();
		}
	});
</script>
</head>
<body>
	<div id="mainContent" class="mainContent">
		<div id="exeList" class="divList">
		<form action="#" method="post" name="queryForm" id="queryForm">
		<table id="exeListTable" class="customerTable">
			<!--
			<caption>
				<div id="conditionDiv" class="conditionDiv">
					<div id="conditions" class="conditions">
						编号：<input type="text" id="exeId" name="exeId" style="width: 100px;"/>
						关键字：<input type="text" id="keyword" name="keyword" style="width: 150px;"/>
						难度：<input type="radio" name="level" value="high" checked="checked"/><span class="radioSpan" onclick="checkSiblingRadio(0)"> 高级</span>
						<input type="radio" name="level" value="middle"/><span class="radioSpan" onclick="checkSiblingRadio(1)"> 中级</span>
						<input type="radio" name="level" value="low"/><span class="radioSpan" onclick="checkSiblingRadio(2)"> 初级</span>　
						排序规则：<select class="orderRule" id="orderRule">
							<option value="ASC" selected="selected">按日期升序</option>
							<option value="DESC">按日期降序</option>
						</select>
						解题状态：<select class="status" id="status">
							<option value="Accepted" selected="selected">Accepted</option>
							<option value="Wrong">Wrong</option>
							<option value="Compile Error">Compile Error</option>
						</select>
					</div>
				</div>
			</caption>
			-->
			<thead>
				<tr>
					<td width="10%">编号</td>
					<td width="30%">名称</td>
					<td width="20%">解题时间</td>
					<td width="10%">题目难度</td>
					<td width="10%">答题状态</td>
					<td width="25%">操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${pb.recordList }" var="workout">
					<tr>
						<td>${workout.id }</td>
						<td>${workout.problem.name }</td>
						<td>${workout.workTime }</td>
						<td>${workout.problem.level }</td>
						<td>${workout.state }</td>
						<td>
							<a href="poj/resolveProblemUI.html?id=${workout.problem.id }">重做</a>　
						</td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="6">
						<div id="pagingDiv"></div>
						<!-- 
						<div id="searchBtn" class="searchBtn">查　询</div>
						 -->
					</td>
				</tr>
			</tfoot>
		</table>
		</form>
		</div>
	</div>
	<div class="description">
	<!-- 
	功能：<br/>
	(1) 默认条件下列分页出所有的题目,需要显示题目ID，名称，创建日期，出题人，等级，操作<br/>
	(2) 可以指定筛选条件，条件包括：关键字（题目ID，名称，内容），难度<br/>
	(3) 排序功能，按照创建时间排序，默认是降序排序，即后面出的在前面<br/>
	(4) 提供新建题目的按钮，提供一个从文件导入题目的按钮
	-->
	</div>
	<div id="dialogModal"></div>
</body>
</html>