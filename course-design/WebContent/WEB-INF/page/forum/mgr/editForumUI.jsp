<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/include.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">   
<html>
<head>
	<title>编辑板块</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<script type="text/javascript" src="script/common/jquery/jquery.js"></script>
    <link type="text/css" rel="stylesheet" href="script/forum/css/pageCommon.css" />
</head>
<body>

<!-- 标题显示 -->
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="script/forum/css/images/title_arrow.gif"/> 版块设置
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<!--显示表单内容-->
<div id="MainArea">
    <form action="${empty forum ? 'forumMgr/saveForum.html' : 'forumMgr/updateForum.html' }" method="post">
    	<input type="hidden" name="id" value="${forum.id }">
        <div class="ItemBlock_Title1">
        </div>
        
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table cellpadding="0" cellspacing="0" class="mainForm">
                    <tr>
                        <td width="100">版块名称</td>
                        <td><input type="text" name="name" class="InputStyle" value="${forum.name }"/> *</td>
                    </tr>
                    <tr>
                        <td>版块说明</td>
                        <td><textarea name="description" class="TextareaStyle">${forum.description }</textarea></td>
                    </tr>
                </table>
            </div>
        </div>
        
        <!-- 表单操作 -->
        <div id="InputDetailBar">
            <input type="image" src="script/forum/css/images/save.png"/>
            <a href="javascript:history.back();"><img src="script/forum/css/images/goBack.png"/></a>
        </div>
    </form>
</div>

</body>
</html>