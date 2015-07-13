
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test Session Page</title>
</head>
<body>
This is A : ${pageContext.session.id} <br/>

Headers: <br/>
${headerValues}<br/>

cookie: <br/>
${cookie}<br/>

params:<br/>
value aaa is: ${param.hotelid}

</body>
</html>