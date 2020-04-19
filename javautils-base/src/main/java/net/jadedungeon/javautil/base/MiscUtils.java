package net.jadedungeon.javautil.base;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class MiscUtils {

	public static String getStackTrace(Throwable aThrowable) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		aThrowable.printStackTrace(printWriter);
		return result.toString();
	}

}
