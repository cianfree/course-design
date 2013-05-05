package edu.zhku.pj.core;

/**
 * 语言类型
 * 
 * @author XJQ
 * @since 2013-3-9
 */
public class Language {
	private String name;
	private String template;
	private SourceHandler handler;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SourceHandler getHandler() {
		return handler;
	}

	public void setHandler(SourceHandler handler) {
		this.handler = handler;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	@Override
	public String toString() {
		return "Language [name=" + name + ", handler=" + handler + "]";
	}

}
