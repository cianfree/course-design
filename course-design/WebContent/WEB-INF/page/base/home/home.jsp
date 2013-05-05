<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/include.jsp" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>ZHKU-POJ</title>
	<meta name="Keywords" content="ZHKU, 仲恺农业工程学院, POJ, 在线评测系统" />
	<meta name="" content="ZHKU, 仲恺农业工程学院, POJ, 在线评测系统" />
	<meta name="author" content="如有问题，请联系：仲恺农业工程学院-夏集球(xiajiqiu1990@163.com)" />
	
	<link href="script/base/css/layout.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="script/common/jquery/jquery.js"></script>

	<script type="text/javascript" src="script/base/js/menu.js"></script>
	<script type="text/javascript" src="script/base/js/index.js"></script>
</head>
<body>
<script type="text/javascript">
if(window.parent != window){
	window.parent.location.reload(true);
}
</script>
<c:if test="${empty sessionScope.currentUser}">
	<c:redirect url="user/loginUI.html"></c:redirect>
</c:if>
<div id="container">
  <div id="header">
  	<div class="bigTitle">Program Online Judge</div><div class="smallTitle">在线源程序测评系统</div>
  	<div id="headerOp">
  		<span class="welcome">欢迎您: ${sessionScope.currentUser.account }</span>
  		<input type="button" id="logoutBtn" value="退出系统" />
  	</div>
  </div>
  <div id="menu">
	<div id="navigator">
		<div class="nav" id="navMenu">
		<ul>
		  <li><a href="home/introduction.html" target="viewframe" title="网站首页" onclick="showView(this.title)">网站首页</a> 
		  </li>
		 
<!-- 循环显示权限 -->
<c:forEach items="${applicationScope.topPrivs }" var="topPriv">
	<li><poj:a pid="${topPriv.id }" href="${topPriv.action }" target="viewframe" title="${topPriv.name }" onclick="showView(this.title);return false;">${topPriv.name }</poj:a>
		<ul>
			<c:forEach items="${topPriv.children }" var="priv">
				<li><poj:a pid="${priv.id }" href="${priv.action }.html" target="viewframe" title="${priv.name }" onclick="showView(this.title)">${priv.name }</poj:a></li>
			</c:forEach>
		</ul>
	</li>
</c:forEach>
		 <li><a href="" onclick="javascript: return false;" class="simpleSpan">实用工具</a> 
			<ul>
				<li><a href="http://www.zhku.edu.cn" target="_blank" title="学校主页" onclick="showView(this.title)">学校主页</a> </li>
				<li><a href="http://qq.ip138.com/train/" target="_blank" title="火车时刻" onclick="showView(this.title)">火车时刻</a> </li>
				<li><a href="http://www.airchina.com.cn/" target="_blank" title="飞机航班" onclick="showView(this.title)">飞机航班</a> </li>
				<li><a href="http://www.ip138.com/post/" target="_blank" title="邮编/区号" onclick="showView(this.title)">邮编/区号</a> </li>
				<li><a href="http://www.timedate.cn/" target="_blank" title="国际时间" onclick="showView(this.title)">国际时间</a> </li>
			</ul>
		  </li>
		</ul>
		</div>
	</div>
	<!-- 
  	<div id="location">
  		当前位置: <span id="currentOpt">网站首页</span>
  	</div>
	 -->
  </div>
  
  <div id="mainContent">
  	<div id="mainFrame">
		<iframe id="viewframe" name="viewframe" src="home/introduction.html" scrolling="yes" width="100%" height="90%" allowtransparency="true" frameborder="0">
		</iframe>
	</div>
  </div>
  <div id="footer"></div>
</div>
</body>
</html>