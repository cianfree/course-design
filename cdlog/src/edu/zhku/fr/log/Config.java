package edu.zhku.fr.log;

import java.io.File;

/**
 * 日志配置
 *
 * @author XJQ
 * @since 2013-3-24
 */
public class Config {
	private Level level; // 日志级别
	private Target target; // 目标
	private File dir; // 输出目录

	public Level level() {
		return this.level;
	}

	public void level(Level level) {
		this.level = level;
	}

	public Target target() {
		return this.target;
	}

	public void target(Target target) {
		this.target = target;
	}

	public File dir() {
		return dir;
	}

	public void dir(File dir) {
		this.dir = dir;
	}
}
