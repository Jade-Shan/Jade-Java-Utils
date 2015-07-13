package jadeutils.web.http.upload;

public interface UploadToolsCfg {

	/**
	 * 判断字段是否是要处理的字段
	 * 
	 * @param fieldName
	 *            表单字段名
	 * @return 是不是想要处理的字段
	 */
	public boolean isMatchFieldName(String fieldName);
}
