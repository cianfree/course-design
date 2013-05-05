<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>发表新主题</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script type="text/javascript" src="script/common/jquery/jquery.js"></script>
    <link type="text/css" rel="stylesheet" href="script/forum/css/pageCommon.css" />
	<link type="text/css" rel="stylesheet" href="script/forum/css/forum.css" />

	<script type="text/javascript" src="script/common/ckeditor/ckeditor.js"></script>
    <script type="text/javascript">
		$(function(){
			/*
			var fck = new FCKeditor("content");
			fck.Width = "99%";
			fck.Height = "100%";
			fck.ToolbarSet = "bbs";
			fck.BasePath = "script/fckeditor/";
			fck.ReplaceTextarea();
			*/
			
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
            <img border="0" width="13" height="13" src="script/forum/css/images/title_arrow.gif"/> 发表新主题
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<!--显示表单内容-->
<div id="MainArea">
<form action="${empty topic ? 'forum/saveTopic.html' : 'forum/updateTopic.html'}" style="margin: 0; padding: 0;" method="post">
	<input type="hidden" name="forumId" value="${forum.id }">
	<input type="hidden" name="id" value="${topic.id }">
	<div id="PageHead"></div>
	<center>
		<div class="ItemBlock_Title1">
			<div style="float:left;width:85%;">
				<font class="MenuPoint"> &gt; </font>
				<a href="forum/forumList.html">论坛</a>
				<font class="MenuPoint"> &gt; </font>
				<a href="forum/forumShow.html?id=${forum.id }">${forum.name }</a>
				<font class="MenuPoint"> &gt;&gt; </font>
				发表新主题
			</div>
		</div>
		<div class="ItemBlockBorder">
			<table border="0" cellspacing="1" cellpadding="1" width="100%" id="InputArea">
				<tr>
					<td class="InputAreaBg" height="30"><div class="InputTitle">标题</div></td>
					<td class="InputAreaBg"><div class="InputContent">
						<input type="text" name="title" class="InputStyle" style="width:100%;" value="${topic.title }"/></div>
					</td>
				</tr>
				<tr height="240">
					<td class="InputAreaBg"><div class="InputTitle">内容</div></td>
					<td class="InputAreaBg"><div class="InputContent"><textarea name="content">${topic.content }</textarea></div></td>
				</tr>
				<tr height="30">
					<td class="InputAreaBg" colspan="2" align="center">
						<input type="image" src="script/forum/css/images/button/submit.png" style="margin-right:15px;"/>
						<a href="javascript:window.history.back();"><img src="script/forum/css/images/button/goBack.png"/></a>
					</td>
				</tr>
			</table>
		</div>
	</center>
</form>
</div>

</body>
</html>
