package edu.zhku.fr.expections;

@SuppressWarnings("serial")
public class WritingXmlException extends FrameException {

    public WritingXmlException() {
        super("Writing xml file error");
    }

    public WritingXmlException(String message, Throwable cause) {
        super(message, cause);
    }

    public WritingXmlException(String message) {
        super(message);
    }

    public WritingXmlException(Throwable cause) {
        super(cause);
    }

}
