package edu.zhku.fr.expections;

/**
 * 配置重复异常
 *
 * @author XJQ
 * @since 2013-3-23
 */
public class DuplicateModuleException extends FrameException {
	private static final long serialVersionUID = 1L;

	public DuplicateModuleException() {
		super();
	}
	
	public DuplicateModuleException(String message) {
		super(message);
	}
}
