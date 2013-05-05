<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>温馨提示</title>
<script type="text/javascript">
function refreshParent() {
	if(window.parent != window){
		window.parent.location.reload(true);
	}
	return true;
}
</script>

</head>
<body style="background-color: #F3F9FD;">
<h3 align="center" style="color: red;">对不起，您没有权限进行此操作.....</h3>
<h3 align="center"><a href="/" onclick="return refreshParent();">点击此返回主页>></a></h3>
</body>
</html>