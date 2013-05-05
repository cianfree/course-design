<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>版块列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script type="text/javascript" src="script/common/jquery/jquery.js"></script>
    <link type="text/css" rel="stylesheet" href="script/forum/css/pageCommon.css" />
    <link href="script/common/mypaging/css/mypaging.css" type="text/css" rel="stylesheet" />
    <link type="text/css" rel="stylesheet" href="script/common/css/common.css" />
	<script type="text/javascript" src="script/common/mypaging/jquery.mypaging.js"></script>
    
    <script type="text/javascript">
    	$(function(){
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
    	});
    </script>
</head>
<body>

<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="script/forum/css/images/title_arrow.gif"/> 版块管理
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<div id="MainArea">
    <table cellspacing="0" cellpadding="0" class="TableStyle">
        <!-- 表头-->
        <thead>
            <tr align="CENTER" valign="MIDDLE" id="TableTitle">
            	<td width="250px">版块名称</td>
                <td width="300px">版块说明</td>
                <td>相关操作</td>
            </tr>
        </thead>

		<!--显示数据列表-->
        <tbody id="TableData" class="dataContainer">
        	<c:forEach items="${pb.recordList }" var="forum" varStatus="status">
				<tr class="TableDetail1 template">
					<td>${forum.name}&nbsp;</td>
					<td>${forum.description}&nbsp;</td>
					<td><poj:a onclick="javascript: return confirm('你确定要删除吗？');" href="forumMgr/deleteForum.html?id=${forum.id }">删除</poj:a>
						<poj:a href="forumMgr/editForumUI.html?id=${forum.id }">修改</poj:a>
					
						<c:choose>
							<c:when test="${status.first }">
								<span style="color: gray;cursor: pointer;">上移</span>
							</c:when>
							<c:otherwise>
								<poj:a href="forumMgr/moveUp.html?id=${forum.id }">上移</poj:a>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${status.last }">
								<span style="color: gray;cursor: pointer;">下移</span>
							</c:when>
							<c:otherwise>
								<poj:a href="forumMgr/moveDown.html?id=${forum.id }">下移</poj:a>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
        	</c:forEach>
        </tbody>
    </table>
    <form action="forumMgr/forumList.html" method="post" id="queryForm"></form>
    <!-- 其他功能超链接 -->
    <div id="TableTail">
        <div id="TableTail_inside">
           <poj:a href="forumMgr/editForumUI.html" cssClass="abtn" >新建</poj:a>
           <div id="pagingDiv" style="float:right;margin-right: 10px;"></div>
        </div>
    </div>
</div>

</body>
</html>
