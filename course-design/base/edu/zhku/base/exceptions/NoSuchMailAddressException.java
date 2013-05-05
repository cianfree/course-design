package edu.zhku.base.exceptions;


/**
 * 邮件地址不存在异常，必须捕获
 *
 * @author XJQ
 * @since 2013-1-11
 */
@SuppressWarnings("serial")
public class NoSuchMailAddressException extends Exception {

	public NoSuchMailAddressException() {
		super("邮件地址不存在！");
	}
}
