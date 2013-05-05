<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link type="text/css" rel="stylesheet" href="script/base/css/introduction.css"/>
<link type="text/css" rel="stylesheet" href="script/common/jqueryui/jqueryui.css" />

<script type="text/javascript" src="script/common/jquery/jquery.js"></script>
<script type="text/javascript" src="script/common/jqueryui/jqueryui.js"></script>

<script type="text/javascript">
	$(function(){
		$("#left,#center,#right").draggable({ 
				containment: "parent",
				revert: true	// 拖动后回到原地方
		});
	});
</script>
</head>
<body>
	<div id="webIntro" class="webIntro">
		<div id="left">
			<div class="title">POJ源程序测评系统(Program Online Judge, POJ)</div>
			<div class="content">
				<span class="from">仲恺农业工程学院计算机学院毕业设计</span><br/><br/>
				研制人：夏集球<br/>
				院系：计算机科学与工程学院<br/>
				专业：计算机科学与技术092班<br/>
				电邮：xiajiqiu1990@163.com<br/><br/> 
				Java版本：java 1.6.0_37<br/>
				gcc/g++版本：3.4.5<br/><br/>
				<span class="copyright">版权所有,侵权必究</span>
			</div>
		</div>
		<div id="center">
			<div class="title">本系统的评测状态(Judge Status)</div>
			<div class="content">
			[1] Accepted:解答正确<br/>
			[2] Presentation Error:输出格式错误<br/>
			[3] Compilation Error:编译错误<br/>
			</div>
		</div>
		<div id="right">
			<div class="title">功能描述</div>
			<div class="content">
			2013-3-1 POJ源程序测评系统(Program Online Judge, POJ)1.0研制成功。<br/>
			2013-3-1 POJ源程序测评系统(Program Online Judge, POJ)1.0测试完成。<br/>
			★自动评测C/C++/Java源程序<br/>
			</div>
		</div>
	</div>
</body>
</html>