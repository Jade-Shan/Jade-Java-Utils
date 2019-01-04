package jadeutils.net.email;

import java.io.IOException;

import javax.mail.BodyPart;
import javax.mail.Message;

public interface MailAttachmentHandler {

	/**
	 * 处理邮件的附件
	 * 
	 * @param msg
	 *            邮件的主体
	 * @param bodyPart
	 *            遍历邮件主体中的其中一个部分
	 * @throws IOException
	 */
	public void handleMailAttachment(Message msg, BodyPart bodyPart) throws IOException;
}
