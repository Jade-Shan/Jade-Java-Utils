package jadeutils.net.email;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import jadeutils.text.HtmlUtil;

/**
 * 使用IMAP协议接收邮件
 *
 * 
 * 
 * POP3和IMAP协议的区别:
 * 
 * POP3协议允许电子邮件客户端下载服务器上的邮件,但是在客户端的操作(如移动邮件、标记已读等),不会反馈到服务器上,
 * 
 * 比如通过客户端收取了邮箱中的3封邮件并移动到其它文件夹,邮箱服务器上的这些邮件是没有同时被移动的。
 *
 * 
 * 
 * IMAP协议提供webmail与电子邮件客户端之间的双向通信,客户端的操作都会同步反应到服务器上,对邮件进行的操作,服务
 * 上的邮件也会做相应的动作。比如在客户端收取了邮箱中的3封邮件,并将其中一封标记为已读,将另外两封标记为删除,这些操作会 即时反馈到服务器上。
 *
 * 
 * 
 * 两种协议相比,IMAP 整体上为用户带来更为便捷和可靠的体验。POP3更易丢失邮件或多次下载相同的邮件,但IMAP通过邮件客户端
 * 与webmail之间的双向同步功能很好地避免了这些问题。
 * 
 */
public class MailUtil {

	public static final String MAIL_TYPE_TEXT_PAIN = "text/*";
	public static final String MAIL_TYPE_TEXT_HTML = "text/html";
	public static final String MAIL_TYPE_MSG_RFC822 = "message/rfc822";

