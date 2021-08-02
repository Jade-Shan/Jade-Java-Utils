/*
 * 
 */
package net.jadedungeon.javautil.text;

import java.util.ArrayList;

import net.jadedungeon.javautil.reflect.User;
import net.jadedungeon.javautil.text.JsonUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/*
 * @author SHAN013
 * 
 */
public class JsonUtilsTest {
	private static JsonUtils util = JsonUtils.newInstance();

	/*
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/*
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		User a = new User();
		a.setId("001");
		a.setName("aa");
		a.setAge(20);
		a.setIsAdmin(false);
		a.setActived(true);
		System.out.println(util.toJson(a));
	}

	/*
	 * 格式化json字符串
	 * 
	 * @param json
	 *            要格式化的json字符串
	 * @param indentStr
	 *            缩进方式
	 * @return 格式化后的字符串
	 */
	public static String formatJson(String json, String indentStr) {
		if (json == null || json.trim().length() == 0) {
			return null;
		}

		int fixedLenth = 0;
		ArrayList<String> tokenList = new ArrayList<String>();
		{
			String jsonTemp = json;
			// 预读取
			while (jsonTemp.length() > 0) {
				String token = getToken(jsonTemp);
				jsonTemp = jsonTemp.substring(token.length());
				token = token.trim();
				tokenList.add(token);
			}
		}

		for (int i = 0; i < tokenList.size(); i++) {
			String token = tokenList.get(i);
			int length = token.getBytes().length;
			if (length > fixedLenth && i < tokenList.size() - 1
					&& tokenList.get(i + 1).equals(":")) {
				fixedLenth = length;
			}
		}

		StringBuilder buf = new StringBuilder();
		int indentLevel = 0;
		for (int i = 0; i < tokenList.size(); i++) {

			String token = tokenList.get(i);

			if (token.equals(",")) {
				buf.append(token);
				doFill(buf, indentLevel, indentStr);
				continue;
			}
			if (token.equals(":")) {
				buf.append(" ").append(token).append(" ");
				continue;
			}
			if (token.equals("{")) {
				String nextToken = tokenList.get(i + 1);
				if (nextToken.equals("}")) {
					i++;
					buf.append("{ }");
				} else {
					indentLevel++;
					buf.append(token);
					doFill(buf, indentLevel, indentStr);
				}
				continue;
			}
			if (token.equals("}")) {
				indentLevel--;
				doFill(buf, indentLevel, indentStr);
				buf.append(token);
				continue;
			}
			if (token.equals("[")) {
				String nextToken = tokenList.get(i + 1);
				if (nextToken.equals("]")) {
					i++;
					buf.append("[ ]");
				} else {
					indentLevel++;
					buf.append(token);
					doFill(buf, indentLevel, indentStr);
				}
				continue;
			}
			if (token.equals("]")) {
				indentLevel--;
				doFill(buf, indentLevel, indentStr);
				buf.append(token);
				continue;
			}

			buf.append(token);
			// 左对齐
			if (i < tokenList.size() - 1 && tokenList.get(i + 1).equals(":")) {
				int fillLength = fixedLenth - token.getBytes().length;
				if (fillLength > 0) {
					for (int j = 0; j < fillLength; j++) {
						buf.append(" ");
					}
				}
			}
		}
		return buf.toString();
	}

	private static String getToken(String json) {
		StringBuilder buf = new StringBuilder();
		boolean isInYinHao = false;
		while (json.length() > 0) {
			String token = json.substring(0, 1);
			json = json.substring(1);

			if (!isInYinHao
					&& (token.equals(":") || token.equals("{")
							|| token.equals("}") || token.equals("[")
							|| token.equals("]") || token.equals(","))) {
				if (buf.toString().trim().length() == 0) {
					buf.append(token);
				}

				break;
			}

			if (token.equals("\\")) {
				buf.append(token);
				buf.append(json.substring(0, 1));
				json = json.substring(1);
				continue;
			}
			if (token.equals("\"")) {
				buf.append(token);
				if (isInYinHao) {
					break;
				} else {
					isInYinHao = true;
					continue;
				}
			}
			buf.append(token);
		}
		return buf.toString();
	}

	/*
	 * 生成行
	 * 
	 * @param buf
	 *            文本
	 * @param indentLevel
	 *            缩进的层级
	 * @param indentStr
	 *            缩进的字符串
	 */
	private static void doFill(StringBuilder buf, int indentLevel,
			String indentStr) {
		buf.append("\n");
		for (int i = 0; i < indentLevel; i++) {
			buf.append(indentStr);
		}
	}
}
