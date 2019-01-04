package jadeutils.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import jadeutils.base.FileOperater;
import jadeutils.net.email.MailAttachmentHandler;
import jadeutils.net.email.MailBodyHandler;
import jadeutils.net.email.MailFolderHandler;
import jadeutils.net.email.MailUtil;

public class MailUtilTest {

	/*
	 * 
	 * 网易邮箱开通了IMAP后，再心蓝中使用IMAP收件无法正常， <br/> 提示“NO Select Unsafe Login. Please
	 * contact kefu@188.com for help”。 <br/>
	 * 第三方客户端收信他们就会认为是不安全的，实则是为了推广他们自己的邮件客户端，实属霸王条款。 <br/> <br/>
	 * 网民的力量是强大的，据说从2014.12.20开始，网易提供了偷偷提供了一个入口设置解决这个问题，链接地址： <br/>
	 * http://config.mail.163.com/settings/imap/index.jsp?uid=xxxxxx@163.com <br/>
	 * 
	 * 首先需要先登录网页邮箱，如果是126则自己替换链接中的163。按页面提示，通过手机短信验证之后，出现如下提示: <br/> <br/>
	 * 您可以继续使用当前客户端收发邮件了，请特别注意个人的电子信息安全哦。感谢您对网易邮箱的支持！就可以正常使用第三方邮件客户端的IMAP功能来收件了。
	 * <br/>
	 * 
	 */
	// private static final String smtpServer = "smtp.163.com";
	// private static final String smtpPort = "25";
	// private static final String smtpSslPort = "994";// 994 or 456

	private static final String email = "test@test.com";
	private static final String password = "qwer1234";

	private static final Properties MAIL_SERV_CFG_163_RPOP3_NOSSL = new Properties();
	private static final Properties MAIL_SERV_CFG_163_IMAP_NOSSL = new Properties();
	private static final Properties MAIL_SERV_CFG_163_RPOP3_SSL = new Properties();
	private static final Properties MAIL_SERV_CFG_163_IMAP_SSL = new Properties();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// 准备连接服务器的会话信息
		MAIL_SERV_CFG_163_IMAP_NOSSL.setProperty("mail.store.protocol", "imap");
		MAIL_SERV_CFG_163_IMAP_NOSSL.setProperty("mail.imap.ssl.enable", "false");
		MAIL_SERV_CFG_163_IMAP_NOSSL.setProperty("mail.imap.host", "imap.163.com");
		MAIL_SERV_CFG_163_IMAP_NOSSL.setProperty("mail.imap.port", "143");

		// 准备连接服务器的会话信息
		MAIL_SERV_CFG_163_RPOP3_NOSSL.setProperty("mail.store.protocol", "pop3");
		MAIL_SERV_CFG_163_RPOP3_NOSSL.setProperty("mail.pop3.ssl.enable", "false");
		MAIL_SERV_CFG_163_RPOP3_NOSSL.setProperty("mail.pop3.host", "pop3.163.com");
		MAIL_SERV_CFG_163_RPOP3_NOSSL.setProperty("mail.pop3.port", "110");

		// 准备连接服务器的会话信息
		MAIL_SERV_CFG_163_IMAP_SSL.setProperty("mail.store.protocol", "imap");
		MAIL_SERV_CFG_163_IMAP_SSL.setProperty("mail.imap.ssl.enable", "true");
		MAIL_SERV_CFG_163_IMAP_SSL.setProperty("mail.imap.host", "imap.163.com");
		MAIL_SERV_CFG_163_IMAP_SSL.setProperty("mail.imap.port", "993");
		MAIL_SERV_CFG_163_IMAP_SSL.setProperty("mail.imap.auth.plain.disable", "true");

		// 准备连接服务器的会话信息
		MAIL_SERV_CFG_163_RPOP3_SSL.setProperty("mail.store.protocol", "pop3");
		MAIL_SERV_CFG_163_RPOP3_SSL.setProperty("mail.pop3.ssl.enable", "true");
		MAIL_SERV_CFG_163_RPOP3_SSL.setProperty("mail.pop3.host", "pop3.163.com");
		MAIL_SERV_CFG_163_RPOP3_SSL.setProperty("mail.pop3.port", "995");
		MAIL_SERV_CFG_163_RPOP3_SSL.setProperty("mail.pop3.auth.plain.disable", "true");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	private MailFolderHandler defaultFolderHandler = new MailFolderHandler() {

		@Override
		public void handleMailFolder(Folder folder, MailBodyHandler bodyHandler, MailAttachmentHandler athmHandler)
				throws IOException //
		{
			try {
				// 获得收件箱的邮件列表
				Message[] messages = folder.getMessages();
				// 打印不同状态的邮件数量
				System.out.println("<" + folder.getFullName() + "> 中有" + messages.length + "封邮件!");
				System.out.println("其中有" + folder.getUnreadMessageCount() + "封未读邮件!");
				System.out.println("其中有" + folder.getNewMessageCount() + "封新邮件!");
				System.out.println("其中有" + folder.getDeletedMessageCount() + "封已删除邮件!");
				if (null != messages && messages.length > 0) {
					for (Message msg : messages) {
						MailUtil.parseMessage(bodyHandler, athmHandler, msg); // 解析邮件
					}
				}
			} catch (Exception e) {
				throw new IOException(e.getMessage());
			}
		}
	};

