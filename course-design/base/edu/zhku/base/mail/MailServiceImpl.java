package edu.zhku.base.mail;

import java.io.UnsupportedEncodingException;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.AddressException;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import edu.zhku.base.BaseKey;
import edu.zhku.base.exceptions.NoSuchMailAddressException;
import edu.zhku.fr.ConfigCenter;
import edu.zhku.fr.Key;
import edu.zhku.fr.domain.User;

/**
 * Mail service, to solve the action of mail reference
 * 
 * @author XJQ
 * @since 2013-1-15
 */
@Service
public class MailServiceImpl implements MailService {
	/**
	 * 创建一份激活邮件
	 * 
	 * @param user
	 * @param session
	 * @return
	 * @throws MessagingException
	 * @throws AddressException
	 * @throws UnsupportedEncodingException
	 */
	private Message createActivateMail(User user, Session session) throws MessagingException, AddressException, UnsupportedEncodingException {
		Multipart multipart = MailUtils.createMultipart("mixed");
		String url = "";
		String rootUrl = ConfigCenter.getConfigString(Key.ROOT_URL);
		url = rootUrl + "user/activate.html?account=" + user.getAccount() + "&code=" + DigestUtils.md5Hex(user.getEmail());

		/*
		String subject = "仲恺在线测评激活邮-" + user.getAccount();
		String content = "您好!<br/><br/>" + //
				"感谢您在仲恺在线评测系统（<a href=" + rootUrl + ">" + rootUrl + "</a>）注册用户<br/><br/>" + //
				"账户需要激活才能使用！赶紧激活成为仲恺在线评测系统的一员吧！<br/><br/>" + //
				"点击下面的链接立即激活帐户(或将网址复制到浏览器中打开)：<br/><br/>" + //
				"<a href=" + url + ">" + url + "</a>";//
		*/
		String template = ConfigCenter.getConfig(BaseKey.ACTIVE_MAIL_CONTENT).toString();
		String subject = ConfigCenter.getConfig(BaseKey.ACTIVE_MAIL_SUBJECT).toString();
		String content = fixArguments(template, new Object[]{rootUrl, rootUrl, url, url});
		
		multipart = MailUtils.createHtmlContent(multipart, new String(content.getBytes("utf-8"), "ISO-8859-1"));
		Message message = MailUtils.createMimiMail(session, multipart, subject);
		MailUtils.setReceivers(message, RecipientType.TO, new Account(user.getEmail()));
		return message;
	}
	
	/**
	 * 填充参数
	 * @param template
	 * @param objects
	 * @return
	 */
	private String fixArguments(String template, Object[] args) {
		int index = 0;
		for(Object obj : args) {
			template = template.replace("{"+ (index ++ ) +"}", obj.toString());
		}
		return template;
    }

	@Override
	public void sendActivateMail(User user) throws NoSuchMailAddressException {
		Account from = MailUtils.getRandomSender();
		Session session = MailUtils.createSession(MailUtils.getMailProperties(from));

		try {
			Message message = this.createActivateMail(user, session);
			MailUtils.sendEmail(session, message, from);
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new NoSuchMailAddressException();
		}
	}

}
