$(function(){
	var $roleName = $("#roleName");
	var oldName = $roleName.val()==null || $roleName.val() == "" ? null : $roleName.val();
	
	// 验证角色，如角色名是否已经存在
	function validateRole() {
		var roleName = $roleName.val();
		if(!roleName) {
			return "角色名不能为空";
		}
		// 发起Ajax请求获取数据
		var msg = null;
		$.ajax({
			url: ajaxUrl,
			type: "GET",
			dataType: "json",
			data: {
				func: "base:hasRole",
				name: roleName
			},
			success: function(state) {
				if(state == 0) msg = "角色名不能为空";
				else if(state == 1) msg = "角色名已经存在！";
			}
		});
		return msg;
	}
	
	var $msg = $("#tipSpan");
	
	function isEmpty(obj) {
		if(obj == null || obj == "" || obj == undefined) {
			return true;
		}
		return false;
	}
	
	function validateRoleName() {
		var name = $roleName.val();
		if(!name) {
			$msg.html("角色名不能为空");
			return;
		}
		$.ajax({
			url: ajaxUrl,
			type: "GET",
			dataType: "json",
			data: {func: "base:hasRole", name: name},
			success: function(state) {
				switch(state){
					case 0: $msg.html("角色名不能为空"); $roleName.focus(); break;
					case 1: $msg.html("对不起，该角色名已经存在"); $roleName.focus().val(""); break;
					case 2: $msg.html(""); break;
					default: break;
				}
			}
		});
	}
	
	$roleName.bind("blur", function(){
		if(isEmpty($("#roleId").val())) {
			validateRoleName();
			return;
		}
		if($roleName.val() != oldName) {
			validateRoleName();
		}
	});
	
});