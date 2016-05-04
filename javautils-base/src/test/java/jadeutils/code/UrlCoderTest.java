/**
 * 
 */
package jadeutils.code;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author SHAN013
 * 
 */
public class UrlCoderTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws UnsupportedEncodingException {
		String url = "evoker_alucard@163.com";
		String du = URLEncoder.encode(url, "UTF-8");
		System.out.println(du);
		assertEquals(url, URLDecoder.decode(du, "UTF-8"));
	}

}
