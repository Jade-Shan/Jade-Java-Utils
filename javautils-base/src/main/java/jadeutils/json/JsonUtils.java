package jadeutils.json;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

public class JsonUtils {
	private ObjectMapper mapper;

	private JsonUtils(Inclusion inclusion) {
		mapper = new ObjectMapper();
		// 设置输出包含的属性
		mapper.getSerializationConfig().setSerializationInclusion(inclusion);
		// 设置输入时忽略JSON字符串中存在而Java对象实际没有的属性
		mapper.getDeserializationConfig()
				.set(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
						false);
	}

	/*
	 * 创建只输出初始值被改变的属性到Json字符串的Binder.
	 */
	public static JsonUtils newInstance() {
		return new JsonUtils(Inclusion.NON_DEFAULT);
	}

	/*
	 * 创建输出全部属性到Json字符串的Binder.
	 */
	public static JsonUtils newInstanceAlways() {
		return new JsonUtils(Inclusion.ALWAYS);
	}

	/*
	 * 创建只输出非空属性到Json字符串的Binder.
	 */
	public static JsonUtils newInstanceNonNull() {
		return new JsonUtils(Inclusion.NON_NULL);
	}

	/*
	 * 如果JSON字符串为Null或"null"字符串,返回Null. 如果JSON字符串为"[]",返回空集合.
	 * 
	 * 如需读取集合如List/Map,且不是List<String>这种简单类型时使用如下语句: List<MyBean> beanList =
	 * binder.getMapper().readValue(listString, new
	 * TypeReference<List<MyBean>>() {});
	 */
	public <T> T fromJson(String jsonString, Class<T> clazz) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}

		try {
			return mapper.readValue(jsonString, clazz);
		} catch (IOException e) {
			// logger.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}

	/*
	 * 如果对象为Null,返回"null". 如果集合为空集合,返回"[]".
	 */
	public String toJson(Object object) {

		try {
			return mapper.writeValueAsString(object);
		} catch (IOException e) {
			// logger.warn("write to json string error:" + object, e);
			return null;
		}
	}

	/*
	 * 设置转换日期类型的format pattern,如果不设置默认打印Timestamp毫秒数.
	 */
	public void setDateFormat(String pattern) {
		if (StringUtils.isNotBlank(pattern)) {
			DateFormat df = new SimpleDateFormat(pattern);
			mapper.getSerializationConfig().setDateFormat(df);
			mapper.getDeserializationConfig().setDateFormat(df);
		}
	}

	/*
	 * 取出Mapper做进一步的设置或使用其他序列化API.
	 */
	public ObjectMapper getMapper() {
		return mapper;
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
