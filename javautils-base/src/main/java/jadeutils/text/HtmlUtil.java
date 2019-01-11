package jadeutils.text;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @Function: html 转为纯文本 保留格式
 * 
 * 
 * @Author: zhangZhiPeng
 * 
 * @Date: 2013-10-29
 * 
 * @Modifications:
 * 
 * @Modifier Name; Date; The Reason for Modifying
 *
 * 
 * 
 */
public class HtmlUtil {

	public static void main(String[] args) {
		String content = html2text("<p>The Nobel(撒娇的空间卡死的快乐? Peace Prize for 2008 was given to Martti Ahtisaari. "
				+ "He was the president of Finland from 1994 to 2000. He won the prize for his work in solving international "
				+ "conflicts for more than 30 years. </p>"
				+ "<p>&nbsp;&nbsp; &nbsp; his work has made a more peaceful world in Nobel spirit, the officer said, "
				+ "he has won the prize.</p>" //
				+ "<h2 id=\"toc_0.1\">理论基础</h2> <p> 在这里1V可以定义为产生1A电流来做1W的功所需要的压力。如果脱离电子学来看： </p> "
				+ "<ul> <li> 1W等于每秒1J的功。 <li> 1J等于1N的力的作用下，前进1m所需要的功。 <li> 1N等于每秒让1kg的物体加速1 m/s所需要的力。 </ul> "
				+ "<table> <tr> <th> 毫瓦表示法 </th> <th> 瓦特表示法 </th> <th> 千瓦表示法 </th> <th> 兆瓦表示法 </th> </tr> "
				+ "<tr> <td> 1 mW </td> <td> 0.001 W </td> <td> 0.000,001 kW </td> <td> 0.000,000,001 MW </td> </tr> "
				+ "<tr> <td> 1000 mW </td> <td> 1 W </td> <td> 0.001 kW </td> <td> 0.000,001 MW </td> </tr> "
				+ "<tr> <td> 1,000,000 mW </td> <td> 1000 W </td> <td> 1 kW </td> <td> 0.001 MW </td> </tr> "
				+ "<tr> <td> 1,000,000,000 mW </td> <td> 1000,000 W </td> <td> 1000 kW </td> <td> 1 MW </td> </tr> </table> "
				+ "<p> 注意：毫瓦（mW）里的m是小写的，而兆瓦（MW）里的M是大写的。不要写错了。 </p> ");
		System.out.println(content);
	}

	/**
	 * parse html to formatted text
	 * 
	 * @param html
	 *            html source
	 * @return formatted text
	 */
	public static String html2text(String html) {
		StringBuffer sb = new StringBuffer(html.length());
		char[] data = html.toCharArray();
		int start = 0;
		boolean previousIsPre = false;
		HtmlToken token = null;
		for (;;) {
			token = parse(data, start, previousIsPre);
			if (token == null)
				break;
			previousIsPre = token.isPreTag();
			sb = sb.append(token.getText());
			start += token.getLength();
		}
		return sb.toString();
	}

	/**
	 * parse html
	 * 
	 * @param data
	 *            data
	 * @param start
	 *            start index
	 * @param previousIsPre
	 *            previous
	 * @return html TOKEN
	 */
	private static HtmlToken parse(char[] data, int start, boolean previousIsPre) {
		if (start >= data.length)
			return null;

		// try to read next char:
		char c = data[start];
		if (c == '<') {
			// this is a tag or comment or script:
			int end_index = indexOf(data, start + 1, '>');
			if (end_index == (-1)) {
				// the left is all text!
				return new HtmlToken(HtmlToken.TOKEN_TEXT, data, start, data.length, previousIsPre);
			}

			String s = new String(data, start, end_index - start + 1);
			// now we got s="<...>":
			if (s.startsWith("<!--")) { // this is a comment!
				int end_comment_index = indexOf(data, start + 1, "-->");
				if (end_comment_index == (-1)) {
					// illegal end, but treat as comment:
					return new HtmlToken(HtmlToken.TOKEN_COMMENT, data, start, data.length, previousIsPre);
				} else {
					return new HtmlToken(HtmlToken.TOKEN_COMMENT, data, start, end_comment_index + 3, previousIsPre);
				}

			}
			String s_lowerCase = s.toLowerCase();
			if (s_lowerCase.startsWith("<script")) { // this is a script:
				int end_script_index = indexOf(data, start + 1, "</script>");
				if (end_script_index == (-1))
					// illegal end, but treat as script:
					return new HtmlToken(HtmlToken.TOKEN_SCRIPT, data, start, data.length, previousIsPre);

				else

					return new HtmlToken(HtmlToken.TOKEN_SCRIPT, data, start, end_script_index + 9, previousIsPre);

			} else { // this is a tag:
				return new HtmlToken(HtmlToken.TOKEN_TAG, data, start, start + s.length(), previousIsPre);
			}
		}

		// this is a text:
		int next_tag_index = indexOf(data, start + 1, '<');
		if (next_tag_index == (-1)) {
			return new HtmlToken(HtmlToken.TOKEN_TEXT, data, start, data.length, previousIsPre);
		}
		return new HtmlToken(HtmlToken.TOKEN_TEXT, data, start, next_tag_index, previousIsPre);
	}

