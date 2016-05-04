<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<title>upload</title>
</head>
<body>
	<!-- enctype 默认是 application/x-www-form-urlencoded -->
	<form method="post" action="fileUploadServlet" enctype="multipart/form-data">
		用户名：<input type="text" name="usename"><br />
		上传文件：<input type="file" name="file1"><br />
		上传文件： <input type="file" name="file2"><br />
		<input type="submit" value="提交" /> 
	</form>
</body>
</html>