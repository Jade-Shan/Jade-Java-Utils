/**
 * 
 */
package jadeutils.reflect;

import java.lang.reflect.Method;

/**
 * @author qwshan
 * 
 */
public abstract class BaseAction {

	public String execution(String funcName, String a, String b, String c)
			throws Exception {
		funcName = null == funcName ? "defFunc" : funcName;
		Method func = this.getClass().getMethod(funcName, String.class,
				String.class, String.class);
		return (String) func.invoke(this, a, b, c);
	}

	public abstract String defFunc(String a, String b, String c)
			throws Exception;

}
