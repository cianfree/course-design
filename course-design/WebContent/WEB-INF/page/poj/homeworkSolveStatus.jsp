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

<script type="text/javascript">
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
					//alert("currentPage: " + currentPage + "\npageSize: " + pageSize);
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
	function deleteProblemConfirm(action) {
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
</script>
</head>
<body>
	<div id="mainContent" class="mainContent">
		<div id="exeList" class="divList">
		<form action="poj/homeworkSolveStatus.html" method="post" name="queryForm" id="queryForm">
		<input type="hidden" name="hwId" value="${hw.id }"/>
		<table id="exeListTable" class="customerTable">
						<!-- 
			<caption>
				<div id="conditionDiv" class="conditionDiv">
					<div id="conditions" class="conditions">
						查询条件设置 
						编号：<input type="text" id="exeId" name="exeId" value="${condition.exeId }"/>
						关键字：<input type="text" id="keyword" name="keyword" value="${condition.keyword }" />
						难度：<input type="radio" name="level" value="高级" ${'高级' eq condition.level ? 'checked' : ''}/><span class="radioSpan" onclick="checkSiblingRadio(0)"> 高级</span>
						<input type="radio" name="level" value="中级" ${'中级' eq condition.level ? 'checked' : ''}/><span class="radioSpan" onclick="checkSiblingRadio(1)"> 中级</span>
						<input type="radio" name="level" value="初级" ${'初级' eq condition.level ? 'checked' : ''}/><span class="radioSpan" onclick="checkSiblingRadio(2)"> 初级</span>　
						排序规则：<select class="orderRule" id="orderRule">
							<option value="ASC" selected="selected">按日期升序</option>
							<option value="DESC">按日期降序</option>
						</select>
					</div>
					<div id="searchBtn" class="searchBtn">
						查　询
					</div>
				</div>
			</caption>
						-->
			<thead>
				<tr>
					<td width="10%">用户ID</td>
					<td width="20%">用户名</td>
					<td width="20%">题目名称</td>
					<td width="10%">状态</td>
					<td width="10%">尝试次数</td>
					<td width="35%">最早解题时间</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${pb.recordList }" var="hws">
				<tr>
					<td>${hws.userId }</td>
					<td>${hws.userName }</td>
					<td>${hws.problemName }</td>
					<td>${hws.state }</td>
					<td>${hws.attemptCount }</td>
					<td>${hws.workTime }</td>
				</tr>
			</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="6">
						<div id="pagingDiv"></div>
						<div class="manager">
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