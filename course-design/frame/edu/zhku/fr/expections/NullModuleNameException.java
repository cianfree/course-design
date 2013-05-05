package edu.zhku.fr.expections;

/**
 * 配置文件中没有设置模块名称异常，必须捕获
 * 
 * @author XJQ
 * @since 2013-3-23
 */
public class NullModuleNameException extends FrameException {
	private static final long serialVersionUID = 1L;

	public NullModuleNameException() {
		super();
	}

	public NullModuleNameException(String message) {
		super(message);
	}

}
