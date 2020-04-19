package net.jadedungeon.javautil.web.http.upload;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import net.jadedungeon.javautil.base.FileOperater;

public class UploadTools {
	public static final String DATE_FORMAT_YEAR = "yyyy";
	public static final String DATE_FORMAT_MON_DAY = "MMdd";

	public static final String IS_RENAME = "isRename";
	public static final String FUNC_NAME = "funcName";
	public static final String RELATIVE_PATH = "relativePath";
	public static final String FILE_NAME = "realFileName";

	/*
	 * 上传单个文件
	 * 
	 * @param req
	 *            http请求
	 * @param characterEncoding
	 *            编码
	 * @param uploadFilePath
	 *            上传路径
	 * @param tmpFilePath
	 *            临时文件路径
	 * @param singleFileSize
	 *            单个文件大小
	 * @param isCached
	 *            是否使用缓存
	 * @param fileName
	 *            文件所在的字段名
	 * @throws Exception
	 */
	public void uploadSingleFile(HttpServletRequest req,
			String characterEncoding, String uploadFilePath,
			String tmpFilePath, int singleFileSize, boolean isCached,
			final String fileName) throws Exception {
		this.uploadManyFiles(req, characterEncoding, uploadFilePath,
				tmpFilePath, singleFileSize, singleFileSize, isCached,
				new UploadToolsCfg() {
					@Override
					public boolean isMatchFieldName(String fieldName) {
						return fieldName.equals(fileName);
					}
				});
	}

	/*
	 * 上传多个文件
	 * 
	 * @param req
	 *            http请求
	 * @param characterEncoding
	 *            编码
	 * @param uploadFilePath
	 *            上传路径
	 * @param tmpFilePath
	 *            临时文件路径
	 * @param singleFileSize
	 *            单个文件大小
	 * @param allFileSize
	 *            所有文件大小
	 * @param isCached
	 *            是否使用缓存
	 * @param cfg
	 *            配置类
	 * @throws Exception
	 */
	public void uploadManyFiles(HttpServletRequest req,
			String characterEncoding, String uploadFilePath,
			String tmpFilePath, int singleFileSize, int allFileSize,
			boolean isCached, UploadToolsCfg cfg) throws Exception//
	{
		try {
			req.setCharacterEncoding(characterEncoding);
			// 获得磁盘文件条目工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			if (isCached) {
				// 如果没以下两行设置的话，上传大的 文件 会占用 很多内存，
				// 设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同
				// 原理 它是先存到 暂时存储室，然后在真正写到 对应目录的硬盘上，
				// 按理来说 当上传一个文件时，其实是上传了两份，第一个是以 .tem
				// 格式的 然后再将其真正写到 对应目录的硬盘上
				File tmpPath = new File(tmpFilePath);
				if (tmpPath.exists() && tmpPath.isFile()) {
					tmpPath.delete();
					tmpPath.mkdirs();
				} else if (!tmpPath.exists()) {
					tmpPath.mkdirs();
				}
				factory.setRepository(tmpPath);
				// 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
				if (allFileSize > 0) {
					factory.setSizeThreshold(allFileSize);
				} else {
					factory.setSizeThreshold(500 * 1024);
				}
			}

			// 高水平的API文件上传处理
			ServletFileUpload uploader = new ServletFileUpload(factory);
			// 将页面请求传递信息最大值设置为50M
			uploader.setSizeMax(allFileSize);
			// 将单个上传文件信息最大值设置为6M
			uploader.setSizeMax(singleFileSize);

			List<FileItem> list = (List<FileItem>) uploader.parseRequest(req);
			boolean isRename = true;
			String funcName = "others";
			/* 遍历所有表单文字成员，全都加到请求的attribute里 */
			for (FileItem item : list) {
				if (item.isFormField()) {
					String fieldName = item.getFieldName();// 表单字段名
					String fieldValue = item.getString();

					if (fieldName.equals(IS_RENAME)) {// 查看请求里要不要改名
						if (fieldValue != null
								&& fieldValue.equalsIgnoreCase("false")) {
							isRename = false;
						}
					} else if (FUNC_NAME.equals(fieldName)
							&& null != fieldValue
							&& !"".equals(fieldValue.trim())) {// 查看请求里的功能名
						funcName = fieldValue;
					}
					req.setAttribute(fieldName, fieldValue);
				}
			}
			/* 遍历所有二进制流，执行保存操作 */
			for (FileItem item : list) {
				String fieldName = item.getFieldName();// 表单字段名
				if (!item.isFormField() && cfg.isMatchFieldName(fieldName)) {
					this.doUpload(req, item, //
							uploadFilePath, funcName, isRename);
				}
			}
		} catch (SizeLimitExceededException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	/*
	 * 执行上传文件保存操作
	 * 
	 * @param req
	 * @param item
	 * @param uploadFilePath
	 * @param funcName
	 * @throws Exception
	 */
	private void doUpload(HttpServletRequest req, FileItem item,
			String uploadFilePath, String funcName, boolean isRename)
			throws Exception {

		/* 组成硬盘上的绝对路径 */
		String relativePath = this.getRelativePath(funcName);
		req.setAttribute(RELATIVE_PATH, relativePath);

		/* 生成文件名 */
		String fieldName = item.getFieldName();// 表单字段名
		String filename = this.getUploadFileName(item.getName(), isRename);
		// 注意这里的key是写死的，如果有多个文件一次上传的话会被覆盖掉
		req.setAttribute(FILE_NAME, filename);
		// TODO: 这里的不是写死的，但还没有想好怎么拿……
		req.setAttribute(fieldName, filename);

		// 这是第三方提供的写入磁盘方法，这里先注释掉不用
		if (Integer.parseInt("2") > 6666) {// 这里的代码永远执行不到
			this.autoWriteFile(item, uploadFilePath, filename);
		}
		// 自己定义的写入磁盘的方法
		FileOperater.writeFile(uploadFilePath + relativePath, filename,
				item.getInputStream());
	}

	/*
	 * 由第三提供的方法自动写入文件
	 * 
	 * @param item
	 * @param filename
	 * @throws Exception
	 */
	private void autoWriteFile(FileItem item, String path, String filename)
			throws Exception {
		item.write(new File(path, filename));
	}

	/*
	 * 生成相对地址
	 * 
	 * @param funcName
	 *            对应的功能名
	 * @return
	 */
	private String getRelativePath(String funcName) {
		Date now = new Date();
		StringBuffer sb = new StringBuffer(funcName);
		sb.append("/");
		sb.append((new SimpleDateFormat(DATE_FORMAT_YEAR)).format(now))//
				.append("/");
		sb.append((new SimpleDateFormat(DATE_FORMAT_MON_DAY)).format(now))//
				.append("/");
		return sb.toString();
	}

	/*
	 * 取得上传的文件名
	 * 
	 * @return
	 */
	private String getUploadFileName(String fieldValue, boolean isRename) {
		String fileName = fieldValue;
		String[] tmp = fileName.split("/");
		int tailIdx = tmp.length - 1;
		fileName = tmp[tailIdx];
		tmp = fileName.split("\\\\");
		tailIdx = tmp.length - 1;
		fileName = tmp[tailIdx];
		if (isRename) { // 如果要重新生成一个文件名
			StringBuffer sb = new StringBuffer(UUID.randomUUID().toString());
			String[] nn = fileName.split("\\.");
			if (null != nn && nn.length > 0) {
				sb.append(".").append(nn[nn.length - 1]);
			}
			fileName = sb.toString();
		}
		return fileName;
	}
}
