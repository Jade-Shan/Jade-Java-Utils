package jadeutils.misc;

import jadeutils.base.ShellUtil;
import jadeutils.base.ShellUtil.SysExecResult;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class XvfbTest {

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
	public void testXvfb() throws Exception {
		if (null != osName && osName.toLowerCase().contains("linux")) {
			String script = " Xvfb :77 -screen 0 1024x768x24 & ";
			System.out.println(script);
			ShellUtil shell = new ShellUtil();
			SysExecResult result = shell.runBashScript(script, null, null);
			System.out.println(result.getOut());
		}
	}

}
