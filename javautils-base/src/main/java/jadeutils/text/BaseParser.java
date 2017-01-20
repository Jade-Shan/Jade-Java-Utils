package jadeutils.text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BaseParser implements Parser {

	private String str = null; /* ori string */

	public BaseParser(String str) {
		this.str = str;
		this.initNodeSpliterSet();
		this.initStringMark();
	}

	/* 分隔字符 */
	private Set<Character> nodeSpliterSet = null;

	abstract protected String getNodeSpliter();

	private void initNodeSpliterSet() {
		this.nodeSpliterSet = new HashSet<Character>();
		String s = this.getNodeSpliter();
		for (int i = 0; i < s.length(); i++) {
			this.nodeSpliterSet.add(s.charAt(i));
		}
		this.stringMark = this.initStringMark();
		this.initExcapeCharSet();
	}

	/* 字符串的开始与结束字符 */
	private Map<Character, Character> stringMark;

	abstract protected Map<Character, Character> initStringMark();

	/* 转义字符 */
	private Set<Character> escapeCharSet = null;

	abstract protected String getEscapeCharStr();

	private void initExcapeCharSet() {
		this.escapeCharSet = new HashSet<Character>();
		String s = this.getEscapeCharStr();
		for (int i = 0; i < s.length(); i++) {
			this.escapeCharSet.add(s.charAt(i));
		}
	}

	/* 拆出字符串，注意引号的转义 */
	public List<String> nodes = null;
	private StringBuffer sb = new StringBuffer();

	public void toNodes() {
		this.nodes = new ArrayList<String>();
		char p = ' ';
		char c = ' ';
		Character strOpen = null;
		if (null != str && str.length() > 0) {
			for (int i = 0; i < str.length(); i++) {
				p = c;
				c = str.charAt(i);
				if (null != strOpen) {
					if ('\\' == p) {
						if (isStringClose(strOpen, c) || isStringOpen(c)) {
							sb.append(p).append(c);
						} else {
							sb.append(c);
						}
					} else if (isStringClose(strOpen, c)) {
						strOpen = null;
						this.pushBuffer();
					} else {
						sb.append(c);
					}
				} else {
					if (isBlankChar(c)) {
						this.pushBuffer();
					} else if (isStringOpen(c)) {
						strOpen = c;
						this.pushBuffer();
					} else {
						sb.append(c);
					}
				}
			}
		}
	}

	private boolean isStringOpen(Character c) {
		return this.stringMark.values().contains(c);
	}

	private boolean isStringClose(Character strOpen, Character c) {
		return this.stringMark.keySet().contains(c) ? strOpen
				.equals(this.stringMark.get(c)) : false;
	}

	private boolean isBlankChar(Character c) {
		return this.nodeSpliterSet.contains(c);
	}

	/**
	 * 把缓存中的内容加到链表中去
	 */
	private void pushBuffer() {
		if (sb.length() > 0) {
			String s = sb.toString();
			nodes.add(s);
			sb = new StringBuffer();
		}
	}
}
