package edu.zhku.fr.expections;

/**
 * 框架错误
 * 
 * @author XJQ
 * 2013-4-20
 */
@SuppressWarnings("serial")
public class FrameException extends RuntimeException {

	public FrameException() {
		super();
	}

	public FrameException(String message, Throwable cause) {
		super(message, cause);
	}

	public FrameException(String message) {
		super(message);
	}

	public FrameException(Throwable cause) {
		super(cause);
	}

	
}
