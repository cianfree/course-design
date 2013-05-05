<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>帖子回复</title>
	<link type="text/css" rel="stylesheet" href="script/forum/css/forum.css" />
	<link type="text/css" rel="stylesheet" href="script/forum/css/pageCommon.css" />
	<script type="text/javascript" src="script/common/jquery/jquery.js"></script>
	<script type="text/javascript" src="script/common/ckeditor/ckeditor.js"></script>
	
    <script type="text/javascript">
		$(function(){
			CKEDITOR.replace('content', {
				// 配置信息
				//toolbar: 'Basic',
				//uiColor: '#9AB8F3'
				customConfig: 'self/onlineChat_config.js'	// 使用自定义的配置文件，推荐使用的方式
			});
		});
    </script>
</head>
<body>

<!-- 标题显示 -->
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="script/forum/css/images/title_arrow.gif"/> 帖子回复
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<!--显示表单内容-->
<div id="MainArea">
	<form action="${empty reply ? 'forum/replyTopic.html' : 'forum/updateReply.html' }" method="post">
		<input type="hidden" name="topicId" value="${topic.id }">
		<input type="hidden" name="id" value="${reply.id }">
		<div id="PageHead"></div>
		<center>
			<div class="ItemBlock_Title1">
				<div style="float:left; width: 85%;">
					<font class="MenuPoint"> &gt; </font>
					<poj:a href="forum/forumList.html">论坛</poj:a>
					<font class="MenuPoint"> &gt; </font>
					<poj:a href="forum/forumShow.html?id=${topic.forum.id}">${topic.forum.name}</poj:a>
					<font class="MenuPoint"> &gt;&gt; </font>
					帖子回复
				</div>
			</div>
			<div class="ItemBlockBorder">
				<table border="0" cellspacing="1" cellpadding="1" width="100%" id="InputArea">
					<tr>
						<td class="InputAreaBg" height="30" width="80px"><div class="InputTitle">帖子主题</div></td>
						<td class="InputAreaBg"><div class="InputContent">${topic.title}</div></td>
					</tr>
					<tr>
						<td class="InputAreaBg" height="30"><div class="InputTitle">标题</div></td>
						<td class="InputAreaBg">
							<div class="InputContent">
								<input type="text" name="title" value="回复：${topic.title }" class="InputStyle" style="width:100%;"/>
							</div>
						</td>
					</tr>
					<tr height="240">
						<td class="InputAreaBg"><div class="InputTitle">内容</div></td>
						<td class="InputAreaBg">
							<div class="InputContent">
								<textarea name="content" style="width:650px;height:200px;">${reply.content }</textarea>
							</div>
						</td>
					</tr>
					<tr height="30">
						<td class="InputAreaBg" colspan="2" align="center">
							<input type="image" src="script/forum/css/images/button/submit.png" style="margin-right:15px;"/>
							<a href="javascript:history.back();"><img src="script/forum/css/images/button/goBack.png"/></a>
						</td>
					</tr>
				</table>
			</div>
		</center>
	</form>
</div>
</body>
</html>