	private MailBodyHandler defaultBodyHandler = new MailBodyHandler() {
		@Override
		public void handleMailBody(Message msg) throws IOException {
			try {
				System.out.println("------------------解析第" + msg.getMessageNumber() + "封邮件-------------------- ");
				System.out.println("主题: " + MailUtil.getSubject(msg));
				System.out.println("发件人: " + MailUtil.getFrom(msg));
				System.out.println("收件人：" + MailUtil.getReceiveAddress(msg, null));
				System.out.println("发送时间：" + MailUtil.getSentDate(msg, null));
				System.out.println("是否已读：" + MailUtil.isSeen(msg));
				System.out.println("邮件优先级：" + MailUtil.getPriority(msg));
				System.out.println("是否需要回执：" + MailUtil.isReplySign(msg));
				System.out.println("是否包含附件：" + MailUtil.hasAttachment(msg));
				System.out.println("邮件大小：" + msg.getSize() * 1024 + "kb");
				/*
				 * 处理文本
				 */
				Map<String, StringBuffer> content = new HashMap<>();
				MailUtil.parseMailTextContent(msg, content);
				System.out.println("邮件正文预览：" + content.get(MailUtil.MAIL_TYPE_TEXT_PAIN).toString().substring(0, 200));
				System.out
						.println("邮件正文HTML：" + content.get(MailUtil.MAIL_TYPE_TEXT_HTML).toString().substring(0, 200));
				/*
				 * 设置已读标志 <br/> msg.setFlag(Flag.SEEN, true);
				 */
				/*
				 * 删除邮件 <br/> msg.setFlag(Flag.DELETED, true);
				 */
				System.out.println("------------------第" + msg.getMessageNumber() + "封邮件解析结束，并将其标记为已读 ---------- \n");
			} catch (Exception e) {
				throw new IOException(e.getMessage());
			}
		}
	};

	private MailAttachmentHandler defaultAttachmentHandler = new MailAttachmentHandler() {
		@Override
		public void handleMailAttachment(Message msg, BodyPart bodyPart) throws IOException {
			try {
				InputStream is = bodyPart.getInputStream();
				String subject = URLEncoder.encode(MailUtil.getSubject(msg), "UTF-8");
				String filename = URLEncoder.encode(MailUtil.decodeText(//
						bodyPart.getFileName()), "UTF-8");
				FileOperater.writeFile("/mailtmp/", subject + "_" + filename, is);
			} catch (Exception e) {
				throw new IOException(e.getMessage());
			}
		}
	};

	@Test
	public void testLoopAllFolderIMAP() throws Exception {
		MailUtil.receiveMail(MAIL_SERV_CFG_163_IMAP_NOSSL, email, password, true, defaultFolderHandler,
				defaultBodyHandler, defaultAttachmentHandler);
	}

	@Test
	public void testLoopAllFolderPOP3() throws Exception {
		MailUtil.receiveMail(MAIL_SERV_CFG_163_RPOP3_NOSSL, email, password, true, defaultFolderHandler,
				defaultBodyHandler, defaultAttachmentHandler);
	}

	@Test
	public void testMoveMail() throws Exception {
		// String fromStr = "lab-booking-unread";
		// String toStr = "lab-booking-success";
		String fromStr = "INBOX";
		String toStr = "草稿箱";
		// 创建Session实例对象
		Session session = Session.getInstance(MAIL_SERV_CFG_163_IMAP_NOSSL);
		// 创建IMAP协议的Store对象
		Store store = session.getStore("imap");
		// 连接邮件服务器
		store.connect(email, password);
		// 获得收件箱
		Folder from = store.getFolder(fromStr);
		from.open(Folder.READ_WRITE);
		Folder to = store.getFolder(toStr);
		to.open(Folder.READ_WRITE);

		Message[] mails = from.getMessages();
		if (null != mails && mails.length > 0) {
			String subject = MailUtil.getSubject(mails[0]);
			if (subject.indexOf("Move to") > -1) {
				Message[] mr = { mails[0] };
				from.copyMessages(mr, to);
				mails[0].setFlag(Flags.Flag.DELETED, true);// 设置已删除状态为true
			}
		}
		from.close(true); // ture表示对标记了删除记录的邮件实施删除操作
		to.close(true);
		store.close();
	}
}
