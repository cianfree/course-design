package edu.zhku.fr.expections;

/**
 * 没有这个key值异常
 * 
 * @author XJQ
 * @since 2013-3-27
 */
public class NoSuchKeyException extends FrameException {
	private static final long serialVersionUID = 1L;

	public NoSuchKeyException() {
		super("Sorry, you have not such key int the class edu.zhku.frame.config.Key. Program will exit!");
	}

	public NoSuchKeyException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSuchKeyException(String message) {
		super(message);
	}

	public NoSuchKeyException(Throwable cause) {
		super(cause);
	}

}
