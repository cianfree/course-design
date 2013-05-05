package edu.zhku.fr.expections;

@SuppressWarnings("serial")
public class InstancingException extends FrameException {

    public InstancingException() {
        super("Can't instance class");
    }

    public InstancingException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstancingException(String message) {
        super(message);
    }

    public InstancingException(Throwable cause) {
        super(cause);
    }

}
