package edu.zhku.fr.log;

/**
 * 目标
 *
 * @author XJQ
 * @since 2013-3-24
 */
public enum Target {
	CONSOLE("console"),
	DIR("dir");
	
	private String value;
	
	Target(String value) {
		this.value = value;
	}
	
	public String value() {
		return this.value;
	}
	
	public static Target get(String key) {
		if("dir".equals(key)) {
			return Target.DIR;
		}
		return Target.CONSOLE;
	}
}
