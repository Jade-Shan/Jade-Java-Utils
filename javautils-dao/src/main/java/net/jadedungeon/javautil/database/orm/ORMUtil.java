package net.jadedungeon.javautil.database.orm;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class ORMUtil {
	
	public static Map<String, Object> result2map(Set<String> colNames, ResultSet rs) {
		Map<String, Object> record = new HashMap<>();
		for (String colName : colNames) {
			try {
				record.put(colName, rs.getObject(colName));
			} catch (SQLException e) { e.printStackTrace(); }
		}
		return record;
	}

	public static <T extends Record<K>, K> T result2record(Class<T> clazz, Set<String> colNames, ResultSet rs) //
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, //
			InvocationTargetException, NoSuchMethodException, SecurityException //
	{
		T record = null;
		if (null != rs) {
			record = clazz.getDeclaredConstructor(clazz).newInstance();
			Field[] fields = clazz.getDeclaredFields();
			for (Field f : fields) {
				Column clm = f.getAnnotation(Column.class);
				if (clm != null) {
					String colName = clm.column();
					if (StringUtils.isBlank(colName)) {
						colName = f.getName();
					}
					if (null != colNames && !colNames.isEmpty() && !colNames.contains(colName)) {
						// skip this column
					} else {
						try {
							int colIdx = rs.findColumn(colName);
							Object colValue = rs.getObject(colIdx);
							f.setAccessible(true);
							f.set(record, colValue);
						} catch (SQLException | IllegalArgumentException | //
								IllegalAccessException e) //
						{
							e.printStackTrace();
						}
					}
				}
			}
		}
		return record;
	}

	public static Object column2field(Object column) {
		Object field = null;
		if (null == column) {
			// do nothing
		} else if (column instanceof Boolean || column instanceof Byte //
				|| column instanceof Short || column instanceof Integer //
				|| column instanceof Long || column instanceof Float //
				|| column instanceof Double || column instanceof Character //
				|| column instanceof String || column instanceof Date) //
		{
			field = column;
		}
		return field;
	}

}
