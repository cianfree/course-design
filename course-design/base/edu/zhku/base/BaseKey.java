package edu.zhku.base;

import edu.zhku.fr.domain.Role;

public class BaseKey extends edu.zhku.fr.Key {
	public static final BaseKey BASE_CONFIG = new BaseKey("baseModuleConfigLocation", "base/base-config.xml");
	public static final BaseKey LANGUAGE = new BaseKey("language", "cn");
	public static final BaseKey USER_INIT_PWD = new BaseKey("user-init-pwd", "111111");
	public static final BaseKey SERVER_MAIL_ACCOUNT = new BaseKey("server-mail", "");
	public static final BaseKey ACTIVE_MAIL_CONTENT = new BaseKey("active-mail-content", "");
	public static final BaseKey ACTIVE_MAIL_SUBJECT = new BaseKey("active-mail-subject", "系统激活邮件");
	
	public static final BaseKey DEFAULT_ROLE = new BaseKey("default-role", new Role("普通用户", "用户在注册时候就是以普通用户的身份", Role.TYPE_NOT_DEL));
	
	// 系统固有角色
	public static final BaseKey INHERENT_ROLES = new BaseKey("inherent-roles", getList(Role.class, new Role("普通用户", "用户在注册时候就是以普通用户的身份", Role.TYPE_NOT_DEL)));
	
	// ---------------------------------------------------------

	protected BaseKey(Object key, Object defVal) {
		super(key, defVal);
	}

	protected BaseKey(Object key) {
		super(key);
	}
}
