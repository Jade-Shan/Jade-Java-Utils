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
<script src="<%=basePath%>scripts/3rd/jquery-1.8.2.min.js"></script>
<link  href="<%=basePath%>scripts/3rd/AjaxFileUploader/ajaxfileupload.css" type="text/css" rel="stylesheet" />
<script src="<%=basePath%>scripts/3rd/AjaxFileUploader/ajaxfileupload.js" type="text/javascript"></script>
<script src="<%=basePath%>scripts/comm/ajaxupload.js"  type="text/javascript"></script>
<script type="text/javascript">
function uploadSuccessFunc(data){
	var px = '<%=basePath%>upload/images/' + data.relativePath + data.avatarUUID;
	$('#avatarH').attr('src', px+"-H.jpg");
	$('#avatarL').attr('src', px+"-L.jpg");
	$('#avatarM').attr('src', px+"-M.jpg");
	$('#avatarS').attr('src', px+"-S.jpg");
};
function uploadErrorFunc(data){
	alert(data.err);
};
function uploadAvatar(){
	uploadFile({ iptId : "upFileIpt", funcName : "userAvatar", isRename : "true" }, 
		defaultUploadAnimation, uploadSuccessFunc, uploadErrorFunc);
};
</script>
</head>
<body>
		<img id="loading" src="<%=basePath%>scripts/3rd/AjaxFileUploader/loading.gif" style="display:none;" />

		<p>upload file:</p>
		<input  class="input" id="upFileIpt" type="file" size="45" name="filedata" />
		<button class="button-normal" id="buttonUpload" onclick="uploadAvatar();">Upload file</button>

		<img id="avatarH" src="http://img01.taobaocdn.com/tps/i1/T1P1N3XCNbXXbf682X-140-35.png" />
		<img id="avatarL" src="http://img01.taobaocdn.com/tps/i1/T1P1N3XCNbXXbf682X-140-35.png" />
		<img id="avatarM" src="http://img01.taobaocdn.com/tps/i1/T1P1N3XCNbXXbf682X-140-35.png" />
		<img id="avatarS" src="http://img01.taobaocdn.com/tps/i1/T1P1N3XCNbXXbf682X-140-35.png" />
</body>
</html>