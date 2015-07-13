package jadeutils.mongo;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AnnotationTest {

	private User user = null;

	@Before
	public void setUp() throws Exception {
		Map<String, String> ss = new HashMap<>();
		ss.put("key1", "value1");
		ss.put("key2", "value2");
		ss.put("key3", "value3");
		user = new User("A001", "Jade", 3, Arrays.asList(new UserAuth("func1",
				true), new UserAuth("func2", false)), Arrays.asList("aaa",
				"bbb"), ss, true);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMongoDocument() {
		MongoDocument md = user.getClass().getAnnotation(MongoDocument.class);
		assertEquals("user", md.collectionName());
		assertEquals("jade-utils-test", md.databaseName());

		//
		assertEquals(null,
				"A String".getClass().getAnnotation(MongoDocument.class));
	}

	@Test
	public void testMongoFile() throws IllegalArgumentException,
			IllegalAccessException //
	{
		Field[] fields = user.getClass().getDeclaredFields();
		for (Field f : fields) {
			if (f.getAnnotation(MongoField.class) != null) {
				f.setAccessible(true);
				System.out.println(//
						f.getName() + "=" + f.get(user) + ": " + f.getType());
			}
		}
	}

}
