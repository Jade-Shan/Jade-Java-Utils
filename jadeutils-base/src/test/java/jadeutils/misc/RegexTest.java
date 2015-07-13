package jadeutils.misc;

import static org.junit.Assert.assertTrue;
import jadeutils.base.DateCaculater;
import jadeutils.base.ShellUtil;
import jadeutils.base.ShellUtil.DefaultStreamHandler;
//import jadeutils.base.ShellUtil.DefaultStreamHandler;
import jadeutils.base.ShellUtil.SysExecResult;
import jadeutils.text.TextTempletRanderTool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RegexTest {

	private String scpTemplet = "/usr/bin/expect -c \"\n" + "set timeout 15\n"
			+ "spawn scp ${file.src} ${file.target}\n"
			+ "expect \"password:\" {exp_send \"${host.user.password}\"\\r;}\n"
			+ "interact\"";

	private Map<String, String> scpParams = new HashMap<String, String>();

	private String osName = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.osName = System.getProperty("os.name");
		this.scpParams.put("host.user.password", "passwd");
		this.scpParams.put("file.src", "/home/jade/tmp/test/test.01.data");
		this.scpParams.put("file.target", "morgan@nginx.local-vm:~/tmp/");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Pattern p = Pattern.compile("[0-9A-Za-z]*");
		assertTrue(p.matcher("").matches());
	}

	@Test
	public void testTempleteClean() {
		String str = "${file.src} ${file.target} ${file.target} ${file.aaa} ${file.target} ${file.target}";
		List<String> recs = TextTempletRanderTool.getUnRanderedParams(str);
		Assert.assertEquals("[${file.aaa}, ${file.target}, ${file.src}]",
				recs.toString());
	}

	@Test
	public void testDate() {
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String t1 = "20080509";
		String t2 = "20090513";
		try {
			Date d1 = format.parse(t1);
			Date d2 = format.parse(t2);
			System.out.println(DateCaculater.getBetweenDays(d1, d2));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testXvfb() throws Exception {
		if (null != osName && osName.toLowerCase().contains("linux")) {
			String script = " Xvfb :77 -screen 0 1024x768x24 & ";
			System.out.println(script);
			ShellUtil shell = new ShellUtil();
			SysExecResult result = shell.runBashScript(script, null, null);
			System.out.println(result.getOut());
		}
	}

	@Test
	public void testTempRander() throws Exception {
		if (null != osName && osName.toLowerCase().contains("linux")) {
			String script = TextTempletRanderTool.render(this.scpTemplet,
					scpParams);
			System.out.println(script);
			script = "ls -al";
			ShellUtil shell = new ShellUtil();
			SysExecResult result = shell.runBashScript(script, null, null);
			System.out.println(result.getOut());
		}
	}

	@Test
	public void testWinCmd() throws Exception {
		if (null != osName && osName.toLowerCase().contains("windows")) {
			ShellUtil shell = new ShellUtil();
			// 定义对调用结果的输出流的处理方法
			// 这里是把输出流转为String
			DefaultStreamHandler hdl = shell.new DefaultStreamHandler("GB2312");
			shell.setStdoutCallback(hdl);
			shell.setStderrCallback(hdl);
			SysExecResult result = shell.runWinCmd("dir", null, null);
			System.out.println(result.getOut());
		}
	}
}
