package jadeutils.text;

import jadeutils.base.FileOperater;
import jadeutils.base.FileOperater.FileProcesser;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class CharCodeUtil {

	/*
	 * 清除一个目录下所有文件的BOM信息
	 * 
	 * @param rootPath
	 *            开始的根目录
	 * @param filter
	 *            过滤器，指定哪些文件是要进行操作的
	 * @throws IOException
	 */
	public static void cleanFileUtf8BomInfo(File rootPath, FileFilter filter)
			throws IOException {
		FileOperater.processEachFile(rootPath, filter, new FileProcesser() {

			@Override
			public void process(File file) throws IOException {
				byte[] bs = FileUtils.readFileToByteArray(file);
				/* 如果是UTF-8 BOM去掉前三个字节 */
				if (bs[0] == -17 && bs[1] == -69 && bs[2] == -65) {
					byte[] nbs = new byte[bs.length - 3];
					System.arraycopy(bs, 3, nbs, 0, nbs.length);
					FileUtils.writeByteArrayToFile(file, nbs);
				}
			}
		});
	}

}
