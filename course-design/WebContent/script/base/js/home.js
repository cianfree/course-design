/**
 * 验证账户格式，长度大于6小于25个字符，不为空 返回验证的信息，如果验证通过就返回null
 */
function accountValidate(account) {
	if (!account)
		return "抱歉，用户名不能为空...";
	if (account.length < 5 || account.length > 25)
		return "抱歉，用户名必须是5-25个字符...";
	return null;
}

/**
 * 密码验证，密码不能为空，并且密码长度在6——25个字符
 */
function passwordValidate(pwd) {
	if (!pwd)
		return "抱歉，密码不能为空...";
	if (pwd.length < 5 || pwd.length > 25)
		return "抱歉，密码必须是5-25个字符...";
	return null;
}