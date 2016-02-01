package jadeutils.mongo;

import jadeutils.mongo.impl.MongoUtil;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.DBCursor;

public class MongoResultSet<T extends MongoModel> {
	private DBCursor cursor;
	private Class<T> entryClass;

	public MongoResultSet(Class<T> entryClass, DBCursor cursor) {
		this.cursor = cursor;
		this.entryClass = entryClass;
	}

	public MongoResultSet<T> skip(int n) {
		return new MongoResultSet<T>(this.entryClass, this.cursor.skip(n));
	}

	public MongoResultSet<T> limit(int n) {
		return new MongoResultSet<T>(this.entryClass, this.cursor.limit(n));
	}

	public MongoResultSet<T> sort(Condition orderBy)
			throws IllegalArgumentException, IllegalAccessException //
	{
		return new MongoResultSet<T>(this.entryClass,
				this.cursor.sort(MongoUtil.parseCondition(orderBy)));
	}

	public boolean hasNext() {
		if (null == cursor) {
			return false;
		} else {
			boolean result = cursor.hasNext();
			if (result) {
				return result;
			} else {
				cursor.close();
				return false;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public T next() throws InstantiationException, IllegalAccessException {
		T obj = null;
		if (cursor.hasNext()) {
			obj = (T) MongoUtil.genModelFromRec(entryClass, cursor.next());
		} else {
			cursor.close();
		}
		return obj;
	}

	public List<T> toList() throws InstantiationException,
			IllegalAccessException {
		List<T> list = new ArrayList<T>();
		while (this.hasNext()) {
			list.add(this.next());
		}
		return list;
	}

}
