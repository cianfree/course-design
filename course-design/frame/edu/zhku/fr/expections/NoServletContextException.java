package edu.zhku.fr.expections;

/**
 * 未提供ServletContextEvent对象异常
 *
 * @author XJQ
 * @since 2013-3-27
 */
public class NoServletContextException extends FrameException {

	private static final long serialVersionUID = 1L;

	public NoServletContextException() {
		super("You have not provider the ServletContextEvent.");
	}

	public NoServletContextException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoServletContextException(String message) {
		super(message);
	}

	public NoServletContextException(Throwable cause) {
		super(cause);
	}
}
