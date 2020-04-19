package net.jadedungeon.javautil.mongo;

import static net.jadedungeon.javautil.mongo.Condition.newCondition;
import static org.junit.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserDaoTest {
	// private Logger logger = LoggerFactory.getLogger(this.getClass());

	// 配置文件位置
	private static final String CONF_FILE_PROP = "jade-mongodb.properties";

	private Properties props = null;

	private UserDao dao = null;
	private List<User> users = new ArrayList<User>();

	public UserDaoTest() throws IOException {
		InputStream confIn = new BufferedInputStream(Thread.currentThread()
				.getContextClassLoader().getResourceAsStream(CONF_FILE_PROP));
		props = new Properties();
		props.load(confIn);
		//
		Map<String, String> ss = new HashMap<>();
		ss.put("key1", "value1");
		ss.put("key2", "value2");
		ss.put("key3", "value3");
		for (int i = 0; i < 100; i++) {
			User user = new User("A00" + i, "Jade", i, Arrays.asList(
					new UserAuth("func1", true), new UserAuth("func2", false)),
					Arrays.asList("aaa", "bbb"), ss, true);
			users.add(user);
		}
	}

	@Before
	public void setUp() throws Exception {
		dao = new UserDao(Arrays.asList(new MongoServer(props.getProperty(
				"mongo.host", "localhost"), Integer.parseInt(props.getProperty(
				"mongo.port", "27017")))));
	}

	@After
	public void tearDown() throws Exception {
		// dao.close();
	}

	@Test
	public void testInsert() throws IllegalArgumentException,
			IllegalAccessException, InterruptedException //
	{
		for (User user : users) {
			dao.insert(user);
		}
	}

	@Test
	public void testFindByMongoId() throws IllegalArgumentException,
			IllegalAccessException, InterruptedException,
			InstantiationException //
	{
		dao.insert(users.get(0));
		User u = null;
		u = dao.getByMongoId("546f6a07cb32030f30d88ce5");
		System.out.println(u);
		Assert.assertNotNull(u);
		u = dao.getByMongoId("546f6a18cb326bfb10945cf8");
		System.out.println(u);
		Assert.assertNotNull(u);
		assertEquals(true, true);
	}

	@Test
	public void testFindOneByCondition01() throws InstantiationException,
			IllegalAccessException //
	{
		Condition cdt = newCondition("name", "Jade");
		User u = dao.findOneByCondition(cdt);
		System.out.println(u);
	}

	@Test
	public void testFindByCondition01() throws InstantiationException,
			IllegalAccessException //
	{
		MongoResultSet<User> rs = null;
		Condition cdt = newCondition("name", "Jade");
		rs = dao.findByCondition(cdt).sort(newCondition("level", 1)).skip(30)
				.limit(30);
		while (rs.hasNext()) {
			System.out.println(rs.next().toString());
		}
	}

	@Test
	public void testUpdateOne() throws IllegalArgumentException,
			IllegalAccessException //
	{
		Condition cdt = newCondition("onDuby", true);
		Condition opt = newCondition("$set", newCondition("onDuby", false));
		System.out.println(opt);
		dao.updateOne(cdt, opt);
	}

	@Test
	public void testUpdateOne02() throws IllegalArgumentException,
			IllegalAccessException //
	{
		Condition cdt = newCondition("onDuby", true);

		List<UserAuth> l = new ArrayList<UserAuth>();
		l.add(new UserAuth("func1", true));
		l.add(new UserAuth("func2a", true));
		l.add(new UserAuth("func3", false));
		Condition opt = newCondition("$set", newCondition("authList", l));
		System.out.println(opt);
		dao.updateOne(cdt, opt);
	}

	@Test
	public void testUpdateAll() throws IllegalArgumentException,
			IllegalAccessException //
	{
		Condition cdt = newCondition("onDuby", true);
		Condition opt = newCondition("$set", newCondition("onDuby", false));
		System.out.println(opt);
		dao.updateAll(cdt, opt);
	}

}
