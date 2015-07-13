package jadeutils.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.DirectoryWalker;

public class FileOperater {

	/**
	 * 保存文件
	 * 
	 * @param path
	 *            路径
	 * @param fileName
	 *            文件名
	 * @param file
	 *            文件内容
	 * @throws Exception
	 * @throws Exception
	 */
	public static void writeFile(String path, String fileName, File file)
			throws IOException {
		/* 如果目录不存在则创建目录 */
		File fileDir = new File(path);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		/* 文件写入服务器 */
		writeToStream(path + fileName, new FileInputStream(file));
	}

	/**
	 * 保存文件
	 * 
	 * @param path
	 *            路径
	 * @param fileName
	 *            文件名
	 * @param inputStream
	 *            输入流
	 * @throws IOException
	 */
	public static void writeFile(String path, String fileName,
			InputStream inputStream) throws IOException {
		/* 如果目录不存在则创建目录 */
		File fileDir = new File(path);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		/* 文件写入服务器 */
		writeToStream(path + fileName, inputStream);
	}

	/**
	 * 写入到磁盘
	 * 
	 * @param fileName
	 * @param fileData
	 * @throws IOException
	 */
	private static void writeToStream(String fileName, InputStream inputStream)
			throws IOException {
		File file = new File(fileName);
		File parentDir = file.getParentFile();
		if (parentDir.exists() && parentDir.isFile()) {
			parentDir.delete();
			parentDir.mkdirs();
		} else if (!parentDir.exists()) {
			parentDir.mkdirs();
		}
		// 开启IO流保存上传的文件
		FileOutputStream fos = new FileOutputStream(file);
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inputStream.read(buffer)) > 0) {
			fos.write(buffer, 0, len);
		}
		fos.close();
		inputStream.close();
	}

	/**
	 * 删除单个文件
	 * 
	 * @param fileName
	 *            被删除文件的文件名
	 */
	public static void deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.isFile() && file.exists()) {
			file.delete();
		}
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param dir
	 *            被删除目录的文件路径
	 */
	public void deleteDirectory(String dir) throws IOException {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir);
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			// 如果dir对应的文件不存在，或者不是一个目录，则退出
		} else {
			// 删除文件夹下的所有文件(包括子目录)
			File[] files = dirFile.listFiles();
			if (null != files) {
				for (File file : files) {
					if (file.isFile()) {
						deleteFile(file.getAbsolutePath());
					} else {
						deleteDirectory(file.getAbsolutePath());
					}
				}
			}
		}
	}

	/**
	 * 抽象出对文件操作的接口
	 * 
	 * @author jade
	 *
	 */
	public interface FileProcesser {

		/**
		 * 对一个文件进行一系列的操作
		 * 
		 * @param file
		 */
		public void process(File file) throws IOException;

	}

	/**
	 * 遍历目录并处理所有文件的工具
	 * 
	 * @author jade
	 *
	 */
	private class MyDirectoryWalker extends DirectoryWalker<Object> {
		FileFilter filter = null;
		FileProcesser processer = null;

		public MyDirectoryWalker(FileFilter filter, FileProcesser processer) {
			super();
			this.filter = filter;
			this.processer = processer;
		}

		@Override
		protected void handleFile(File file, int depth,
				Collection<Object> results) throws IOException {
			if (filter.accept(file))
				processer.process(file);
		}

		public void start(File rootPath) throws IOException {
			this.walk(rootPath, null);
		}
	}

	private void startWalker(File rootPath, FileFilter filter,
			FileProcesser processer) throws IOException {
		MyDirectoryWalker w = new MyDirectoryWalker(filter, processer);
		w.start(rootPath);
	}

	/**
	 * 处理所有的文件
	 * 
	 * @param rootPath
	 *            开始的根目录
	 * @param filter
	 *            过滤器，只处理过滤器符合的文件
	 * @param processer
	 *            处理器，指定了对文件的操作
	 * @throws IOException
	 */
	public static void processEachFile(File rootPath, FileFilter filter,
			FileProcesser processer) throws IOException {
		(new FileOperater()).startWalker(rootPath, filter, processer);
	}

	public static byte[] readStream(InputStream is) throws IOException {
		byte[] result = null;
		byte[] buffer = new byte[1024];
		int size = is.read(buffer);
		while (size > 0) {
			if (result == null) {
				result = new byte[size];
				System.arraycopy(buffer, 0, result, 0, size);
			} else {
				byte[] tmp = new byte[result.length + size];
				System.arraycopy(result, 0, tmp, 0, result.length);
				System.arraycopy(buffer, 0, tmp, result.length, size);
				result = tmp;
			}
			size = is.read(buffer);
		}
		return result;
	}

	public static byte[] gZip(byte[] data) {
		byte[] b = null;

		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			byte[] buf = new byte[1024];
			int num = -1;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			GZIPOutputStream gzipos = new GZIPOutputStream(baos);
			while ((num = bis.read(buf, 0, buf.length)) != -1) {
				gzipos.write(buf, 0, num);
			}
			gzipos.finish();
			gzipos.flush();
			baos.flush();
			b = baos.toByteArray();

			gzipos.close();
			baos.close();
			bis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}

	public static byte[] unGZip(byte[] data) {
		byte[] b = null;

		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			GZIPInputStream gzip = new GZIPInputStream(bis);
			byte[] buf = new byte[1024];
			int num = -1;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((num = gzip.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, num);
			}
			b = baos.toByteArray();
			baos.flush();
			baos.close();
			gzip.close();
			bis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}
}
