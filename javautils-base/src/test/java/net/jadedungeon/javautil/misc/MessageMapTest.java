package net.jadedungeon.javautil.misc;

import junit.framework.Assert;
import net.jadedungeon.javautil.text.MessageMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MessageMapTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMessageMap() {
		MessageMap mm = new MessageMap("test");
		Assert.assertEquals("key1", mm.getMessage("key1"));
	}

}
