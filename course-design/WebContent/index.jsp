<%-- 将jsp文件包含进来 --%>
<%@ include file="/WEB-INF/page/include/include.jsp" %>

<%-- 重定向，因为我们使用index.jsp作为虚拟的欢迎路径,跳转到登录界面 --%>
<c:choose>
	<c:when test="${applicationScope['frame.isDeploy'] }">
		<c:redirect url="/user/loginUI.html"/>
	</c:when>
	<c:otherwise>
		<c:redirect url="/frame/noModuleDeploy.html"/>
	</c:otherwise>
</c:choose>
