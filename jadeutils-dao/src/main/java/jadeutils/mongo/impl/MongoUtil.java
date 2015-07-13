package jadeutils.mongo.impl;

import jadeutils.mongo.Condition;
import jadeutils.mongo.MongoDocument;
import jadeutils.mongo.MongoField;
import jadeutils.mongo.MongoModel;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class MongoUtil<T extends MongoModel> {

	/**
	 * get table Name from Object
	 * 
	 * @param obj
	 * @return table name
	 */
	public static <T extends MongoModel> String getCollectionNameFromOjbect(
			T obj) {
		MongoDocument md = obj.getClass().getAnnotation(MongoDocument.class);
		return null == md ? null : md.collectionName();
	}

	/**
	 * generate rec from object
	 * 
	 * @param obj
	 * @return DBObject
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static <T extends MongoModel> DBObject genRecFromModel(T obj)
			throws IllegalArgumentException, IllegalAccessException {
		return genRecFromObject(obj);
	}

	public static DBObject genRecFromObject(Object obj)
			throws IllegalArgumentException, IllegalAccessException {
		DBObject rec = new BasicDBObject();
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field f : fields) {
			if (f.getAnnotation(MongoField.class) != null) {
				f.setAccessible(true);
				rec.put(f.getName(), parseObject(f.get(obj)));
			}
		}
		return rec;
	}

	/**
	 * 
	 * @param clazz
	 * @param rec
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	public static <T extends MongoModel> T genModelFromRec(
			Class<? extends MongoModel> clazz, DBObject rec)
			throws InstantiationException, IllegalAccessException {
		Object obj = genObjectFromRec(clazz, rec);
		if (null != obj) {
			T result = (T) obj;
			ObjectId mid = (ObjectId) rec.get("_id");
			result.setMongoId(null == mid ? null : mid.toString());
			return result;
		} else {
			return null;
		}
	}

	public static Object genObjectFromRec(Class<?> clazz, DBObject rec)
			throws InstantiationException, IllegalAccessException {
		Object obj = null;
		if (null != rec) {
			obj = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			for (Field f : fields) {
				MongoField mf = f.getAnnotation(MongoField.class);
				if (mf != null && rec.containsField(f.getName())) {
					f.setAccessible(true);
					Object value = parseField(mf.ElemType(),
							rec.get(f.getName()));
					f.set(obj, value);
				}
			}
		}
		return obj;
	}

	/**
	 * 
	 * @param clazz
	 * @param value
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static Object parseField(Class<?> clazz, Object value)
			throws IllegalArgumentException, IllegalAccessException,
			InstantiationException {
		Object result = null;
		if (null == value) {
			// do nothing
		} else if (value instanceof Boolean || value instanceof Byte
				|| value instanceof Short || value instanceof Integer
				|| value instanceof Long || value instanceof Float
				|| value instanceof Double || value instanceof Character
				|| value instanceof String || value instanceof Date) //
		{
			result = value;
		} else if (value instanceof List<?>) {
			@SuppressWarnings("unchecked")
			List<Object> l = (List<Object>) value;
			List<Object> ll = new ArrayList<>();
			for (Object o : l) {
				ll.add(parseField(clazz, o));
			}
			result = ll;
		} else if (value instanceof Object[]) {
			Object[] l = (Object[]) value;
			List<Object> ll = new ArrayList<>();
			for (Object o : l) {
				ll.add(parseField(clazz, o));
			}
			result = ll;
		} else if (value instanceof Map<?, ?> && clazz.isInstance(Map.class)) {
			@SuppressWarnings("unchecked")
			Map<Object, Object> m = (Map<Object, Object>) value;
			if (!m.isEmpty()) {
				Class<?> et = m.entrySet().toArray()[0].getClass();
				Map<String, Object> o = new HashMap<>();
				for (Entry<Object, Object> e : m.entrySet()) {
					o.put(e.getKey().toString(), parseField(et, e.getValue()));
				}
				result = o;
			}
		} else if (value instanceof DBObject) {
			result = genObjectFromRec(clazz, (DBObject) value);
		}
		return result;
	}

	/**
	 * 
	 * @param value
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Object parseObject(Object value)
			throws IllegalArgumentException, IllegalAccessException {
		Object result = null;
		if (null == value) {
			// do nothing
		} else if (value instanceof Boolean || value instanceof Byte
				|| value instanceof Short || value instanceof Integer
				|| value instanceof Long || value instanceof Float
				|| value instanceof Double || value instanceof Character
				|| value instanceof String || value instanceof Date) //
		{
			result = value;
		} else if (value instanceof List<?>) {
			@SuppressWarnings("unchecked")
			List<Object> l = (List<Object>) value;
			List<Object> ll = new ArrayList<>();
			for (Object o : l) {
				ll.add(parseObject(o));
			}
			result = ll;
		} else if (value instanceof Object[]) {
			Object[] l = (Object[]) value;
			List<Object> ll = new ArrayList<>();
			for (Object o : l) {
				ll.add(parseObject(o));
			}
			result = ll;
		} else if (value instanceof Map<?, ?>) {
			@SuppressWarnings("unchecked")
			Map<Object, Object> m = (Map<Object, Object>) value;
			DBObject o = new BasicDBObject();
			for (Entry<Object, Object> e : m.entrySet()) {
				o.put(e.getKey().toString(), parseObject(e.getValue()));
			}
			result = o;
		} else {
			result = genRecFromObject(value);
		}
		return result;
	}

	/**
	 * 
	 * @param condition
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static BasicDBObject parseCondition(Condition condition) throws IllegalArgumentException, IllegalAccessException {
		BasicDBObject result = null;
		if (null != condition && null != condition.getKey() && null != condition.getValue()) {
			Object value = parseConditionValue(condition.getValue());
			if (null != value) {
				result = new BasicDBObject(condition.getKey(), value);
				addLink(result, condition);
			}
		}
		return result;
	}

	private static void addLink(BasicDBObject result, Condition condition) throws IllegalArgumentException, IllegalAccessException {
		Condition.Link link = condition.getLink();
		if (null != link && null != link.getCondition()) {
			if (Condition.LinkType.AND.equals(link.getType())) {
				result.append(link.getCondition().getKey(),
						parseConditionValue(link.getCondition().getValue()));
			}
		}
	}

	private static Object parseConditionValue(Object value) throws IllegalArgumentException, IllegalAccessException {
		Object result = null;
		if (null == value) {
			// do nothing
		} else if (value instanceof Boolean || value instanceof Byte
				|| value instanceof Short || value instanceof Integer
				|| value instanceof Long || value instanceof Float
				|| value instanceof Double || value instanceof Character
				|| value instanceof String || value instanceof Date) //
		{
			result = value;
		} else if (value instanceof List<?>) {
			@SuppressWarnings("unchecked")
			List<Object> l = (List<Object>) value;
			List<Object> ll = new ArrayList<>();
			for (Object o : l) {
				ll.add(parseConditionValue(o));
			}
			result = ll;
		} else if (value instanceof Object[]) {
			Object[] l = (Object[]) value;
			List<Object> ll = new ArrayList<>();
			for (Object o : l) {
				ll.add(parseConditionValue(o));
			}
			result = ll;
		} else if (value instanceof Map<?, ?>) {
			@SuppressWarnings("unchecked")
			Map<Object, Object> m = (Map<Object, Object>) value;
			DBObject o = new BasicDBObject();
			for (Entry<Object, Object> e : m.entrySet()) {
				o.put(e.getKey().toString(), parseConditionValue(e.getValue()));
			}
			result = o;
		} else if (value instanceof Condition) {
			result = parseCondition((Condition) value);
		} else {
			result = genRecFromObject(value);
		}
		return result;
	}
}
