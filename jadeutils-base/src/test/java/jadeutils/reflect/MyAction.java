/**
 * 
 */
package jadeutils.reflect;

/**
 * @author qwshan
 * 
 */
public class MyAction extends BaseAction {

	@Override
	public String defFunc(String a, String b, String c) {
		return "MyAction.defFunc(" + a + ", " + b + ", " + c + ")";
	}

	public String doLogic(String a, String b, String c) {
		return "MyAction.doLogic(" + a + ", " + b + ", " + c + ")";
	}
}
