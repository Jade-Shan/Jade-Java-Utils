package net.jadedungeon.javautil.base;

import org.junit.Assert;
import org.junit.Test;

import net.jadedungeon.javautil.encryption.MD5Coder;

/*
 * 
 * @author morgan
 * 
 */
public class MD5CoderTest {

	@Test
	public void testMD5String() {
		System.out.println(MD5Coder.encodeString("Hello world!"));
		Assert.assertEquals("86fb269d190d2c85f6e0468ceca42a20",
				MD5Coder.encodeString("Hello world!"));
	}

}
