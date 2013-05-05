package edu.zhku.fr.log;

/**
 * 日志级别，总共有五个级别：<br>
 * 1, Debug: 开发的时候输出调试用的日志信息，主要是在开发的时候，对程序员使用		<br>
 * 2, Info： 输出一些系统执行时候信息		<br>
 * 3, Warn： 输出警告信息，警告信息在本系统中，只是起到警示作用，不一定会对系统有什么影响		<br>
 * 4, Error： 错误信息，除了这样的错误表示系统不正常了		<br>
 * 5, Fatal: 严重错误信息，系统启动的时候，由于某些错误的发生，就会导致系统无法继续运行下去，这个时候就需要重新配置系统了，检查系统的错误所在
 *
 * @author XJQ
 * @since 2013-3-23
 */
public enum Level {
	Debug("debug", 0),
	Info("info", 1),
	Warn("warn", 2),
	Error("error", 3),
	Fatal("fatal", 4)
	;
	
	private String level;
	private int code;
	
	Level(String level, int code) {
		this.level = level;
		this.code = code;
	}
	
	public String level() {
		return this.level;
	}
	
	public void level(String level) {
		this.level = level;
	}
	
	public int code() {
		return this.code;
	}
	
	public static Level getLevel(String level) {
		Level[] levels = Level.values();
		for (Level lv : levels) {
			if(lv.level().equals(level)) {
				return lv;
			}
		}
		return Level.Error;
	}

	public boolean lt(Level lv) {
		return this.code() < lv.code();
	}
	
	public boolean gt(Level lv) {
		return this.code() > lv.code();
	}
	
	public boolean eq(Level lv) {
		return this.code() == lv.code();
	}
}
