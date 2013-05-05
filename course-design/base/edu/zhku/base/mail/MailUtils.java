package edu.zhku.base.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import edu.zhku.base.BaseKey;
import edu.zhku.fr.ConfigCenter;


public class MailUtils {
	private static final Map<Account, Properties> pros = new HashMap<Account, Properties>();
	private static final List<Account> accounts = new ArrayList<Account>();
	
	static {
		// 执行初始化操作
		init();
	}

	/**
	 * 初始化
	 */
	@SuppressWarnings("unchecked")
    private static void init() {
		accounts.addAll((Set<Account>) ConfigCenter.getConfig(BaseKey.SERVER_MAIL_ACCOUNT));

		// 初始化邮件发送的基本属性
		Properties pro = null;
		for (Account account : accounts) {
			pro = new Properties();
			pro.setProperty("type", account.getServerConfig().getType());
			pro.setProperty("mail.transport.protocol", account.getServerConfig().getTransportProtocol());
			pro.setProperty("mail.host", account.getServerConfig().getServerHost());
			pro.setProperty("mail.debug", account.getServerConfig().getDebug());
			pro.setProperty("mail.smtp.auth", account.getServerConfig().getAuth());
			
			pros.put(account, pro);
        }
	}
	
	/**
	 * 获取一个随机的发送账户
	 * 
	 * @return
	 */
	public static Account getRandomSender() {
		return accounts.get(getRandom(0, accounts.size()-1));
	}

	/**
	 * 获取指定类型的邮箱配置属性
	 * 
	 * @param from
	 * @return
	 */
	public static Properties getMailProperties(Account from) {
		return pros.get(from);
	}

	/**
	 * 产生min和max之间的随机数，其中包含min 和 max
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandom(int min, int max) {
		return new Random().nextInt(max - min + 1) + min;
	}

	/**
	 * 保持单例
	 */
	private MailUtils() {
	}

