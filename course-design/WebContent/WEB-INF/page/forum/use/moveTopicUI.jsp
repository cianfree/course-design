<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>移动主题</title>
	<link type="text/css" rel="stylesheet" href="script/forum/css/forum.css" />
	<link type="text/css" rel="stylesheet" href="script/forum/css/pageCommon.css" />
</head>
<body>

<!-- 标题显示 -->
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="script/forum/css/images/title_arrow.gif"/> 移动主题
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<!--显示表单内容-->
<div id="MainArea">
<form action="forum/moveTopic.html" method="post">
	<input type="hidden" name="id" value="${topic.id }">
	
	<div id="PageHead"></div>
	<center>
		<div class="ItemBlock_Title1">
			<div style="float:left; width:85%;">
				<font class="MenuPoint"> &gt; </font>
				<poj:a href="forum/forumList.html">论坛</poj:a>
				<font class="MenuPoint"> &gt; </font>
				<poj:a href="forum/forumShow.html?id=${topic.forum.id }">${topic.forum.name }</poj:a>
				<font class="MenuPoint"> &gt;&gt; </font>
				移动主题
			</div>
		</div>
		<div class="ItemBlockBorder">
			<table border="0" cellspacing="1" cellpadding="1" width="100%" id="InputArea">
				<tr>
					<td class="InputAreaBg" height="30"><div class="InputTitle">帖子主题</div></td>
					<td class="InputAreaBg"><div class="InputContent">${topic.title }</div></td>
				</tr>
				<tr>
					<td class="InputAreaBg" height="30"><div class="InputTitle">移动到</div></td>
					<td class="InputAreaBg"><div class="InputContent">
						<select name="forumId" style="width: 100%;">
							<c:forEach items="${forumList }" var="forum">
								<option value="${forum.id }" ${forum.id eq topic.forum.id ? 'selected' : ''}>${forum.name }</option>
							</c:forEach>
						</select>
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