	/**
	 * send email
	 * 
	 * @param emailProps
	 *            email props
	 * @param email
	 *            email
	 * @param password
	 *            password
	 * @param toArr
	 *            toArr
	 * @param ccArr
	 *            ccArr
	 * @param bccArr
	 *            bccArr
	 * @param subject
	 *            subject
	 * @param text
	 *            text
	 * @throws Exception
	 *             exception
	 */
	public static void sendTextMail(Properties emailProps, String email, String password, String[] toArr,
			String[] ccArr, String[] bccArr, String subject, String text) throws Exception //
	{
		Session smtpSession = Session.getInstance(emailProps, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(email, password);
			}
		});
		try {
			Message msg = null;
			msg = new MimeMessage(smtpSession);
			msg.setFrom(new InternetAddress(email));
			for (String to : toArr) {
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			}
			for (String cc : ccArr) {
				msg.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
			}
			for (String bcc : bccArr) {
				msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc));
			}
			msg.setSubject(subject);
			msg.setText(text);
			Transport.send(msg);
		} catch (MessagingException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * send email
	 * 
	 * @param emailProps
	 *            email props
	 * @param email
	 *            email
	 * @param password
	 *            password
	 * @param toArr
	 *            toArr
	 * @param ccArr
	 *            ccArr
	 * @param bccArr
	 *            bccArr
	 * @param subject
	 *            subject
	 * @param text
	 *            text
	 * @param attachments
	 *            attachments
	 * @throws Exception
	 *             exception
	 */
	public static void sendTextMail(Properties emailProps, String email, String password, String[] toArr,
			String[] ccArr, String[] bccArr, String subject, String text, Map<String, DataSource> attachments)
			throws Exception //
	{
		Session smtpSession = Session.getInstance(emailProps, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(email, password);
			}
		});
		try {
			Message msg = null;
			msg = new MimeMessage(smtpSession);
			msg.setFrom(new InternetAddress(email));
			for (String to : toArr) {
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			}
			for (String cc : ccArr) {
				msg.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
			}
			for (String bcc : bccArr) {
				msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc));
			}
			msg.setSubject(subject);

			MimeMultipart multipart = new MimeMultipart();
			MimeBodyPart part0 = new MimeBodyPart();
			part0.setContent(text, "text/plain;charset=UTF-8");
			multipart.addBodyPart(part0);
			if (null != attachments) {
				for (Entry<String, DataSource> ath : attachments.entrySet()) {
					MimeBodyPart pat = new MimeBodyPart();
					pat.setDataHandler(new DataHandler(ath.getValue()));
					pat.setFileName(ath.getKey());
					multipart.addBodyPart(pat);
				}
				multipart.setSubType("mixed");
			}
			msg.setContent(multipart);
			Transport.send(msg);
		} catch (MessagingException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * send email
	 * 
	 * @param emailProps
	 *            email props
	 * @param email
	 *            email
	 * @param password
	 *            password
	 * @param toArr
	 *            toArr
	 * @param ccArr
	 *            ccArr
	 * @param bccArr
	 *            bccArr
	 * @param subject
	 *            subject
	 * @param html
	 *            html
	 * @param related
	 *            related attachments
	 * @param attachments
	 *            attachments
	 * @throws Exception
	 *             exception
	 */
	public static void sendHtmlMail(Properties emailProps, String email, String password, String[] toArr,
			String[] ccArr, String[] bccArr, String subject, String html, Map<String, DataSource> related,
			Map<String, DataSource> attachments) throws Exception //
	{
		Session smtpSession = Session.getInstance(emailProps, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(email, password);
			}
		});
		try {
			Message msg = null;
			msg = new MimeMessage(smtpSession);
			msg.setFrom(new InternetAddress(email));
			for (String to : toArr) {
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			}
			for (String cc : ccArr) {
				msg.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
			}
			for (String bcc : bccArr) {
				msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc));
			}
			msg.setSubject(subject);

			//
			MimeMultipart pageMulti = new MimeMultipart();
			MimeBodyPart pageBody = new MimeBodyPart();
			pageBody.setContent(html, "text/html;charset=UTF-8");
			pageMulti.addBodyPart(pageBody);
			if (null != related) {
				for (Entry<String, DataSource> ath : related.entrySet()) {
					MimeBodyPart pat = new MimeBodyPart();
					pat.setDataHandler(new DataHandler(ath.getValue()));
					pat.setContentID(ath.getKey());
					pageMulti.addBodyPart(pat);
				}
				pageMulti.setSubType("related");
			}

			//
			MimeBodyPart contentBody = new MimeBodyPart();
			contentBody.setContent(pageMulti);
			MimeMultipart contentMulti = new MimeMultipart();
			contentMulti.addBodyPart(contentBody);
			if (null != attachments) {
				for (Entry<String, DataSource> ath : attachments.entrySet()) {
					MimeBodyPart pat = new MimeBodyPart();
					pat.setDataHandler(new DataHandler(ath.getValue()));
					pat.setFileName(ath.getKey());
					contentMulti.addBodyPart(pat);
				}
				contentMulti.setSubType("mixed");
			}
			
			//
			MimeBodyPart mmBody = new MimeBodyPart();
			mmBody.setContent(contentMulti);
			MimeMultipart multipart = new MimeMultipart();
			multipart.addBodyPart(mmBody);

//			MimeMultipart textMulti = new MimeMultipart();
			MimeBodyPart textBody = new MimeBodyPart();
			String text = HtmlUtil.html2text(html);
			textBody.setContent(text, "text/plain;charset=UTF-8");
			multipart.addBodyPart(textBody);
