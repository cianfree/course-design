package edu.zhku.base.mail;

import edu.zhku.base.exceptions.NoSuchMailAddressException;
import edu.zhku.fr.domain.User;

/**
 * 邮件组件
 *
 * @author XJQ
 * @since 2013-1-11
 */
public interface MailService {
	/**
	 * 注册的时候发送一封激活邮件
	 * @param user
	 * @throws NoSuchMailAddressException	没有这样的邮件地址异常
	 */
	void sendActivateMail(User user) throws NoSuchMailAddressException;
}
