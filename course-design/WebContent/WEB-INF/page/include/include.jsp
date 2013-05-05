<%@page import="edu.zhku.fr.Key"%>
<%@page import="edu.zhku.fr.ConfigCenter"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="poj" uri="http://gz.cian.cn/pojtag" %>
<%@ taglib prefix="pf" uri="http://gz.cian.cn/pojfun"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";	
	if(ConfigCenter.getConfig(Key.ROOT_URL) != null) {
	    ConfigCenter.setConfig(Key.ROOT_URL, basePath);
	}
%>
<base href="<%=basePath %>">

<script type="text/javascript" >
<!--
(function(window){
	var oldJsonParse = window.JSON.parse;
	window.JSON.toJson = function (obj) {
		return window.JSON.stringify(obj);
	}
	
	window.JSON.parse = function(jsonStr) {
		var result = null;
		try {
			result = oldJsonParse(jsonStr);
		} catch (e) {
			result = eval("("+jsonStr+")");
		}
		return result;
	}
	window.ajaxUrl = "/req.aj";
})(window);
//-->
</script>
<script type="text/javascript" src="script/common/mypaging/i18n/jquery.mypaging-zh-CN.js"></script>
