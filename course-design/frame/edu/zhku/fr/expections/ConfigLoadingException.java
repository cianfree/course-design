package edu.zhku.fr.expections;

/**
 * 配置加载错误，运行时异常
 * 
 * @author XJQ
 * @since 2013-3-23
 */
public class ConfigLoadingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ConfigLoadingException() {
		super("Default Exception tips: Frame Configuration loading exception");
	}

	public ConfigLoadingException(String message) {
		super(message);
	}

	public ConfigLoadingException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigLoadingException(Throwable cause) {
		super(cause);
	}

}
