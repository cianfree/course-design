<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限设置</title>
<link rel="stylesheet" type="text/css" href="script/base/css/system.css">
<link rel="stylesheet" type="text/css" href="script/common/tree/jquery.treeview.css">

<script type="text/javascript" src="script/common/jquery/jquery.js"></script>
<script type="text/javascript" src="script/common/tree/jquery.treeview.js"></script>

<script type="text/javascript" src="script/base/js/privTreeUI.js"></script>


</head>
<body>
<div class="mainContent">

<div class="privTitle">
	正在为[${role.name }]角色配置权限
</div>

<div class="privTree">
<form action="role/savePriv.html" name="privForm" method="post" id="privForm">
<input type="hidden" name="roleId" value="${role.id }">
<input type="checkbox" id="selectAll"><span>全选</span>
<ul id="browser" class="treeview">
	<c:forEach items="${applicationScope.topPrivs }" var="topPriv">
		<li class="closed"><input type="checkbox" name="privId" value="${topPriv.id }" ${pf:isInPrivs(topPriv.id, privIds) ? 'checked' : ''}><span>${topPriv.name }</span>
			<ul>
			<c:forEach items="${topPriv.children }" var="child">
				<li class="closed"><input type="checkbox" name="privId" value="${child.id }" ${pf:isInPrivs(child.id, privIds) ? 'checked' : ''}><span>${child.name }</span>
					<ul>
						<c:forEach items="${child.children }" var="priv">
							<li class="closed"><input type="checkbox" name="privId" value="${priv.id }" ${pf:isInPrivs(priv.id, privIds) ? 'checked' : ''}><span>${priv.name }</span></li>
						</c:forEach>
					</ul>
				</li>
			</c:forEach>
			</ul>
		</li>
	</c:forEach>
	<!-- 
	<li class="closed"><input type="checkbox" name="privList" value="1"><span>在线测评</span>
		<ul>
			<li><input type="checkbox" name="privList" value="2"><span>题目管理</span>
				<ul>
					<li><input type="checkbox" name="privList" value="3"><span>题目列表</span></li>
					<li><input type="checkbox" name="privList" value="4"><span>删除题目</span></li>
					<li><input type="checkbox" name="privList" value="5"><span>修改题目</span></li>
					<li><input type="checkbox" name="privList" value="6"><span>解题目</span></li>
				</ul>
			</li>
			<li><input type="checkbox" name="privList"  value="7"><span>解题列表</span></li>
			<li><input type="checkbox" name="privList"  value="8"><span>个人汇总</span></li>
			<li><input type="checkbox" name="privList"  value="9"><span>所有汇总</span></li>
		</ul>
	</li>
	<li class="closed"><input type="checkbox" name="privList" value="10"><span>个人设置</span>
		<ul>
			<li><input type="checkbox" name="privList" value="11"><span>个人信息</span></li>
			<li><input type="checkbox" name="privList" value="12"><span>修改密码</span></li>
		</ul>
	</li>
	<li class="closed"><input type="checkbox" name="privList" value="13"><span>网上交流</span>
		<ul>
			<li><input type="checkbox" name="privList" value="14"><span>登录邮箱</span></li>
			<li><input type="checkbox" name="privList" value="15"><span>论坛系统</span>
				<ul>
					<li><input type="checkbox" name="privList" value="16"><span>板块列表</span></li>
					<li><input type="checkbox" name="privList" value="17"><span>主题列表</span></li>
					<li><input type="checkbox" name="privList" value="18"><span>文章列表</span></li>
					<li><input type="checkbox" name="privList" value="19"><span>回复帖子</span></li>
					<li><input type="checkbox" name="privList" value="20"><span>移动板块</span></li>
					<li><input type="checkbox" name="privList" value="21"><span>置为精华帖</span></li>
					<li><input type="checkbox" name="privList" value="22"><span>置为置顶帖</span></li>
					<li><input type="checkbox" name="privList" value="23"><span>置为普通帖</span></li>
				</ul>
			</li>
		</ul>
	</li>
	 -->
</ul>
</form>
</div>

<div class="privOpration">
	<div>
		<span class="privBtn">提交</span>
		<span class="privBtn">返回</span>
	</div>
</div>

</div>
</body>
</html>