	public static void sendEmail(Session session, Message message, Account from, Account[] tos) throws Exception {
		Transport transport = null;
		try {
			transport = session.getTransport();
			// 通过发送用户连接到服务器
			transport.connect(from.getAddress(), from.getPassword());
			// 发送邮件
			if(tos != null && tos.length > 0) {
				addReceiver(message, RecipientType.TO, tos);
			}
			transport.sendMessage(message, message.getAllRecipients());
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				transport.close();
			} catch (Exception e) {
			}
		}
	}
	
	public static boolean sendEmail(Session session, Message message, Account... to) throws NoSuchProviderException, MessagingException {
		Transport transport = session.getTransport();
		InternetAddress[] addresses = getAllReceivers(message);
		/**
		 * if the receiver is null, there is not necessary to send any mail
		 */
		if (addresses == null) {
			return false;
		}
		/**
		 * if the sources, there is impossible to send email
		 */
		if (to == null) {
			return false;
		}
		/**
		 * if not set any mail from, set it
		 */
		if (message.getFrom() == null) {
			if (to.length > 1) {
				setFrom(message, to);
			} else {
				setFrom(message, to[0]);
			}
		}
		/**
		 * sending the email, maybe has many from and many receivers
		 */
		for (int i = 0; i < to.length; ++i) {
			transport.connect(to[i].getAddress(), to[i].getPassword());
			transport.sendMessage(message, addresses);
		}
		transport.close();
		return true;
	}
	
	/**
	 * 给指定的邮件添加发送人
	 * 
	 * @param message
	 *            要发送的邮件消息
	 * @param accounts
	 *            发送人帐号信息，应该包含了账户类型，账户名和账户密码
	 * @return 返回消息主体
	 * @throws MessagingException
	 */
	public static Message addFrom(Message message, Account... accounts) throws MessagingException {
		return addFrom(message, convertToInternetAddresses(accounts));
	}

	public static Message addFrom(Message message, InternetAddress... addresses) throws MessagingException {
		message.addFrom(addresses);
		return message;
	}

	public static Message addReceiver(Message message, RecipientType rt, Account... accounts) throws MessagingException {
		return addReceivers(message, rt, convertToInternetAddresses(accounts));
	}

	public static Message addReceivers(Message message, RecipientType rt, InternetAddress... addresses) throws MessagingException {
		message.addRecipients(rt, addresses);
		return message;
	}

	private static InternetAddress[] convertToInternetAddresses(Account... accounts) throws AddressException {
		int len = accounts.length;
		InternetAddress[] addresses = new InternetAddress[len];
		for (int i = 0; i < len; ++i) {
			addresses[i] = new InternetAddress(accounts[i].getAddress());
		}
		return addresses;
	}

	/**
	 * 创建一个包含附件内容的邮件
	 * 
	 * @param multipart
	 * @param file
	 *            要发送的附件
	 * @param fileName
	 *            要发送的附件名称
	 * @return
	 * @throws MessagingException
	 */
	public static Multipart createAttachmentContent(Multipart multipart, File file, String fileName) throws MessagingException {
		MimeBodyPart fileBody = new MimeBodyPart();
		fileBody.setDataHandler(new DataHandler(new FileDataSource(file)));
		fileBody.setFileName(fileName);
		multipart.addBodyPart(fileBody);
		return multipart;
	}

	/**
	 * 创建一个包含附件内容的邮件
	 * 
	 * @param multipart
	 * @param filePath
	 *            文件路径
	 * @param fileName
	 *            文件名称
	 * @return
	 * @throws MessagingException
	 */
	public static Multipart createAttachmentContent(Multipart multipart, String filePath, String fileName) throws MessagingException {
		File file = new File(filePath);
		return createAttachmentContent(multipart, file, fileName);
	}

	/**
	 * 创建一个保护焊HTML内容的邮件
	 * 
	 * @param multipart
	 * @param content
	 *            内容，其中包含HTML标签之类的内容
	 * @return
	 * @throws MessagingException
	 */
	public static Multipart createHtmlContent(Multipart multipart, String content) throws MessagingException {
		MimeBodyPart html = new MimeBodyPart();
		html.setContent(content, "text/html");
		multipart.addBodyPart(html);
		return multipart;
	}

	/**
	 * 创建一个包含图片的邮件
	 * 
	 * @param multipart
	 * @param image
	 * @return
	 * @throws MessagingException
	 */
	public static Multipart createImageContent(Multipart multipart, File image) throws MessagingException {
		MimeBodyPart html = new MimeBodyPart();
		String uuid = UUID.randomUUID().toString();
		html.setContent("<img src='cid:" + uuid + "'></a>", "text/html");
		MimeBodyPart imageBody = new MimeBodyPart();
		imageBody.setDataHandler(new DataHandler(new FileDataSource(image)));
		imageBody.setContentID(uuid);
		multipart.addBodyPart(html);
		multipart.addBodyPart(imageBody);
		return multipart;
	}

	/**
	 * 根据给定的图片的路径创建一个图片邮件，这个图片不是作为附件，而是作为邮件的内容
	 * 
	 * @param multipart
	 * @param imagePath
	 * @return
	 * @throws MessagingException
	 */
	public static Multipart createImageContent(Multipart multipart, String imagePath) throws MessagingException {
		File imageFile = new File(imagePath);
		return createImageContent(multipart, imageFile);
	}

	/**
	 * 创建一个混合模式的邮件
	 * 
	 * @param session
	 *            指定会话
	 * @param multipart
	 * @param subject
	 *            指定邮件的主题
	 * @return
	 * @throws MessagingException
	 */
	public static Message createMimiMail(Session session, Multipart multipart, String subject) throws MessagingException {
		// create message
		Message message = new MimeMessage(session);
		// set subject
		message.setSubject(subject);
		// set content which had provided
		message.setContent(multipart);

		return message;
	}
	public static Message createMimiMail(Session session, String subject) throws MessagingException {
		// create message
		Message message = new MimeMessage(session);
		// set subject
		message.setSubject(subject);
		
		return message;
	}

	public static Multipart createMultipart(String type) {
		return new MimeMultipart(type);
	}

	/**
	 * 根据给定的邮件属性配置来创建一个会话
	 * 
	 * @param properties
	 * @return
	 */
	public static Session createSession(Properties properties) {
		return Session.getDefaultInstance(properties);
	}

	/**
	 * 创建一个只包含文本的邮件
	 * 
	 * @param session
	 * @param subject
	 * @return
	 * @throws MessagingException
	 */
	public static Message createTextMail(Session session, String subject) throws MessagingException {
		// create message
		Message message = new MimeMessage(session);
		// set subject
		message.setSubject(subject);
		// set mail to
		return message;
	}

	public static InternetAddress[] getAllReceivers(Message message) throws MessagingException {
		return (InternetAddress[]) message.getAllRecipients();
	}

	public static Message setFrom(Message message, Account... accounts) throws MessagingException {
		return addFrom(message, convertToInternetAddresses(accounts));
	}

	public static Message setFrom(Message message, InternetAddress... addresses) throws MessagingException {
		message.setFrom(null);
		return addFrom(message, addresses);
	}

	public static Message setReceivers(Message message, RecipientType rt, Account... receivers) throws MessagingException {
		return setReceivers(message, rt, convertToInternetAddresses(receivers));
	}

	public static Message setReceivers(Message message, RecipientType rt, InternetAddress... addresses) throws MessagingException {
		message.setRecipients(rt, addresses);
		return message;
	}

	public static Message setText(Message message, String text) throws MessagingException {
		message.setText(text);
		return message;
	}
}
