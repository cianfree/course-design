package edu.zhku.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import edu.zhku.fr.log.Log;

/**
 * 处理资源文件，因为我们的配置中会有一些文件配置比较特殊，如classpath
 * 
 * @author XJQ
 * 
 *         2013-3-21
 */
public abstract class ResourceUtils {
	/**
	 * 默认加载器
	 */
	private static ClassLoader defaultLoader = ClassLoader.getSystemClassLoader();

	/**
	 * 根据特定的类加载器获取资源文件输入流，如果存在就返回一个非空输入流，否则返回null
	 * 
	 * @param path
	 * @param loader
	 * @return
	 */
	public static InputStream getResourceAsStream(String path, ClassLoader loader) {
		loader = getClassLoader(loader);
		// 1, 处理路径字符串，获取这个文件的物理绝对路径
		path = fixedPath(path);
		InputStream is = loader.getResourceAsStream(path);
		if (is != null) {
			return is;
		}
		try {
			return new FileInputStream(path);
		} catch (FileNotFoundException e) {
			Log.error("Sorry, can not find the resource.");
		}
		return null;
	}

	/**
	 * 获取这个路径对应的文件全路径，如果文件不存在，那么就返回null
	 * 
	 * @param path
	 * @param loader
	 * @return
	 */
	public static String getResourceRealPath(String path, ClassLoader loader) {
		loader = getClassLoader(loader);

		// 检查path,删除注入classpath,\\//
		path = fixedPath(path);

		// 先假设为classpath下面的配置文件
		URL url = loader.getResource(path); // 如果不存在，或者不是classpath下面的文件，那么就会返回null
		if (url != null) {
			if(isLinux()) return url.getFile();
			if(isWindows()) return url.getFile().substring(1);
			return url.getFile();
		}
		// 可能是文件系统中的资源，那么就用创建文件来检测
		File file = new File(path);
		if (file.exists()) {
			return file.getAbsolutePath();
		}
		return null;

	}

	private static boolean isWindows() {
		return System.getProperty("os.name").matches("(?i)(^win.*)");
	}
	
	private static boolean isLinux() {
		return System.getProperty("os.name").matches("(?i)(^linux.*)");
	}
	
	public static void main(String[] args) {
		System.out.println(System.getProperty("os.name").matches("(?i)(^win.*)"));
		System.out.println(System.getProperty("os.name").matches("(?i)(^linux.*)"));
		Properties pro = System.getProperties();
		Set<Entry<Object, Object>> sets = pro.entrySet();
		for (Entry<Object, Object> entry : sets) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
	}

	/**
	 * 处理路径信息，把无关的截取掉，如下：<br>
	 * classpath:config.xml --> config.xml<br>
	 * \\\config.xml --> config.xml<br>
	 * E:\jqxia\config.xml --> E:\jqxia\config.xml
	 * 
	 * @param path
	 * @return 如果path为空直接返回null
	 */
	public static String fixedPath(String path) {
		if (path != null) {
			path = path.replaceAll("(?i)(^classpath:)|(^[/]+)|(^[\\\\]+)", "").trim();
			path = path.replaceAll("(?i)(^classpath:)|(^[/]+)|(^[\\\\]+)", "").trim();
			path = path.replaceAll("(?i)(^classpath:)|(^[/]+)|(^[\\\\]+)", "").trim();
			return path;
		}
		return null;
	}
	
	/**
	 * 如有带有classpath就自带
	 * @param path
	 * @return
	 */
	public static String fixedPathWithClasspath(String path) {
		if (path != null) {
			path = path.replaceAll("(?i)(^[/]+)|(^[\\\\]+)", "").trim();
			path = path.replaceAll("(?i)(^[/]+)|(^[\\\\]+)", "").trim();
			path = path.replaceAll("(?i)(^classpath:)", "classpath:").trim();
			if(!path.startsWith("classpath:")) {
				path = "classpath:" + path;
			}
			return path;
		}
		return null;
	}
	
	/**
	 * 判断path是否存在
	 * @param path
	 * @return
	 */
	public static boolean isExists(String path) {
		return !(getResourceRealPath(path, null) == null);
	}

	/**
	 * 获取类加载器
	 * 
	 * @param loader
	 * @return
	 */
	private static ClassLoader getClassLoader(ClassLoader loader) {
		return loader == null ? defaultLoader : loader;
	}

}
