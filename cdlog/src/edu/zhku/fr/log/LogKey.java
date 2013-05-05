package edu.zhku.fr.log;

/**
 * 日志使用需要的key和value
 *
 * @author XJQ
 * @since 2013-3-23
 */
public enum LogKey {
	LEVEL("log.level", "error"),
	TARGET("log.target", "console"), 
	DIR("log.target.dir", "user.dir"), 
	DATE_PATTERN("log.date.pattern", "yyyy-MM-dd HH:mm:ss")
	
	;
	
	private String key;
	private String value;
	
	LogKey(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public String key() {
		return this.key;
	}
	
	public String value() {
		return this.value.toUpperCase();
	}
}
