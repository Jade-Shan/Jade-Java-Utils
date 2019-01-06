package jadeutils.net.email;

import java.io.IOException;

import javax.mail.Folder;

public interface MailFolderHandler {

	/**
	 * 处理邮件的目录
	 * 
	 * @param folder
	 *            目录
	 * @param bodyHandler
	 *            处理目录下每封邮件主体的逻辑的实现
	 * @param athmHandler
	 *            处理目录下每封邮件附件逻辑的实现
	 * @throws IOException 
	 *         something err
	 */
	public void handleMailFolder(Folder folder, MailBodyHandler bodyHandler, MailAttachmentHandler athmHandler)
			throws IOException;

}
