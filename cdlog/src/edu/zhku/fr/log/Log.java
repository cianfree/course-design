package edu.zhku.fr.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * 日志抽象类
 * 
 * @author XJQ
 * @since 2013-3-24
 */
public abstract class Log {
	private static boolean initialized = false; // 是否已经初始化了
	private static SimpleDateFormat format; // 日期格式化

	// ---------------------------------------
	private static Config config = new Config();

	// -----------------------------------
	/**
	 * 根据配置文件进行初始化日志类，如果没有设置配置文件，那么就加载默认的配置文件log.cf<br>
	 * 如果没有配置默认的配置文件，那么就使用默认的配置<br>
	 * 
	 * @param path
	 */
	public static void init(String path) {
		if (!isInitialized()) {
			if (path == null || "".equals(path)) {
				path = "log.cf";
			}
			Properties pro = new Properties();
			try {
				InputStream is = Log.class.getClassLoader()
						.getResourceAsStream(path);
				pro.load(is);
				config.level(loadLevel(pro));
				config.target(loadTarget(pro));
				config.dir(loadDir(pro));
				loadDateFoemat(pro);
				initialized = true;
			} catch (Exception e) {
				initDefault();
			}
		}
	}

	/**
	 * 默认初始化
	 */
	private static void initDefault() {
		initialized = true;
		config.level(Level.Error);
		config.target(Target.get(LogKey.TARGET.value()));
		File dir = new File(System.getProperty("user.dir"), "logs");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		config.dir(dir);
		loadDateFoemat(null);
	}

