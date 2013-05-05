package edu.zhku.pj.core;

/**
 * 源代码处理器
 *
 * @author XJQ
 * @since 2013-3-9
 */
public interface SourceHandlerManager {
	/**
	 * 设置语言
	 * @param languages
	 */
	public void setLanguages(Language[] languages);
	
	/**
	 * 获取语言
	 * @return
	 */
	public Language[] getLanguages();
	
	/**
	 * 获取指定的语言
	 * @param language
	 * @return
	 */
	public Language getLanguage(String language);
	
	/**
	 * 根据语言类型获取相应的处理器
	 * @param language
	 * @return
	 */
	public SourceHandler getSourceHandler(String language);
	
	/**
	 * 设置全局工作目录
	 * @param workFolder
	 */
	public void setWorkFolder(String workFolder);
	
	/**
	 * 获取全局工作目录
	 * @return
	 */
	public String getWorkFolder();
}
