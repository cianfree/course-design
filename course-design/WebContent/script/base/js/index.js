/**
 * 主页js
 */

function showView(name) {
	$("#currentOpt").html(name);
	return true;
}


$(function(){
	// 给退出系统按钮添加事件
	$("#logoutBtn").bind("click", function(){
		// 发起ajax请求，删除本次登录的用户信息
		$.ajax({
			url: ajaxUrl,
			type: "GET",
			data: {
				func: "base:logout"
			},
			dataType: "json",
			success: function (state) {
				if(state) {
					$(window).attr("location", "/user/loginUI.html");
				}
			}
		});
		
	});
	
});