	private static int indexOf(char[] data, int start, String s) {
		char[] ss = s.toCharArray();
		// TODO: performance can improve!
		for (int i = start; i < (data.length - ss.length); i++) {
			// compare from data[i] with ss[0]:
			boolean match = true;
			for (int j = 0; j < ss.length; j++) {
				if (data[i + j] != ss[j]) {
					match = false;
					break;
				}
			}
			if (match) {
				return i;
			}

		}
		return (-1);
	}

	private static int indexOf(char[] data, int start, char c) {
		for (int i = start; i < data.length; i++) {
			if (data[i] == c) {
				return i;
			}
		}
		return (-1);
	}

}

class HtmlToken {

	public static final int TOKEN_TEXT = 0; // html text.
	public static final int TOKEN_COMMENT = 1; // comment like <!-- comments... -->
	public static final int TOKEN_TAG = 2; // tag like <pre>, <font>, etc.
	public static final int TOKEN_SCRIPT = 3;
	private static final char[] TAG_BR = "<br".toCharArray();
	private static final char[] TAG_P = "<p".toCharArray();
	private static final char[] TAG_LI = "<li".toCharArray();
	private static final char[] TAG_PRE = "<pre".toCharArray();
	private static final char[] TAG_HR = "<hr".toCharArray();
	private static final char[] END_TAG_TH = "</th>".toCharArray();
	private static final char[] END_TAG_TD = "</td>".toCharArray();
	private static final char[] END_TAG_TR = "</tr>".toCharArray();
	// private static final char[] END_TAG_LI = "</li>".toCharArray();
	private static final char[] TAG_H1 = "<h1".toCharArray();
	private static final char[] TAG_H2 = "<h2".toCharArray();
	private static final char[] TAG_H3 = "<h3".toCharArray();
	private static final char[] TAG_H4 = "<h4".toCharArray();
	private static final char[] TAG_H5 = "<h5".toCharArray();
	private static final char[] TAG_H6 = "<h6".toCharArray();
	private static final char[] END_TAG_H1 = "</h1>".toCharArray();
	private static final char[] END_TAG_H2 = "</h2>".toCharArray();
	private static final char[] END_TAG_H3 = "</h3>".toCharArray();
	private static final char[] END_TAG_H4 = "</h4>".toCharArray();
	private static final char[] END_TAG_H5 = "</h5>".toCharArray();
	private static final char[] END_TAG_H6 = "</h6>".toCharArray();
	private static final Map<String, String> SPECIAL_CHARS = new HashMap<>();

	private int type;
	private String html; // original html
	private String text = null; // text!
	private int length = 0; // html length
	private boolean isPre = false; // isPre tag?

	static {
		SPECIAL_CHARS.put("&quot;", "/");
		SPECIAL_CHARS.put("&lt;", "<");
		SPECIAL_CHARS.put("&gt;", ">");
		SPECIAL_CHARS.put("&amp;", "&");
		SPECIAL_CHARS.put("&reg;", "(r)");
		SPECIAL_CHARS.put("&copy;", "(c)");
		SPECIAL_CHARS.put("&nbsp;", " ");
		SPECIAL_CHARS.put("&pound;", "?");
	}

	/**
	 * html to token
	 * 
	 * @param type
	 *            type
	 * @param data
	 *            data
	 * @param start
	 *            start
	 * @param end
	 *            end
	 * @param previousIsPre
	 *            previous is pre
	 */
	public HtmlToken(int type, char[] data, int start, int end, boolean previousIsPre) {
		this.type = type;
		this.length = end - start;
		this.html = new String(data, start, length);
		// System.out.println("[Token] html=" + html + ".");
		parseText(previousIsPre);
		// System.out.println("[Token] text=" + text + ".");
	}

	public int getLength() {
		return length;
	}

	public boolean isPreTag() {
		return isPre;
	}

