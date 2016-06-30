package jadeutils.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class TextTempletRanderTool {
	private static final Pattern TAKER_PATTERN = Pattern
			.compile("\\$\\{([0-9a-zA-Z]+\\.?)+\\}");

	/*
	 * 渲染一个模板，把<code>${env.key}</code>格式的占位内容替换成传入的值。
	 * 
	 * @param templet
	 *            需要渲染的模板
	 * @param params
	 *            对应的参数
	 * @return 渲染后的结果
	 */
	public static String render(String templet, Map<String, String> params) {
		String result = templet;
		if (StringUtils.isNotBlank(result)) {
			for (Entry<String, String> param : params.entrySet()) {
				String key = param.getKey();
				if (StringUtils.isBlank(key)) {
					// ignore this
				} else {
					key = "\\$\\{" + key.replaceAll("\\.", "\\.") + "\\}";
					String value = param.getValue();
					value = null == value ? "" : value;
					result = result.replaceAll(key, value);
				}
			}
		}
		return result;
	}

	/*
	 * 检查模板中还没有被替换的占位符
	 * 
	 * @param template
	 *            模板
	 * @return 还没有被替换的占位符列表
	 */
	public static List<String> getUnRanderedParams(String template) {
		List<String> results = null;
		Map<String, String> keyMap = new HashMap<String, String>();
		Matcher matcher = TAKER_PATTERN.matcher(template);
		while (matcher.find()) {
			String key = template.substring(matcher.start(), matcher.end());
			keyMap.put(key, key);
		}
		if (keyMap.size() > 0) {
			results = new ArrayList<String>();
			for (Entry<String, String> e : keyMap.entrySet()) {
				results.add(e.getKey());
			}
		}
		return results;
	}
}
