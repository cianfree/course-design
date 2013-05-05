$(function(){
	var $account = $("#account"),
		$password = $("#password"),
		$validateMsg = $("#validateMsg");
		
	$account.focus();
	// 响应Enter事件
	$(document).bind("keydown", function(event){
		if(event.keyCode == 13) {	// 如果是Enter键按下了
			if(validate()){
				$("#loginForm").submit();
			}
		}
	});

	// 给帐号添加事件相应，使用Ajax来判断是否存在用户
	$account.bind("blur", function(e) {
		// 前端验证
		var msg = accountValidate($account.val());
		if(msg) {
			$validateMsg.html(msg);
			$account.val("");
			return ;
		}
		$.getJSON(ajaxUrl,{func: "base:hasUser", account: $account.val()}, function(has){
			switch(has) {
				case 0:
					$validateMsg.html("用户名不存在...");
					$account.val("");
					break;
				case 1: 
					$validateMsg.html("该用户处于未激活状态，不能登录...");
					$account.val("").focus();
					break;
				case 2:
					$validateMsg.html("");
					break;
				default: break;
			}
		});
	});
	
	// 给登录按钮添加事件响应
	$("#loginImage").bind("click", function(){
		if(validate()){
			$("#loginForm").submit();
		}
	});
	
	function validate() {
		// 前端验证
		var msg = accountValidate($account.val());
		if(msg) {
			$validateMsg.html(msg);
			$account.val("");
			return false;
		}
		msg = passwordValidate($password.val());
		if(msg) {
			$validateMsg.html(msg);
			$password.val("");
			return false;
		}
		
		// 如果都没有问题就直接调用表单的submit方法
		return true;
	}
});