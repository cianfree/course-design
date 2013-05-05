$(function() {
	
	var $account = $("#account"),
		$email = $("#email"),
		$userForm = $("#userForm"),
		$accountTip = $account.siblings("span"),
		$emailTip = $email.siblings("span");
	
	$("#submitBtn").bind("click", function(){
		$userForm.trigger("submit")
	});
	
	$("#resetBtn").bind("click", function(){
		$userForm.trigger("reset");
	});
	
	$("#returnBtn").bind("click", function(){
		window.history.back();
	});
	
	var flag = false;
	
	// 判断是否是修改用户信息
	function isModify() {
		return $("#userId").val();
	}
	
	$userForm.bind("submit", function(){
		if(!isModify()) {
			return flag;
		}
		return true;
	});

	// 验证表单，主要验证Account和Email格式正不正确
	function validate() {
		// 验证帐号account
		return validateAccount() && validateEmail();
	}
	
	function validateAccount() {
		var account = $account.val();
		if(!account) {
			$accountTip.html("帐号不能为空...");
			return false;
		}
		// 进行前端验证
		if(account.length < 5 || account.length > 25) {
			$accountTip.html("用户名长度必须在5-25个字符之间...");
			return false;
		}
		return true;
	}
	
	function validateEmail() {
		if(!$email.val()) {
			return false;
		}
		if(!Validator.isEmail($email.val())) {
			$emailTip.html("邮箱格式不正确...");
			return false;
		}
		return true;
	}
	
	// 用户账户验证，使用Ajax验证用户账户是否存在
	$account.bind("blur", function(){
		if(!validateAccount()) {
			flag = false;
			return ;
		}
		
		// 发起ajax请求验证
		$.getJSON(ajaxUrl,{func: "base:hasUser", account: $account.val()}, function(has){
			flag = false;
			switch(has) {
				case 1:
				case 2:
					$accountTip.html("对不起，用户名已经存在...");
					$account.val("");
					break;
				case 0:
					$accountTip.html("");
					flag = true;
					break;
				default: break;
			}
		});
	});
	
	// 如果隐藏的id是空的就说明此时是添加，那么就需要验证邮箱是否存在
	if(!$("#userId").val()) {
		$email.bind("blur", function(){
			if(!validateEmail()) {
				flag = false;
				return ;
			}
			// TODO 这里的邮箱检测还有待改善，考虑使用策略模式来进行验证，因为系统可能允许或不允许多个用户使用同一个邮箱
			/*
			$.getJSON("user/hasEmail.html", {email: $email.val()}, function(state){
				// state 有三种状态，1--存在，-1--格式不正确，0--不存在
				flag = false;
				if(state == 0) {
					// 不存在该注册的邮件，可以注册
					$emailTip.html("");
					flag = true;
				} else if(state == 1) {
					// 已经存在，不能注册
					$emailTip.html("该邮箱已经注册，请重新填写！");
					$email.val("");
				} else if(state == -1) {
					// 格式不正确
					$emailTip.html("邮箱格式不正确，请重新填写！");
					$email.val("");
				} else {
					// 服务器出错了
					$emailTip.html("抱歉，服务器出错了，请稍后尝试！");
					$email.val("");
				}
			});
			*/
		});
	}
});