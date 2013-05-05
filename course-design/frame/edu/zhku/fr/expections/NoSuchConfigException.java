package edu.zhku.fr.expections;

@SuppressWarnings("serial")
public class NoSuchConfigException extends FrameException {

    public NoSuchConfigException() {
        super("No such config file");
    }

    public NoSuchConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchConfigException(String message) {
        super(message);
    }

    public NoSuchConfigException(Throwable cause) {
        super(cause);
    }

}
