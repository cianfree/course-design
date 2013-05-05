package edu.zhku.ajax.dispatcher;

@SuppressWarnings("serial")
public class NANException extends RuntimeException {

	public NANException() {
		super("Not a nnumber");
	}

	public NANException(String message, Throwable cause) {
		super(message, cause);
	}

	public NANException(String message) {
		super(message);
	}

	public NANException(Throwable cause) {
		super(cause);
	}
}