	/**
	 * 初始化日志的日期格式化
	 * 
	 * @param pro
	 */
	private static void loadDateFoemat(Properties pro) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		if (pro != null) {
			pattern = pro.getProperty(LogKey.DATE_PATTERN.key(),
					LogKey.DATE_PATTERN.value());
		}
		try {
			format = new SimpleDateFormat(pattern);
		} catch (Exception e) {
			pattern = LogKey.DATE_PATTERN.value();
			try {
				format = new SimpleDateFormat(pattern);
			} catch (Exception e2) {
				format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			}
		}
	}

	private static File loadDir(Properties pro) {
		String dirType = pro.getProperty(LogKey.DIR.key(), LogKey.DIR.value());
		String path = "";
		if ("user.dir".equals(dirType)) {
			path = System.getProperty("user.dir");
		} else {
			path = dirType;
		}
		File file = new File(path, "logs");
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	/**
	 * 获取输出目标
	 * 
	 * @param pro
	 * @return
	 */
	private static Target loadTarget(Properties pro) {
		System.out.println("target: " + pro.getProperty(LogKey.TARGET.key()));
		return Target.get(pro.getProperty(LogKey.TARGET.key(),
				LogKey.TARGET.value()));
	}

	/**
	 * 获取日志级别
	 * 
	 * @param pro
	 * @return
	 */
	private static Level loadLevel(Properties pro) {
		String levelStr = pro.getProperty(LogKey.LEVEL.key(),
				LogKey.LEVEL.value());
		return Level.getLevel(levelStr);
	}

	/**
	 * 判断是否已经初始化过了
	 * 
	 * @return
	 */
	private static boolean isInitialized() {
		return initialized;
	}

	// -----------------------------------------------------------------------
	/**
	 * 输出调试信息日志
	 * 
	 * @param message
	 */
	public static void debug(Throwable e) {
		writeLog(e, Level.Debug);
	}

	/**
	 * 输出调试信息日志
	 * 
	 * @param message
	 */
	public static void debug(String message, Throwable ex) {
		writeLog(message, ex, Level.Debug);
	}

	public static void debug(String message) {
		writeLog(message, Level.Debug);
	}

	/**
	 * 输出调试信息日志
	 * 
	 * @param message
	 */
	public static void info(Throwable e) {
		writeLog(e, Level.Info);
	}

	/**
	 * 输出调试信息日志
	 * 
	 * @param message
	 */
	public static void info(String message, Throwable ex) {
		writeLog(message, ex, Level.Info);
	}

	public static void info(String message) {
		writeLog(message, Level.Info);
	}

	/**
	 * 输出调试信息日志
	 * 
	 * @param message
	 */
	public static void warn(Throwable e) {
		writeLog(e, Level.Warn);
	}

	/**
	 * 输出调试信息日志
	 * 
	 * @param message
	 */
	public static void warn(String message) {
		writeLog(message, Level.Warn);
	}

	public static void warn(String message, Throwable ex) {
		writeLog(message, ex, Level.Warn);
	}

	/**
	 * 输出调试信息日志
	 * 
	 * @param message
	 */
	public static void error(Throwable e) {
		writeLog(e, Level.Error);
	}

	public static void error(String message, Throwable e) {
		writeLog(message, e, Level.Error);
	}

	/**
	 * 输出调试信息日志
	 * 
	 * @param message
	 */
	public static void error(String message) {
		writeLog(message, Level.Error);
	}

	/**
	 * 输出调试信息日志
	 * 
	 * @param message
	 */
	public static void fatal(String message, Throwable e) {
		writeLog(message, e, Level.Fatal);
	}

	public static void fatal(Throwable e) {
		writeLog(e, Level.Fatal);
	}

	/**
	 * 输出调试信息日志
	 * 
	 * @param message
	 */
	public static void fatal(String message) {
		writeLog(message, Level.Fatal);
	}

	private static void writeLog(Throwable ex, Level level) {
		writeLog(ex.getMessage(), ex, level);
	}

	/**
	 * 输出调试日志
	 * 
	 * @param message
	 * @param level
	 */
	private static void writeLog(String message, Level level) {
		if (!isInitialized()) {
			init(null);
		}
		// 判断要不要输出日志信息
		if (!isWriteLog(level)) {
			return;
		}
		// 检测输出目标，[控制台还是文件，如果是文件，那么就需要每天创建一个日志文件]
		if (config.target().equals(Target.CONSOLE)) {
			System.out.println("[" + format.format(new Date()) + "]["
					+ level.level() + "] " + message);
		} else {
			// 检查日志文件，每天一个日志文件
			String fileName = logFileFormat.format(new Date()) + ".log";
			File logFile = new File(config.dir(), fileName);
			FileWriter writer = null;
			try {
				writer = new FileWriter(logFile, true);
				writer.write("[" + format.format(new Date()) + "]["
						+ level.level() + "] " + message
						+ System.getProperty("line.separator"));
			} catch (IOException e) {
				System.out.println("[Error] Log.writeLog() ");
			} finally {
				try {
					writer.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private static void writeLog(String message, Throwable ex, Level level) {
		if (!isInitialized()) {
			init(null);
		}
		// 判断要不要输出日志信息
		if (!isWriteLog(level)) {
			return;
		}
		// 检测输出目标，[控制台还是文件，如果是文件，那么就需要每天创建一个日志文件]
		if (config.target().equals(Target.CONSOLE)) {
			System.out.println("[" + format.format(new Date()) + "]["
					+ level.level() + "] " + message);
		} else {
			// 检查日志文件，每天一个日志文件
			String fileName = logFileFormat.format(new Date()) + ".log";
			File logFile = new File(config.dir(), fileName);
			FileWriter writer = null;
			try {
				writer = new FileWriter(logFile, true);
				writer.write("[" + format.format(new Date()) + "]["
						+ level.level() + "] " + message
						+ System.getProperty("line.separator"));
				if (ex != null) {
					ex.printStackTrace(new PrintWriter(writer));
				}
			} catch (IOException e) {
				System.out.println("[Error] Log.writeLog() ");
			} finally {
				try {
					writer.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * 判断是否要进行输出日志
	 * 
	 * @param level
	 * @return
	 */
	private static boolean isWriteLog(Level level) {
		return config.level().code() <= level.code();
	}

	private static SimpleDateFormat logFileFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static void main(String[] args) {
		Log.init("log.cf");
		Log.info("这是info信息");
		Log.debug("这是debug信息");
		Log.warn("这是warn信息");
		Log.error("这是error信息");
		Log.fatal("这是fatal信息");
		
		// System.out.println(System.getProperty("user.dir"));
	}
}
