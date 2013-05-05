package edu.zhku.pj.core;


/**
 * 定义接口
 *
 * @author XJQ
 * @since 2013-3-8
 */
public interface SourceHandler {
	/**
	 * 执行代码
	 * @param sourceStr
	 * @param input
	 * @param output
	 * @return
	 */
	public HandleStatus handle(String sourceStr, String input, String output);
	
	/**
	 * 设置工作目录
	 * @param workFolder
	 */
	public void setWorkFolder(String workFolder);
	
	/**
	 * 获取工作目录
	 * @return
	 */
	public String getWorkFolder();
	
	/**
	 * 获取语言的String-key
	 * @return
	 */
	public String getLanguageKey();
	
	/**
	 * 设置语言的key
	 * @param key
	 */
	public void setLanguageKey(String key);
}
