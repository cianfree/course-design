<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>查看主题：新手发帖</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<script type="text/javascript" src="script/common/jquery/jquery.js"></script>
    <link type="text/css" rel="stylesheet" href="script/forum/css/pageCommon.css" />
	<link type="text/css" rel="stylesheet" href="script/forum/css/forum.css" />
	
	<link type="text/css" rel="stylesheet" href="script/common/mypaging/css/mypaging.css"/>
	<script type="text/javascript" src="script/common/mypaging/jquery.mypaging.js"></script>
	
	<script type="text/javascript" src="script/common/ckeditor/ckeditor.js"></script>
    <script type="text/javascript">
		$(function(){
			CKEDITOR.replace('content', {
				// 配置信息
				//toolbar: 'Basic',
				//uiColor: '#9AB8F3'
				customConfig: 'self/forum-config.js'	// 使用自定义的配置文件，推荐使用的方式
			});
			
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
            <img border="0" width="13" height="13" src="script/forum/css/images/title_arrow.gif"/> 查看主题
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<!--内容显示-->	
<div id="MainArea">
	<div id="PageHead"></div>
	<center>
		<div class="ItemBlock_Title1" style="width: 98%">
			<font class="MenuPoint"> &gt; </font>
			<a href="forum/forumList.html">论坛</a>
			<font class="MenuPoint"> &gt; </font>
			<a href="forum/forumShow.html?id=${topic.forum.id }">${topic.forum.name }</a>
			<font class="MenuPoint"> &gt;&gt; </font>
			帖子阅读
			<span style="margin-left:30px;"><a href="forum/editTopicUI.html?forumId=${topic.forum.id }">
				<img align="absmiddle" src="script/forum/css/images/button/publishNewTopic.png"/></a>
			</span>
		</div>
		
		<div class="ForumPageTableBorder dataContainer">
		
			<!--显示主题标题等-->
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr valign="bottom">
				<td width="3" class="ForumPageTableTitleLeft">&nbsp;</td>
					<td class="ForumPageTableTitle"><b>本帖主题：${topic.title }</b></td>
					<td class="ForumPageTableTitle" align="right" style="padding-right:12px;">
						<a class="detail" href="forum/replyTopicUI.html?topicId=${topic.id }"><img border="0" src="script/forum/css/images/reply.gif" />回复</a>
						<a href="forum/moveTopicUI.html?id=${topic.id }"><img border="0" src="script/forum/css/images/edit.gif" />移动到其他版块</a>
						<a href="forum/creamTopic.html?id=${topic.id }&forumId=${topic.forum.id }" onClick="return confirm('要把本主题设为精华吗？')"><img border="0" src="script/forum/css/images/forum_hot.gif" />精华</a>
						<a href="forum/topTopic.html?id=${topic.id }&forumId=${topic.forum.id }" onClick="return confirm('要把本主题设为置顶吗？')"><img border="0" src="script/forum/css/images/forum_top.gif" />置顶</a>
						<a href="forum/ordinaryTopic.html?id=${topic.id }&forumId=${topic.forum.id }" onClick="return confirm('要把本主题设为普通吗？')"><img border="0" src="script/forum/css/images/forum_comm.gif" />普通</a>
					</td>
					<td width="3" class="ForumPageTableTitleRight">&nbsp;</td>
				</tr>
				<tr height="1" class="ForumPageTableTitleLine"><td colspan="4"></td></tr>
			</table>

			<!-- ~~~~~~~~~~~~~~~ 显示主帖 ~~~~~~~~~~~~~~~ -->
			<!-- 如果是第一页才显示主帖 -->
			<c:if test="${pb.currentPage == 1 || pb.recordCount < 1}">
			<div class="ListArea">
				<table border="0" cellpadding="0" cellspacing="1" width="100%">
					<tr>
						<td rowspan="3" width="130" class="PhotoArea" align="center" valign="top">
							<!--作者头像-->
							<div class="AuthorPhoto">
								<img border="0" width="110" height="110" src="script/forum/css/images/defaultAvatar.gif" 
									onerror="this.onerror=null; this.src='script/forum/css/images/defaultAvatar.gif';" />
							</div>
							<!--作者名称-->
							<div class="AuthorName">${topic.author.name}</div>
						</td>
						<td align="center">
							<ul class="TopicFunc">
								<!--操作列表-->
								<li class="TopicFuncLi">
									<a class="detail" href="forum/editTopicUI.html?forumId=${topic.forum.id }&topicId=${topic.id}"><img border="0" src="script/forum/css/images/edit.gif" />编辑</a>
									<a class="detail" href="forum/deleteTopic.html?id=${topic.id }&forumId=${topic.forum.id }" onClick="return confirm('确定要删除本帖主题吗？')"><img border="0" src="script/forum/css/images/delete.gif" />删除</a>
								</li>
								<!-- 文章表情与标题 -->
								<li class="TopicSubject">
									${topic.title }
								</li>
							</ul>
						</td>
					</tr>
					<tr><!-- 文章内容 -->
						<td valign="top" align="center">
							<div class="Content">${topic.content }</div>
						</td>
					</tr>
					<tr><!--显示楼层等信息-->
						<td class="Footer" height="28" align="center" valign="bottom">
							<ul style="margin: 0px; width: 98%;">
								<li style="float: left; line-height:18px;"><font color=#C30000>[楼主]</font>
									${topic.postTime }
								</li>
								<li style="float: right;"><a href="javascript:scroll(0,0)">
									<img border="0" src="script/forum/css/images/top.gif" /></a>
								</li>
							</ul>
						</td>
					</tr>
				</table>
			</div>
			</c:if>
			
			<!-- ~~~~~~~~~~~~~~~ 显示主帖结束 ~~~~~~~~~~~~~~~ -->

			<!-- ~~~~~~~~~~~~~~~ 显示回复列表 ~~~~~~~~~~~~~~~ -->
			<div class="ListArea template">
				<table border="0" cellpadding="0" cellspacing="1" width="100%">
					<c:forEach items="${pb.recordList }" var="reply" varStatus="status">
					<tr>
						<td rowspan="3" width="130" class="PhotoArea" align="center" valign="top">
							<!--作者头像-->
							<div class="AuthorPhoto">
								<img border="0" width="110" height="110" src="script/forum/css/images/defaultAvatar.gif" 
									onerror="this.onerror=null; this.src='script/forum/css/images/defaultAvatar.gif';" />
							</div>
							<!--作者名称-->
							<div class="AuthorName">reply.author.name</div>
						</td>
						<td align="center">
							<ul class="TopicFunc">
								<!--操作列表-->
								<li class="TopicFuncLi">
									<a class="detail" href="forum/replyTopicUI.html?replyId=${reply.id }&topicId=${topic.id}"><img border="0" src="script/forum/css/images/edit.gif" />编辑</a>
								</li>
								<!-- 文章表情与标题 -->
								<li class="TopicSubject">
									${reply.title}
								</li>
							</ul>
						</td>
					</tr>
					<tr><!-- 文章内容 -->
						<td valign="top" align="center">
							<div class="Content">${reply.content}</div>
						</td>
					</tr>
					<tr><!--显示楼层等信息-->
						<td class="Footer" height="28" align="center" valign="bottom">
							<ul style="margin: 0px; width: 98%;">
								<li style="float: left; line-height:18px;"><font color=#C30000>[${(pb.currentPage - 1) * pb.pageSize + status.count}楼]</font>
									${reply.postTime }
								</li>
								<li style="float: right;"><a href="javascript:scroll(0,0)">
									<img border="0" src="script/forum/css/images/top.gif" /></a>
								</li>
							</ul>
						</td>
					</tr>
					</c:forEach>
				</table>
			</div>
			<!-- ~~~~~~~~~~~~~~~ 显示回复列表结束 ~~~~~~~~~~~~~~~ -->
		</div>

		<!-- 分页信息  -->
		<div id="pagingDiv" style="float: right;"></div>
		<form action="forum/topicShow.html" method="post" id="queryForm">
			<input type="hidden" name="id" value="${topic.id }">
		</form>
		
		<div class="ForumPageTableBorder" style="margin-top: 25px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr valign="bottom">
					<td width="3" class="ForumPageTableTitleLeft">&nbsp;</td>
					<td class="ForumPageTableTitle"><b>快速回复</b></td>
					<td width="3" class="ForumPageTableTitleRight">&nbsp;</td>
				</tr>
				<tr height="1" class="ForumPageTableTitleLine">
					<td colspan="3"></td>
				</tr>
			</table>
		</div>
	</center>
	
	<!--快速回复-->
	<div class="QuictReply">
	<form action="forum/replyTopic.html" method="post">
		<input type="hidden" name="topicId" value="${topic.id }">
		<div style="padding-left: 3px;">
			<table border="0" cellspacing="1" width="98%" cellpadding="5" class="TableStyle">
				<tr height="30" class="Tint">
					<td width="50px" class="Deep"><b>标题</b></td>
					<td class="no_color_bg">
						<input type="text" name="title" class="InputStyle" value="回复：${topic.title }" style="width:90%"/>
					</td>
				</tr>
				<tr class="Tint" height="200">
					<td valign="top" rowspan="2" class="Deep"><b>内容</b></td>
					<td valign="top" class="no_color_bg">
						<textarea name="content" style="width: 95%; height: 300px"></textarea>
					</td>
				</tr>
				<tr height="30" class="Tint">
					<td class="no_color_bg" colspan="2" align="center">
						<input type="image" src="script/forum/css/images/button/submit.png" style="margin-right:15px;"/>
					</td>
				</tr>
			</table>
		</div>
	</form>
	</div>
</div>
</body>
</html>
