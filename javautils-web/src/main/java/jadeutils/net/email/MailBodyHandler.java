package jadeutils.net.email;

import java.io.IOException;

import javax.mail.Message;

public interface MailBodyHandler {

	/**
	 * 处理邮件的主体
	 * 
	 * @param msg
	 *            邮件的主体
	 * @throws IOException
	 */
	public void handleMailBody(Message msg) throws IOException;
}
