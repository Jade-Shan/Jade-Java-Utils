package jadeutils.text;

/**
 * 
 */

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * @author morgan
 * 
 */
public class MessageMap {
	ResourceBundle resourceBundle = null;

	public MessageMap(String fileName) {
		this.resourceBundle = ResourceBundle.getBundle(fileName);
	}

	/**
	 * <p>
	 * load from prop file
	 * </p>
	 * 
	 * @param msgKey
	 * @return
	 */
	public String getMessage(String msgKey) {
		String strErrorMsg = "Err load msg properties";
		try {
			strErrorMsg = this.resourceBundle.getString(msgKey);
		} catch (Exception e) {
			// do nothing
		}
		return strErrorMsg;
	}

	/**
	 * <p>
	 * load form prop
	 * </p>
	 * 
	 * @param msgKey
	 * @param params
	 * @return
	 */
	public String getMessage(String msgKey, String[] params) {
		Object obj[] = params;
		String strErrorMsg = this.getMessage(msgKey);
		try {
			strErrorMsg = MessageFormat.format(strErrorMsg, obj);
		} catch (Exception e) {
			// do nothing
		}
		return strErrorMsg;
	}
}
