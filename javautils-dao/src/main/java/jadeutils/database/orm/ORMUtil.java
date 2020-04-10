package jadeutils.database.orm;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class ORMUtil {
	
	public static <T extends Record<K>, K> T result2record(Class<T> clazz, ResultSet rs) //
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, //
			InvocationTargetException, NoSuchMethodException, SecurityException  //
	{
		T record = null;
		if (null != rs) {
			record = clazz.getDeclaredConstructor(clazz).newInstance();
			Field[] fields = clazz.getDeclaredFields();
			for (Field f: fields) {
				Column clm = f.getAnnotation(Column.class);
				if (clm != null) {
					String colName = clm.column();
					if (StringUtils.isBlank(colName)) {
						colName = f.getName();
					}
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
