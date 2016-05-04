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
	用户名：${requestScope.usename } <br/>  
	<!-- 把上传的图片显示出来 -->  
	文件：${requestScope.file1 }<br/>  
	<img alt="go" src="upload/<%=(String)request.getAttribute("file1")%> " />  
	文件：${requestScope.file2 }<br/>  
	<img alt="go" src="upload/<%=(String)request.getAttribute("file2")%> " />  
</body>
</html>