//			textMulti.addBodyPart(textBody);

			msg.setContent(multipart);
			Transport.send(msg);
		} catch (MessagingException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 
	 * @param emailProps
	 *            mail server connection props
	 * @param email
	 *            email address
	 * @param password
	 *            email passward
	 * @param isReadOnly
	 *            is read only
	 * @param folderHandler
	 *            handle every mail folder in mail account
	 * @param bodyHandler
	 *            handle mail body in every mail folder
	 * @param athmHandler
	 *            handle mail attachment in every mail
	 * @throws Exception
	 *             something err
	 */
	public static void receiveMail(Properties emailProps, String email, String password, boolean isReadOnly,
			MailFolderHandler folderHandler, MailBodyHandler bodyHandler, MailAttachmentHandler athmHandler)
			throws Exception //
	{
		Session session = null;
		Store store = null;
		try {
			// 创建Session实例对象
			session = Session.getInstance(emailProps);
			// 创建IMAP协议的Store对象
			// store = session.getStore("pop3");
			store = session.getStore(emailProps.getProperty("mail.store.protocol"));
			// 连接邮件服务器
			store.connect(email, password);
		} catch (Exception e) {
			throw e;
			// String s = new String(e.getMessage().getBytes("iso8859-1"), errCharset);
			// System.out.println("aaaa: " + s);
		}
		// 获得收件箱
		// Folder folder = store.getFolder("INBOX");
		// Folder folder = store.getDefaultFolder();

		Folder[] folders = store.getDefaultFolder().list();
		for (Folder folder : folders) {
			if (isReadOnly) {
				folder.open(Folder.READ_ONLY);
			} else {
				folder.open(Folder.READ_WRITE);
			}
			folderHandler.handleMailFolder(folder, bodyHandler, athmHandler);
			// 关闭资源
			if (isReadOnly) {
				folder.close(false); // 不删除远程服务器上的邮件
			} else {
				folder.close(true); // example 删除邮件
			}
		}
		store.close();
	}

	/**
	 * 
	 * parse mail
	 * 
	 * @param bodyHandler
	 *            handle mail body
	 * @param athmHandler
	 *            handle mail attachement
	 * @param messages
	 *            message ( could be one or many) to parse
	 * @throws MessagingException
	 *             something err
	 * @throws IOException
	 *             something err
	 */
	public static void parseMessage(MailBodyHandler bodyHandler, MailAttachmentHandler athmHandler, Message... messages)
			throws MessagingException, IOException //
	{
		if (messages != null && messages.length > 0) { // 解析所有邮件
			for (Message msg : messages) {
				/* 处理文本主体 */
				bodyHandler.handleMailBody(msg);
				/* 处理附件 */
				boolean hasAttachment = MailUtil.hasAttachment(msg);
				// System.out.println("是否包含附件：" + hasAttachment);
				if (hasAttachment) {
					parseAttachment(msg, msg, athmHandler);
				}
			}
		}
	}

	/**
	 * get the topic of mail
	 * 
	 * @param msg
	 *            mail body
	 * @return subject of mail
	 * @throws UnsupportedEncodingException
	 *             io err
	 * @throws MessagingException
	 *             io err
	 */
	public static String getSubject(Message msg) throws UnsupportedEncodingException, MessagingException //
	{
		return MimeUtility.decodeText(msg.getSubject());
	}

	/**
	 * get sender
	 * 
	 * @param msg
	 *            mail
	 * @return sender
	 * @throws MessagingException
	 *             something err
	 * @throws UnsupportedEncodingException
	 *             something err
	 */
	public static String getSender(MimeMessage msg) throws MessagingException, UnsupportedEncodingException //
	{
		Address sender = msg.getSender();
		msg.getSender();
		if (null == sender) {
			return null;
		}
		return msg.getSender().toString();
	}

	/**
	 * get mail from
	 * 
	 * @param msg
	 *            mail
	 * @return name &lt; mail_address &gt;
	 * @throws MessagingException
	 *             something err
	 * @throws UnsupportedEncodingException
	 *             something err
	 */
	public static String getFrom(Message msg) throws MessagingException, UnsupportedEncodingException //
	{
		String from = "";
		Address[] froms = msg.getFrom();

		if (froms.length < 1)
			throw new MessagingException("Err: From message is missing in the email header");

		InternetAddress address = (InternetAddress) froms[0];
		String person = address.getPersonal();
		if (person != null) {
			person = MimeUtility.decodeText(person) + " ";
		} else {
			person = "";
		}
		from = person + "<" + address.getAddress() + ">";

		return from;
	}

	/**
	 * RecipientType.
	 * 
	 * @param msg
	 *            mail
	 * @param type
	 *            recipient type / Message.RecipientType.TO /
	 *            Message.RecipientType.CC / Message.RecipientType.BCC. Return all
	 *            by default.
	 * @return name1 &lt; mail_address1 &gt; , name2 &lt; mail_address2 &gt;, ...
	 * @throws MessagingException
	 *             something err
	 */
	public static String getReceiveAddress(Message msg, Message.RecipientType type) throws MessagingException //
	{
		StringBuffer receiveAddress = new StringBuffer();
		Address[] addresss = null;
		if (type == null) {
			addresss = msg.getAllRecipients();
		} else {
			addresss = msg.getRecipients(type);
		}

		if (addresss == null || addresss.length < 1)
			throw new MessagingException("没有收件人!");
		for (Address address : addresss) {
			InternetAddress internetAddress = (InternetAddress) address;
			receiveAddress.append(internetAddress.toUnicodeString()).append(",");
		}

		receiveAddress.deleteCharAt(receiveAddress.length() - 1); // 删除最后一个逗号

		return receiveAddress.toString();
	}

	/**
	 * get send date
	 * 
	 * @param msg
	 *            mail
	 * @param pattern
	 *            String for java date format
	 * @return yyyy-MM-dd_E_HH:mm:ss
	 * @throws MessagingException
	 *             something err
	 */
	public static String getSentDate(Message msg, String pattern) throws MessagingException //
	{
		Date receivedDate = msg.getSentDate();
		if (receivedDate == null)
			return "";

		if (pattern == null || "".equals(pattern))
			// pattern = "yyyy年MM月dd日 E HH:mm:ss ";
			pattern = "yyyy-MM-dd_HH:mm:ss";

		return new SimpleDateFormat(pattern).format(receivedDate);
	}

	/**
	 * mail has attachment or not
	 * 
	 * @param part
	 *            mail
	 * @return true or false
	 * @throws MessagingException
	 *             something err
	 * @throws IOException
	 *             something err
	 */
	public static boolean hasAttachment(Part part) throws MessagingException, IOException {
		boolean flag = false;
		if (part.isMimeType("multipart/*")) {
			MimeMultipart multipart = (MimeMultipart) part.getContent();
			int partCount = multipart.getCount();
			for (int i = 0; i < partCount; i++) {
				BodyPart bodyPart = multipart.getBodyPart(i);
				String disp = bodyPart.getDisposition();
				if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {
					flag = true;
				} else if (bodyPart.isMimeType("multipart/*")) {
					flag = hasAttachment(bodyPart);
				} else {
					String contentType = bodyPart.getContentType();
					if (contentType.toLowerCase().indexOf("application") > -1
							|| contentType.toLowerCase().indexOf("name") > -1) //
					{
						flag = true;
					}
				}

				if (flag)
					break;
			}
		} else if (part.isMimeType(MAIL_TYPE_MSG_RFC822)) {
			flag = hasAttachment((Part) part.getContent());
		}
		return flag;
	}

	/**
	 * mail is seen or not
	 * 
	 * @param msg
	 *            mail
	 * @return true or false
	 * @throws MessagingException
	 *             something err
	 */
	public static boolean isSeen(Message msg) throws MessagingException {
		return msg.getFlags().contains(Flags.Flag.SEEN);
	}

	/**
	 * is mail need reply
	 * 
	 * @param msg
	 *            mail
	 * @return true or false
	 * @throws MessagingException
	 *             something err
	 */
	public static boolean isReplySign(Message msg) throws MessagingException {
		boolean replySign = false;
		String[] headers = msg.getHeader("Disposition-Notification-To");
		if (headers != null)
			replySign = true;
		return replySign;
	}

	/**
	 * priority
	 * 
	 * @param msg
	 *            mail
	 * @return 1(High):紧急 3:普通(Normal) 5:低(Low)
	 * @throws MessagingException
	 *             something err
	 */
	public static String getPriority(Message msg) throws MessagingException {
		String priority = "普通";
		String[] headers = msg.getHeader("X-Priority");
		if (headers != null) {
			String headerPriority = headers[0];
			if (headerPriority.indexOf("1") != -1 || headerPriority.indexOf("High") != -1)
				priority = "High";
			else if (headerPriority.indexOf("5") != -1 || headerPriority.indexOf("Low") != -1)
				priority = "Low";
			else
				priority = "Normal";
		}
		return priority;
	}

	/**
	 * get mail content by text
	 * 
	 * @param part
	 *            mail body part
	 * @param content
	 *            content of mail body
	 * @throws MessagingException
	 *             something err
	 * @throws IOException
	 *             something err
	 */
	public static void parseMailTextContent(Part part, Map<String, StringBuffer> content)
			throws MessagingException, IOException //
	{
		// 如果是文本类型的附件，通过getContent方法可以取到文本内容，但这不是我们需要的结果，所以在这里要做判断
		boolean isContainTextAttach = part.getContentType().indexOf("name") > 0;
		if (part.isMimeType(MAIL_TYPE_TEXT_HTML) && !isContainTextAttach) {
			StringBuffer sb = content.get(MAIL_TYPE_TEXT_HTML);
			if (null == sb) {

				sb = new StringBuffer(500);
				content.put(MAIL_TYPE_TEXT_HTML, sb);
			}
			sb.append(MimeUtility.decodeText(part.getContent().toString()));
		} else if (part.isMimeType(MAIL_TYPE_TEXT_PAIN) && !isContainTextAttach) {
			StringBuffer sb = content.get(MAIL_TYPE_TEXT_PAIN);
			if (null == sb) {
				sb = new StringBuffer(500);
				content.put(MAIL_TYPE_TEXT_PAIN, sb);
			}
			sb.append(part.getContent().toString());
		} else if (part.isMimeType(MAIL_TYPE_MSG_RFC822)) {
			parseMailTextContent((Part) part.getContent(), content);
		} else if (part.isMimeType("multipart/*")) {
			Multipart multipart = (Multipart) part.getContent();
			int partCount = multipart.getCount();
			for (int i = 0; i < partCount; i++) {
				BodyPart bodyPart = multipart.getBodyPart(i);
				parseMailTextContent(bodyPart, content);
			}
		}
	}

	/**
	 * 
	 * @param msg
	 *            mail
	 * @param part
	 *            part of email
	 * @param athmHandler
	 *            attachment handler
	 * @throws UnsupportedEncodingException
	 *             something err
	 * @throws MessagingException
	 *             something err
	 * @throws FileNotFoundException
	 *             something err
	 * @throws IOException
	 *             something err
	 */
	public static void parseAttachment(Message msg, Part part, MailAttachmentHandler athmHandler)
			throws UnsupportedEncodingException, MessagingException, FileNotFoundException, IOException //
	{
		if (part.isMimeType("multipart/*")) {
			Multipart multipart = (Multipart) part.getContent(); // 复杂体邮件
			// 复杂体邮件包含多个邮件体
			int partCount = multipart.getCount();
			for (int i = 0; i < partCount; i++) {
				// 获得复杂体邮件中其中一个邮件体
				BodyPart bodyPart = multipart.getBodyPart(i);
				// 某一个邮件体也有可能是由多个邮件体组成的复杂体
				String disp = bodyPart.getDisposition();
				if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) //
				{
					athmHandler.handleMailAttachment(msg, bodyPart);
				} else if (bodyPart.isMimeType("multipart/*")) {
					parseAttachment(msg, bodyPart, athmHandler);
				} else {
					String contentType = bodyPart.getContentType();
					if (contentType.toLowerCase().indexOf("name") > -1
							|| contentType.toLowerCase().indexOf("application") > -1) //
					{
						athmHandler.handleMailAttachment(msg, bodyPart);
					}
				}
			}
		} else if (part.isMimeType(MAIL_TYPE_MSG_RFC822)) {
			parseAttachment(msg, (Part) part.getContent(), athmHandler);
		}
	}

	/**
	 * decode test
	 * 
	 * @param encodedText
	 *            MimeUtility.encodeText(String text)
	 * @return text
	 * @throws UnsupportedEncodingException
	 *             something err
	 */
	public static String decodeText(String encodedText) throws UnsupportedEncodingException {
		if (encodedText == null || "".equals(encodedText)) {
			return "";
		} else {
			return MimeUtility.decodeText(encodedText);
		}
	}

}
