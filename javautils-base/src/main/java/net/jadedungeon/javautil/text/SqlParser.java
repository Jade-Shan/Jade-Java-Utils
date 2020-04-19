package net.jadedungeon.javautil.text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class SqlParser extends BaseParser {

	public SqlParser(String str) {
		super(str);
	}

	@Override
	protected String getNodeSpliter() {
		return " \t\n\r().,;";
	}

	@Override
	protected Map<Character, Character> initStringMark() {
		Map<Character, Character> result = new HashMap<Character, Character>();
		result.put('\'', '\'');
		result.put('`', '`');
		return result;
	}

	@Override
	protected String getEscapeCharStr() {
		return "\\";
	}

	public static void main(String[] args) {
		try {
			FileReader fr = new FileReader("test.sql");
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while (null != line) {
				System.out.println(line);
				SqlParser sp = new SqlParser(line);
				sp.toNodes();
				System.out.println(sp.nodes.toString());
				line = br.readLine();
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}
}
