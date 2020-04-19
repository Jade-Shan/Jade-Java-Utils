package net.jadedungeon.javautil.base;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

public class BeanUtils {

	public static Object getFieldValue(Object bean, String fieldName)
			throws Exception {
		String getterName = (new StringBuffer("get"))
				.append(fieldName.substring(0, 1).toUpperCase())//
				.append(fieldName.substring(1)).toString();

		return bean.getClass().getDeclaredMethod(getterName).invoke(bean);
	}

	/*
	 * 
	 * @param srcBean
	 * @param tagBean
	 * @param fieldName
	 */
	public static void copyField(Object srcBean, Object tagBean,
			Class<?> fieldType, String fieldName) throws Exception {
		String getterName = (new StringBuffer("get"))
				.append(fieldName.substring(0, 1).toUpperCase())//
				.append(fieldName.substring(1)).toString();
		String setterName = (new StringBuffer("set"))
				.append(fieldName.substring(0, 1).toUpperCase())//
				.append(fieldName.substring(1)).toString();
		Method setter = tagBean.getClass().getDeclaredMethod(setterName,
				fieldType);
		Method getter = srcBean.getClass().getDeclaredMethod(getterName);
		setter.invoke(tagBean, getter.invoke(srcBean));
	}

	/*
	 * Copy bean object
	 * 
	 * @param srcBean
	 * @param tagBean
	 * @author
	 */
	public static void copyBean(Object srcBean, Object tagBean) {
		try {
			copyBean(srcBean, tagBean, true);
		} catch (Exception e) {
			// do nothing
		}
	}

	/*
	 * Copy bean object
	 * 
	 * @param srcBean
	 * @param tb
	 * @author
	 */
	public static void copyBean(Object srcBean, Object tagBean, boolean isSilent)
			throws Exception {
		Map<String, Method> getterMap = getPropModifier(srcBean, true);
		Map<String, Method> setterMap = getPropModifier(tagBean, false);
		copyProps(srcBean, tagBean, getterMap, setterMap,
				getterMap.size() < setterMap.size(), isSilent);
	}

	private static void copyProps(Object srcBean, Object tagBean,
			Map<String, Method> getterMap, Map<String, Method> setterMap,
			boolean isLoopGetter, boolean isSilent) throws Exception {
		Map<String, Method> lopper = isLoopGetter ? getterMap : setterMap;
		for (Entry<String, Method> t : lopper.entrySet()) {
			Method getter = getterMap.get(t.getKey());
			Method setter = setterMap.get(t.getKey());
			if (null != getter && null != setter) {
				try {
					if (null != getter.invoke(srcBean)) {
						setter.invoke(tagBean, getter.invoke(srcBean));
					}
				} catch (Exception e) {
					if (!isSilent) {
						throw e;
					}
				}
			}
		}
	}

	private static Map<String, Method> getPropModifier(Object bean,
			boolean isGetter) {
		Map<String, Method> result = new HashMap<String, Method>();
		for (Method m : bean.getClass().getMethods()) {
			String propLabel = "";
			String methodName = m.getName();
			if (methodName.startsWith(isGetter ? "get" : "set")) {
				propLabel = methodName.substring(3);
			}
			if (StringUtils.isNotBlank(propLabel)) {
				result.put(propLabel, m);
			}
		}
		return result;
	}

}
