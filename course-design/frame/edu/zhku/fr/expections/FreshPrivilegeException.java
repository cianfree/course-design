package edu.zhku.fr.expections;

@SuppressWarnings("serial")
public class FreshPrivilegeException extends FrameException {

    public FreshPrivilegeException() {
        super("Freshing system privileges error.");
    }

    public FreshPrivilegeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FreshPrivilegeException(String message) {
        super(message);
    }

    public FreshPrivilegeException(Throwable cause) {
        super(cause);
    }

}
