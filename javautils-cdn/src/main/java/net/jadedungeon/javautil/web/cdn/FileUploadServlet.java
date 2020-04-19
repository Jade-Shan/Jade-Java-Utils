/**
 * 
 */
package net.jadedungeon.javautil.web.cdn;

import net.jadedungeon.javautil.image.ImageUtilsWrapper;
import net.jadedungeon.javautil.web.http.upload.UploadTools;
import net.jadedungeon.javautil.web.http.upload.UploadToolsCfg;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;

/**
 * @author SHAN013
 * 
 *         文件上传<br/>
 *         具体步骤： <br/>
 *         1）获得磁盘文件条目工厂 DiskFileItemFactory 要导包 <br/>
 *         2） 利用 req 获取 真实路径 ，供临时文件存储，和 最终文件存储 ，这两个存储位置可不同，也可相同 <br/>
 *         3）对 DiskFileItemFactory 对象设置一些 属性 <br/>
 *         4）高水平的API文件上传处理 ServletFileUpload uploader = new
 *         ServletFileUpload(factory); <br/>
 *         目的是调用 parseRequest（req）方法 获得 FileItem 集合list ， <br/>
 * 
 *         5）在 FileItem 对象中 获取信息， 遍历， 判断 表单提交过来的信息 是否是 普通文本信息 另做处理 <br/>
 *         6） <br/>
 *         第一种. 用第三方 提供的 item.write( new File(path,filename) ); 直接写到磁盘上 <br/>
 *         第二种. 手动处理 <br/>
 */
public class FileUploadServlet extends HttpServlet {

	private static final String characterEncoding = "utf-8";
	private String uploadPath;
	private String uploadPathTmp;
	private String imageBasePath;

	@Override
	public void init() throws ServletException {
		super.init();

		Properties uploadProp = new Properties();
		try {
			uploadProp.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("upload.properties"));
			this.uploadPath = uploadProp.getProperty("upload.base");
			this.uploadPathTmp = uploadProp.getProperty("upload.tmp");
			this.imageBasePath = uploadProp.getProperty("upload.img");
		} catch (IOException e) {
			e.printStackTrace();
			throw new ServletException(
					"init FileUploadServlet error when loading upload.properties");
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String method = req.getParameter("func");
		method = method == null ? "" : method;
		if ("ajaxUploadFile".equals(method)) {
			this.ajaxUploadFile(req, resp);
		} else if ("ajaxUploadImage".equals(method)) {
			this.ajaxUploadImage(req, resp);
		} else if ("ajaxUploadAvatar".equals(method)) {
			this.ajaxUploadAvatar(req, resp);
		} else {
			try {
				this.upload(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected void ajaxUploadFile(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		try {
			this.upload(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void ajaxUploadImage(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		try {
			this.upload(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void ajaxUploadAvatar(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		int singleFileSize = 66 * 500 * 1024;
		boolean isCached = true;

		String errorMessage = null;
		String relativePath = "";
		String fileName = "";
		String avatarUUID = "";
		try {
			/* 上传头像 */
			UploadTools uploader = new UploadTools();
			uploader.uploadSingleFile(req, characterEncoding, this.uploadPath,
					this.uploadPathTmp, singleFileSize, isCached, "filedata");
			/* 缩小头像 */
			relativePath = (String) req.getAttribute(UploadTools.RELATIVE_PATH);
			fileName = (String) req.getAttribute(UploadTools.FILE_NAME);
			String[] arr = fileName.split("\\.");
			avatarUUID = arr[0];
			this.createResizeAvatar(this.uploadPath + relativePath, fileName,
					avatarUUID);
		} catch (SizeLimitExceededException e) {
			errorMessage = "上传文件太大！超过200K";
		} catch (Exception e) {
			errorMessage = "文件上传错误，请确认文件为jpg或png格式！";
		}
		if (null == errorMessage) {
			errorMessage = "";
		}
		StringBuffer msg = new StringBuffer();
		msg = msg.append("{\"err\":\"").append(errorMessage)//
				.append("\",\"baseURL\":\"").append(this.imageBasePath)//
				.append("\",\"relativePath\":\"").append(relativePath)//
				.append("\",\"avatarUUID\":\"").append(avatarUUID)//
				.append("\"}");
		this.printInfoBack(resp, msg.toString());
	}

	private void upload(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		int singleFileSize = 66 * 500 * 1024;
		int allFileSize = 2 * 1024 * 1024;
		boolean isCached = true;

		UploadTools uploader = new UploadTools();
		// uploader.uploadSingleFile(req, characterEncoding, uploadFilePath,
		// tmpFilePath, singleFileSize, isCached, "file1");
		uploader.uploadManyFiles(req, characterEncoding, this.uploadPath,
				this.uploadPathTmp, singleFileSize, allFileSize, isCached,
				new UploadToolsCfg() {
					@Override
					public boolean isMatchFieldName(String fieldName) {
						return true;
					}
				});
		req.getRequestDispatcher("/file-upload/upload-result.jsp")//
				.forward(req, resp);
	}

	private void createResizeAvatar(String realPath, String fileName,
			String uuid) throws IOException {
		String oriFile = realPath + fileName;
		String avFile = realPath + uuid;
		ImageUtilsWrapper.scale(oriFile,//
				avFile + "-H.jpg", 350, 350, false);
		ImageUtilsWrapper.scale(oriFile,//
				avFile + "-L.jpg", 150, 150, false);
		ImageUtilsWrapper.scale(oriFile,//
				avFile + "-M.jpg", 80, 80, false);
		ImageUtilsWrapper.scale(oriFile,//
				avFile + "-S.jpg", 54, 54, false);
	}

	private void printInfoBack(HttpServletResponse resp, String msg) {
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = null;
		try {
			out = resp.getWriter();
			out.println(msg);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != out)
				try {
					out.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
		}
	}

}
