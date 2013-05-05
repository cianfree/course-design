package edu.zhku.fr.expections;

/**
 * 没有初始化配置中心异常
 *
 * @author XJQ
 * @since 2013-3-27
 */
public class NotInitializedConfigCenterException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NotInitializedConfigCenterException() {
		super("Sorry, you have not initialized the config center");
	}

	public NotInitializedConfigCenterException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotInitializedConfigCenterException(String message) {
		super(message);
	}

	public NotInitializedConfigCenterException(Throwable cause) {
		super(cause);
	}

}
