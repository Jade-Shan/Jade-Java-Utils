package jadeutils.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.management.RuntimeErrorException;

public class ShellUtil {
	private StreamHandler outHandler;
	private StreamHandler errHandler;

	public ShellUtil() {
		DefaultStreamHandler s = new DefaultStreamHandler();
		outHandler = s;
		errHandler = s;
	}

	public ShellUtil(StreamHandler out, StreamHandler err) {
		outHandler = out;
		errHandler = err;
	}

	public void setStdoutCallback(StreamHandler stdoutCallback) {
		this.outHandler = stdoutCallback;
	}

	public void setStderrCallback(StreamHandler stderrCallback) {
		this.errHandler = stderrCallback;
	}

	private SysExecResult callSystem(String[] script, String[] envp, File dir)
			throws IOException, InterruptedException //
	{
		SysExecResult result = new SysExecResult();
		Process ps = Runtime.getRuntime().exec(script, envp, dir);
		/* 警告！！！ */
		/* 输出流会阻塞会阻塞程序执行！ */
		/* 一定要读完了输出流，才能保证程序执行完毕 */
		result.setOut(outHandler.trans(ps.getInputStream()));
		result.setErr(errHandler.trans(ps.getErrorStream()));
		/* 等待程序执行完毕，取得退出代码 */
		result.setExitValue(ps.waitFor());
		return result;
	}

	/*
	 * 执行bash脚本
	 * 
	 * @param script 脚本
	 * 
	 * @param envp 环境变量
	 * 
	 * @param dir 路径
	 * 
	 * @return 输出流
	 * 
	 * @throws IOException
	 * 
	 * @throws InterruptedException
	 */
	public SysExecResult runBashScript(String script, String[] envp, File dir)
			throws IOException, InterruptedException //
	{
		// 注:如果sh中含有awk,一定要按new String[]{"/bin/sh","-c",shStr}写,才可以获得流
		return callSystem(new String[] { "/bin/bash", "-c", script }, envp, dir);
	}

	/*
	 * 执行bash脚本并弹出窗口
	 * 
	 * @param script 脚本
	 * 
	 * @param envp 环境变量
	 * 
	 * @param dir 路径
	 * 
	 * @return 输出流
	 * 
	 * @throws IOException
	 * 
	 * @throws InterruptedException
	 */
	public SysExecResult runBashScriptPopWin(String script, String[] envp,
			File dir, String termParam) throws IOException,
			InterruptedException //
	{
		termParam = termParam == null ? "xterm -e " : termParam;
		// 注:如果sh中含有awk,一定要按new String[]{"/bin/sh","-c",shStr}写,才可以获得流
		return callSystem(
				new String[] { "/bin/bash", "-c", termParam + script }, envp,
				dir);
	}

	/*
	 * 执行windows cmd脚本
	 * 
	 * @param script 脚本
	 * 
	 * @param envp 环境变量
	 * 
	 * @param dir 路径
	 * 
	 * @return 输出流
	 * 
	 * @throws IOException
	 * 
	 * @throws InterruptedException
	 */
	public SysExecResult runWinCmd(String script, String[] envp, File dir)
			throws IOException, InterruptedException //
	{
		// 注:如果sh中含有awk,一定要按new String[]{"/bin/sh","-c",shStr}写,才可以获得流
		return callSystem(new String[] { "cmd", "/C", script }, envp, dir);
	}

	/*
	 * 执行windows cmd脚本，并弹出窗口
	 * 
	 * @param script 脚本
	 * 
	 * @param envp 环境变量
	 * 
	 * @param dir 路径
	 * 
	 * @return 输出流
	 * 
	 * @throws IOException
	 * 
	 * @throws InterruptedException
	 */
	public SysExecResult runWinCmdPopWin(String script, String[] envp, File dir)
			throws IOException, InterruptedException //
	{
		// 注:如果sh中含有awk,一定要按new String[]{"/bin/sh","-c",shStr}写,才可以获得流
		return callSystem(new String[] { "cmd", "/C", "start " + script },
				envp, dir);
	}

	/*
	 * 以当前系统编码把输入流转为文本
	 * 
	 * @param inStream
	 * 
	 * @return
	 * 
	 * @throws IOException
	 */
	public String transInStream2String(InputStream inStream) throws IOException {
		Charset charset = Charset.forName(System.getProperty("file.encoding"));
		return transInStream2String(inStream, charset);
	}

	/*
	 * 按指定编码把输入流转为文本
	 * 
	 * @param inStream
	 * 
	 * @param charset
	 * 
	 * @return
	 * 
	 * @throws IOException
	 */
	public static String transInStream2String(InputStream stream,
			Charset charset) throws IOException //
	{
		String result = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(stream,
				charset));
		StringBuffer sb = new StringBuffer();
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line).append("\n");
		}
		result = sb.toString();
		return result;
	}

	/*
	 * 定义如何把标准输出和标准错误转为客户需要的结果。</br> 因为输出流不读取完可能会阻塞系统调用。所以对输出流的处理操作要在系统调用返回前先完成。
	 * 
	 * @author qwshan
	 */
	public interface StreamHandler {

		/*
		 * 处理输出流
		 * 
		 * @param stdout
		 * 
		 * @return
		 */
		public Object trans(InputStream stream);
	};

	/*
	 * 即时地把标准
	 * 
	 * @author qwshan
	 */
	public class DefaultStreamHandler implements StreamHandler {
		private Charset charset = null;

		public DefaultStreamHandler() {
			charset = Charset.forName(System.getProperty("file.encoding"));
		}

		public DefaultStreamHandler(String encoding) {
			charset = Charset.forName(encoding);
		}

		@Override
		public Object trans(InputStream stream) throws RuntimeException {
			String result = null;
			try {
				result = transInStream2String(stream, charset);
			} catch (IOException e) {
				throw new RuntimeErrorException(new Error(e));
			}
			return result;
		}

	}

	/*
	 * 返回结果
	 * 
	 * @author qwshan
	 */
	public class SysExecResult {

		private int exitValue = 0;
		private Object out;
		private Object err;

		public int getExitValue() {
			return exitValue;
		}

		public void setExitValue(int exitValue) {
			this.exitValue = exitValue;
		}

		public Object getOut() {
			return out;
		}

		public void setOut(Object stdout) {
			this.out = stdout;
		}

		public Object getErr() {
			return err;
		}

		public void setErr(Object stderr) {
			this.err = stderr;
		}

	}

}
