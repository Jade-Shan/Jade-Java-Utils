/*
 * 
 */
package net.jadedungeon.javautil.base;

import static net.jadedungeon.javautil.encryption.BinaryCoder.BYTE_BYTES;
import static net.jadedungeon.javautil.encryption.BinaryCoder.INT_BYTES;
import static net.jadedungeon.javautil.encryption.BinaryCoder.LONG_BYTES;
import static net.jadedungeon.javautil.encryption.BinaryCoder.SHORT_BYTES;
import static net.jadedungeon.javautil.encryption.BinaryCoder.decodeBigEndian;
import static net.jadedungeon.javautil.encryption.BinaryCoder.encodeBigEndian;
import static net.jadedungeon.javautil.encryption.BinaryCoder.transByteStreamToHexString;
import static net.jadedungeon.javautil.encryption.BinaryCoder.transDataToHexString;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/*
 * @author morgan
 * 
 */
public class BinaryCoderTest {

	private static byte byteVal = 101;
	private static short shortVal = 10001;
	private static int intVal = 100000001;
	private static long longVal = 1000000000001L;

	/*
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/*
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTransDataToHexString() {
		assertEquals("65", transDataToHexString(byteVal, BYTE_BYTES));
		assertEquals("2711", transDataToHexString(shortVal, SHORT_BYTES));
		assertEquals("05F5E101", transDataToHexString(intVal, INT_BYTES));
		assertEquals("000000E8D4A51001",
				transDataToHexString(longVal, LONG_BYTES));
	}

	@Test
	public void testTransByteStreamToHexString() {
		byte[] byteStream = "Hello World!".getBytes();
		assertEquals("48 65 6C 6C 6F 20 57 6F 72 6C 64 21 ",
				transByteStreamToHexString(byteStream));
	}

	@Test
	public void testEncodeIntBigEndian() {
		// stream of byte code
		byte[] byteStream = new byte[BYTE_BYTES + SHORT_BYTES + INT_BYTES
				+ LONG_BYTES];
		// encode the datas
		int offset = 0;
		offset = encodeBigEndian(byteStream, offset, byteVal, BYTE_BYTES);
		offset = encodeBigEndian(byteStream, offset, shortVal, SHORT_BYTES);
		offset = encodeBigEndian(byteStream, offset, intVal, INT_BYTES);
		offset = encodeBigEndian(byteStream, offset, longVal, LONG_BYTES);
		//
		assertEquals("65 27 11 05 F5 E1 01 00 00 00 E8 D4 A5 10 01 ",
				transByteStreamToHexString(byteStream));
	}

	@Test
	public void testDecodeBigEndian() {
		/*
		 * prepare data
		 */
		byte[] byteStream = new byte[BYTE_BYTES + SHORT_BYTES + INT_BYTES
				+ LONG_BYTES];
		int offset = 0;
		offset = encodeBigEndian(byteStream, offset, byteVal, BYTE_BYTES);
		offset = encodeBigEndian(byteStream, offset, shortVal, SHORT_BYTES);
		offset = encodeBigEndian(byteStream, offset, intVal, INT_BYTES);
		offset = encodeBigEndian(byteStream, offset, longVal, LONG_BYTES);

		/*
		 * 
		 * Decode several fields
		 */
		long value = 0L;
		offset = 0;
		//
		value = decodeBigEndian(byteStream, offset, BYTE_BYTES);
		assertEquals(101, value);
		offset += BYTE_BYTES;
		//
		value = decodeBigEndian(byteStream, offset, SHORT_BYTES);
		assertEquals(10001, value);
		offset += SHORT_BYTES;
		//
		value = decodeBigEndian(byteStream, offset, INT_BYTES);
		assertEquals(100000001, value);
		offset += INT_BYTES;
		//
		value = decodeBigEndian(byteStream, offset, LONG_BYTES);
		assertEquals(1000000000001L, value);
		offset += LONG_BYTES;

	}
}
