package edu.zhku.fr.expections;

@SuppressWarnings("serial")
public class NoServiceException extends FrameException {

    public NoServiceException() {
        super("No such service");
    }

    public NoServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoServiceException(String message) {
        super(message);
    }

    public NoServiceException(Throwable cause) {
        super(cause);
    }

}
