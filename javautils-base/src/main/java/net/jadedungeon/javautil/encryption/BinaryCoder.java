package net.jadedungeon.javautil.encryption;

public class BinaryCoder {

	/* define how many byte every type use */
	public static final int BYTE_BYTES = Byte.SIZE / Byte.SIZE;
	public static final int SHORT_BYTES = Short.SIZE / Byte.SIZE;
	public static final int INT_BYTES = Integer.SIZE / Byte.SIZE;
	public static final int LONG_BYTES = Long.SIZE / Byte.SIZE;

	/*
	 * 用十六进制字符串显示一个字节的内容
	 * 
	 * @param b
	 *            数据
	 * @param size
	 *            数据的长度
	 * @return 对应的十六进制显示
	 */
	public static String transDataToHexString(long b, int size) {
		StringBuffer result = new StringBuffer();
		int charSize = size * 2;
		// 每四个位为一段，从0开始循环取每一段
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < charSize; i++) {
			long curr = b >> ((charSize - i - 1) * 4);
			curr = curr & 0xF;
			sb.append(Long.toHexString(curr).toUpperCase());
		}
		// 显示空的位补0
		for (int i = sb.length(); i < charSize; i += 4) {
			sb.append("0");
		}
		result.append(sb.toString());
		return result.toString();
	}

	/*
	 * 用十进制显示数组中的每个字节
	 * 
	 * @param byteStream
	 *            要显示的数组
	 * @return 显示的字符串
	 */
	public static String transByteStreamToHexString(byte[] byteStream) {
		StringBuilder rtn = new StringBuilder();
		for (byte b : byteStream) {
			// 用十六进制字符串显示一个字节的内容
			rtn.append(BinaryCoder.transDataToHexString((long) b, BYTE_BYTES))
					.append(" ");
		}
		return rtn.toString();
	}

	/*
	 * 把长度为size个字节的变量long存入数组dst，dst前offset个字节已经存放了其他内容了。
	 * 
	 * @param byteStream
	 *            存放消息的数组
	 * @param offset
	 *            存放的起始位置
	 * @param data
	 *            要存放的值
	 * @param dataSize
	 *            存放的值占几个字节
	 * @return 存放后数组被使用了多少个字节（新的offset）
	 */
	public static int encodeBigEndian(byte[] byteStream, int offset, long data,
			int dataSize) {
		// Warning: Untested preconditions (e.g., 0 <= dataSize <= 8)
		for (int i = 0; i < dataSize; i++) {
			// 先通过位移把要取的那8位移到最低，
			// 通过强制转换把最低的8位转为一个byte。
			byteStream[offset++] = (byte) (data >> ((dataSize - i - 1) * Byte.SIZE));
		}
		return offset;
	}

	/*
	 * 从数组中取出数据
	 * 
	 * @param byteStream
	 *            存放消息的数组
	 * @param offset
	 *            存放的起始位置
	 * @param dataSize
	 *            存放的值占几个字节
	 * @return 读出的值
	 */
	public static long decodeBigEndian(byte[] byteStream, int offset,//
			int dataSize) {
		// Warning: Untested preconditions (e.g., 0 <= dataSize <= 8)
		long data = 0;
		for (int i = 0; i < dataSize; i++) {
			// 先把当前的内容左移一个字节
			// 空出的字节中填入流中指定位置的字节
			data = (data << Byte.SIZE) | ((long) byteStream[offset + i] & 0xFF);
		}
		return data;
	}

}
