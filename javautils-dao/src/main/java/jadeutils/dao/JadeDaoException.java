/*
 * 
 */
package jadeutils.dao;

import jadeutils.text.MessageMap;

/*
 * @author morgan
 * 
 */
public class JadeDaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/* 消息key值 */
	private String msgKey = "";
	/* 导致异常的描述文 */
	private String desc = "";
	/* 异常对象 */
	private Exception source = null;
	/* 读取properities文件对象 */
	private static MessageMap comMessage = new MessageMap("msgJadeDao");
	/* 换行符 */
	private static final String CRLF = System.getProperty("line.separator");

	/*
	 * <p>
	 * 异常类构造方法
	 * </p>
	 */
	protected JadeDaoException() {
		super();
	}

	/*
	 * <p>
	 * 异常类构造方法。
	 * </p>
	 * 
	 * @param e
	 *            异常对象
	 */
	public JadeDaoException(Exception e) {
		super(e);
	}

	/*
	 * <p>
	 * 异常类构造方法
	 * </p>
	 * 
	 * @param msgKey
	 *            消息key值
	 */
	public JadeDaoException(String msgKey) {
		super(comMessage.getMessage(msgKey));
		this.desc = comMessage.getMessage(msgKey);
		this.msgKey = msgKey;
	}

	/*
	 * <p>
	 * 异常类构造方法。
	 * </p>
	 * 
	 * @param msgKey
	 *            消息key值
	 * @param params
	 *            消息参数key值
	 */
	public JadeDaoException(String msgKey, String[] params) {
		super(comMessage.getMessage(msgKey, params));
		this.desc = comMessage.getMessage(msgKey, params);
		this.msgKey = msgKey;
	}

	/*
	 * <p>
	 * 异常类构造方法。
	 * </p>
	 * 
	 * @param msgKey
	 *            消息key值
	 * @param e
	 *            异常对象
	 * @since [pro_000]searchUI PKG Ver 1.0
	 */
	public JadeDaoException(String msgKey, Exception e) {
		super(comMessage.getMessage(msgKey), e);
		this.desc = comMessage.getMessage(msgKey);
		this.msgKey = msgKey;
		this.source = e;
	}

	/*
	 * <p>
	 * 异常类构造方法。
	 * </p>
	 * 
	 * @param msgKey
	 *            消息key值
	 * @param desc
	 *            导致异常的sql文
	 * @param e
	 *            异常对象
	 * @since [pro_000]searchUI PKG Ver 1.0
	 */
	public JadeDaoException(String msgKey, String desc, Exception e) {
		super(comMessage.getMessage(msgKey) + CRLF + "Desc: " + desc, e);
		this.msgKey = msgKey;
		this.desc = desc;
		this.source = e;
	}

	/*
	 * <p>
	 * 异常类构造方法。
	 * </p>
	 * 
	 * @param msgKey
	 *            消息key值
	 * @param params
	 *            消息参数key值
	 * @param e
	 *            异常对象
	 * @since [pro_000]searchUI PKG Ver 1.0
	 */
	public JadeDaoException(String msgKey, String[] params, Exception e) {
		super(comMessage.getMessage(msgKey, params), e);
		this.desc = comMessage.getMessage(msgKey, params);
		this.msgKey = msgKey;
		this.source = e;
	}

	/*
	 * <p>
	 * 异常类构造方法。
	 * </p>
	 * 
	 * @param msgKey
	 *            消息key值
	 * @param params
	 *            消息参数key值
	 * @param desc
	 *            导致异常的sql文
	 * @param e
	 *            异常对象
	 * @since [pro_000]searchUI PKG Ver 1.0
	 */
	public JadeDaoException(String msgKey, String[] params, String desc,
			Exception e) {
		super(comMessage.getMessage(msgKey, params) + CRLF + "Desc: " + desc, e);
		this.msgKey = msgKey;
		this.desc = desc;
		this.source = e;
	}

	public String getMsgKey() {
		return msgKey;
	}

	public String getDesc() {
		return desc;
	}

	public Exception getSource() {
		return source;
	}

}
