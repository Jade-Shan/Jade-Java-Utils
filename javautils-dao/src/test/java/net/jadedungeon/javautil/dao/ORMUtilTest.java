package net.jadedungeon.javautil.dao;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.junit.Test;

import net.jadedungeon.javautil.database.orm.Column;
import net.jadedungeon.javautil.database.orm.Record;
import net.jadedungeon.javautil.database.orm.Table;

public class ORMUtilTest {

	@Table(database = "TestDB", table = "TestTable")
	class CustRec extends Record<Integer> {

		@Column(column = "name")
		private String name;

		public CustRec() {
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	@Test
	public void testFieldAnnotation() throws Exception {
		Class<CustRec> clazz = CustRec.class;
		Field[] fields = clazz.getDeclaredFields();
		for (Field f : fields) {
			Annotation[] aArr = f.getAnnotations();
			for (Annotation a : aArr) {
				System.out.println(a.annotationType());
			}
		}
	}

}
