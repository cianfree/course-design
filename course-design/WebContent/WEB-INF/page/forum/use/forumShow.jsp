<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>【${forum.name }】中的主题列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<script type="text/javascript" src="script/common/jquery/jquery.js"></script>
    <link type="text/css" rel="stylesheet" href="script/forum/css/pageCommon.css" />
	<link type="text/css" rel="stylesheet" href="script/forum/css/forum.css" />
	
	<link href="script/common/mypaging/css/mypaging.css" type="text/css" rel="stylesheet" />
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

<!-- 标题显示 -->
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="script/forum/css/images/title_arrow.gif"/> 【常见问题】中的主题列表
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<div id="MainArea">

	<div id="PageHead"></div>
	<center>
		<div class="ItemBlock_Title1" style="width: 98%;">
			<font class="MenuPoint"> &gt; </font>
			<a href="forum/forumList.html">论坛</a>
			<font class="MenuPoint"> &gt; </font>
			${forum.name }
			<span style="margin-left:30px;">
			<poj:a href="forum/editTopicUI.html?forumId=${forum.id }">
				<img align="absmiddle" src="script/forum/css/images/button/publishNewTopic.png"/>
			</poj:a>
			</span>
		</div>
		
		<div class="ForumPageTableBorder">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<!--表头-->
				<tr align="center" valign="middle">
					<td width="3" class="ForumPageTableTitleLeft">
						<img border="0" width="1" height="1" src="script/forum/css/images/blank.gif" />
					</td>
					<td width="50" class="ForumPageTableTitle"><!--状态/图标-->&nbsp;</td>
					<td class="ForumPageTableTitle">主题</td>
					<td width="130" class="ForumPageTableTitle">作者</td>
					<td width="100" class="ForumPageTableTitle">回复数</td>
					<td width="130" class="ForumPageTableTitle">最后回复</td>
					<td width="3" class="ForumPageTableTitleRight">
						<img border="0" width="1" height="1" src="script/forum/css/images/blank.gif" />
					</td>
				</tr>
				<tr height="1" class="ForumPageTableTitleLine"><td colspan="8"></td></tr>
				<tr height=3><td colspan=8></td></tr>
					
				<!--主题列表-->
				<tbody class="dataContainer">
					<c:forEach items="${pb.recordList }" var="topic">
					<tr height="35" id="d0" class="template">
						<td></td>
						<td class="ForumTopicPageDataLine" align="center"><img src="script/forum/css/images/topicType_${topic.type}.gif" /></td>
						<td class="Topic"><a class="Default" href="forum/topicShow.html?id=${topic.id }">${topic.title}</a></td>
						<td class="ForumTopicPageDataLine">
							<ul class="ForumPageTopicUl">
								<li class="Author">${topic.author.name }</li>
								<li class="CreateTime">${topic.postTime }</li>
							</ul>
						</td>
						<td class="ForumTopicPageDataLine Reply" align="center"><b>${topic.replyCount}</b></td>
						<td class="ForumTopicPageDataLine">
							<ul class="ForumPageTopicUl">
								<li class="Author">${topic.lastReply.author.name }</li>
								<li class="CreateTime">${topic.lastUpdateTime }</li>
							</ul>
						</td>
						<td></td>
					</tr>
					</c:forEach>
					</tbody>
					<!--主题列表结束-->	
						
					<tr height="3"><td colspan="9"></td></tr>
				
			</table>
			<!--其他操作-->
			<div id="TableTail">
				<div id="TableTail_inside">
				<form action="forum/forumShow.html" method="post" id="queryForm">
				<input type="hidden" name="id" value="${forum.id }">
					<table border="0" cellspacing="0" cellpadding="0" height="100%" align="left">
						<tr valign=bottom>
							<td>
								<select name="viewType">
									<option value="0" ${condition.viewType eq 0 ? 'selected' : '' }>全部主题</option>
									<option value="1" ${condition.viewType eq 1 ? 'selected' : '' }>全部精华贴</option>
								</select>
								<select name="orderBy">
									<option value="0" ${condition.orderBy eq 0 ? 'selected' : '' }>默认排序（按最后更新时间排序，但所有置顶帖都在前面）</option>
									<option value="1" ${condition.orderBy eq 1 ? 'selected' : '' }>按最后更新时间排序</option>
									<option value="2" ${condition.orderBy eq 2 ? 'selected' : '' }>按主题发表时间排序</option>
									<option value="3" ${condition.orderBy eq 3 ? 'selected' : '' }>按回复数量排序</option>
								</select>
								<select name="reverse">
									<option value="true" ${condition.reverse ? 'selected' : '' }>降序</option>
									<option value="false" ${condition.reverse ? '' : 'selected' }>升序</option>
								</select>
								<input type="IMAGE" src="script/forum/css/images/button/submit.png" align="ABSMIDDLE"/>
							</td>
						</tr>
					</table>
				</form>
				<!-- 分页插件 -->
				<div id="pagingDiv" style="float: right;"></div>
				</div>
			</div>
			
		</div>
	</center>
</div>

</body>
</html>