	private void parseText(boolean previousIsPre) {
		if (type == TOKEN_TAG) {
			char[] cs = html.toCharArray();
			if (compareTag(TAG_BR, cs) || compareTag(TAG_P, cs))
				// text = "\r\n";
				text = "\n";
			else if (compareTag(TAG_H1, cs) || compareString(END_TAG_H1, cs))
				text = "\n####################\n";
			else if (compareTag(TAG_H2, cs) || compareString(END_TAG_H2, cs))
				text = "\n====================\n";
			else if (compareTag(TAG_H3, cs) || compareString(END_TAG_H3, cs))
				text = "\n--------------------\n";
			else if (compareTag(TAG_H4, cs) || compareString(END_TAG_H4, cs) || //
					compareTag(TAG_H5, cs) || compareString(END_TAG_H5, cs) || //
					compareTag(TAG_H6, cs) || compareString(END_TAG_H6, cs))
				text = "\n\n";
			else if (compareTag(TAG_LI, cs))
				text = "\n" + " * ";
			else if (compareTag(TAG_PRE, cs))
				isPre = true;
			else if (compareTag(TAG_HR, cs))
				text = "\n--------\n";
			else if (compareString(END_TAG_TD, cs) || compareString(END_TAG_TH, cs))
				text = "\t";
			// else if (compareString(END_TAG_TR, cs) || compareString(END_TAG_LI, cs))
			else if (compareString(END_TAG_TR, cs))
				text = "\n";
		}
		// text token:
		else if (type == TOKEN_TEXT) {
			text = toText(html, previousIsPre);
		}

	}

	public String getText() {
		return text == null ? "" : text;
	}

	private String toText(String html, final boolean isPre) {
		char[] cs = html.toCharArray();
		StringBuffer buffer = new StringBuffer(cs.length);
		int start = 0;
		boolean continueSpace = false;
		char current, next;
		for (;;) {
			if (start >= cs.length)
				break;
			current = cs[start]; // read current char
			if (start + 1 < cs.length) // and next char
				next = cs[start + 1];
			else
				next = '\0';
			if (current == ' ') {
				if (isPre || !continueSpace)
					buffer = buffer.append(' ');
				continueSpace = true;
				// continue loop:
				start++;
				continue;
			}

			// not ' ', so:
			if (current == '\r' && next == '\n') {
				if (isPre)
					buffer = buffer.append('\n');
				// continue loop:
				start += 2;
				continue;
			}

			if (current == '\n' || current == '\r') {
				if (isPre)
					buffer = buffer.append('\n');
				// continue loop:
				start++;
				continue;
			}

			// cannot continue space:
			continueSpace = false;
			if (current == '&') {
				// maybe special char:
				int length = readUtil(cs, start, ';', 10);
				if (length == (-1)) { // just '&':
					buffer = buffer.append('&');
					// continue loop:
					start++;
					continue;
				} else { // check if special character:
					String spec = new String(cs, start, length);
					String specChar = (String) SPECIAL_CHARS.get(spec);
					if (specChar != null) { // special chars!
						buffer = buffer.append(specChar);
						// continue loop:
						start += length;
						continue;
					} else { // check if like '&#1234':
						if (next == '#') { // maybe a char
							String num = new String(cs, start + 2, length - 3);
							try {
								int code = Integer.parseInt(num);
								if (code > 0 && code < 65536) { // this is a special char:
									buffer = buffer.append((char) code);
									// continue loop:
									start++;
									continue;
								}
							} catch (Exception e) {
								// do nothing
							}
							// just normal char:
							buffer = buffer.append("&#");
							// continue loop:
							start += 2;
							continue;
						} else { // just '&':
							buffer = buffer.append('&');
							// continue loop:
							start++;
							continue;
						}
					}
				}
			} else { // just a normal char!
				buffer = buffer.append(current);
				// continue loop:
				start++;
				continue;
			}
		}
		return buffer.toString();
	}

	// read from cs[start] util meet the specified char 'util',
	// or null if not found:
	private int readUtil(final char[] cs, final int start, final char util, final int maxLength) {
		int end = start + maxLength;
		if (end > cs.length) {
			end = cs.length;
		}
		for (int i = start; i < start + maxLength; i++) {
			if (cs[i] == util) {
				return i - start + 1;
			}
		}
		return (-1);
	}

	// compare standard tag "<input" with tag "<INPUT value=aa>"
	private boolean compareTag(final char[] ori_tag, char[] tag) {
		if (ori_tag.length >= tag.length)
			return false;
		for (int i = 0; i < ori_tag.length; i++) {
			if (Character.toLowerCase(tag[i]) != ori_tag[i])
				return false;
		}
		// the following char should not be a-z:
		if (tag.length > ori_tag.length) {
			char c = Character.toLowerCase(tag[ori_tag.length]);
			if (c < 'a' || c > 'z')
				return true;
			return false;
		}
		return true;
	}

	private boolean compareString(final char[] ori, char[] comp) {
		if (ori.length > comp.length)
			return false;
		for (int i = 0; i < ori.length; i++) {
			if (Character.toLowerCase(comp[i]) != ori[i])
				return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return html;
	}

}
