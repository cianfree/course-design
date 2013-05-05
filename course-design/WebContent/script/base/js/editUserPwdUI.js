$(function() {
	var $newPwd = $("#newPwd"), //
		$rePwd = $("#rePwd"), //
		$oldPwd = $("#oldPwd"), //
		$tips = $("#tips");
	$oldPwd.focus();

	// 添加检查事件
	$oldPwd.bind("blur", function() {
				var oldPwd = $oldPwd.val();
				if (isEmpty(oldPwd)) {
					$tips.html("必须输入旧密码...");
				} else if (oldPwd.length < 5 || oldPwd.length > 25) {
					$tips.html("密码长度必须在5-25位之间...");
				} else {
					$tips.html("");
				}
			});

	$newPwd.bind("blur", function() {
				var newPwd = $newPwd.val();

				if (isEmpty(newPwd)) {
					$tips.html("必须输入新密码...");
				} else if (newPwd.length < 5 || newPwd.length > 25) {
					$tips.html("密码长度必须在5-25位之间...");
				} else if (newPwd == $oldPwd.val()) {
					$tips.html("新密码不能和旧密码一样...");
					$newPwd.focus().val("");
				} else {
					if (!isEmpty($rePwd.val())) {
						if ($rePwd.val() != $newPwd.val()) {
							$tips.html("前后密码输入不一致...");
						} else {
							$tips.html("");
						}
					} else {
						$tips.html("");
					}
				}
			});

	$rePwd.bind("blur", function() {
				if (isEmpty($rePwd.val())) {
					$tips.html("重复输入密码不能为空...");
				} else {
					if (!isEmpty($newPwd.val())) {
						if ($rePwd.val() != $newPwd.val()) {
							$tips.html("前后密码不一致 ...");
						} else {
							$tips.html("");
						}
					} else {
						$tips.html("");
					}
				}
			});

	function isEmpty(val) {
		if (val == null || val == "" || val == undefined)
			return true;
		return false;
	}

	$("#pwdForm").bind("submit", function() {
				return validate();
			});

	// 验证
	function validate() {
		var oldPwd = $oldPwd.val(), //
			newPwd = $newPwd.val(), //
			rePwd = $rePwd.val();

		if (isEmpty(oldPwd)) {
			$tips.html("必须输入旧密码...");
			return false;
		} else if (isEmpty(newPwd)) {
			$tips.html("必须输入新密码...");
			return false;
		} else if (newPwd.length < 5 || newPwd.length > 25) {
			$tips.html("密码长度必须在5-25位之间...");
			return false;
		} else if (newPwd != rePwd) {
			$tips.html("前后密码不一致...");
			return false;
		} else if (newPwd == oldPwd) {
			$tips.html("新密码不能和旧密码一样...");
			return false;
		}
		return true;
	}
});