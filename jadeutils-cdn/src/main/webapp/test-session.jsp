<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test Session Page</title>
</head>
<body>
This is A : ${pageContext.session.id} <br />

Headers: <br />
${headerValues}<br />

cookie: <br />
${cookie}<br />

params:<br />
value aaa is: ${param.hotelid}<br />


<form action='<c:url value="/user.html" />'>
	<input type="text" name="username" value="${cdnjadeutils}" />
</form>

<table>
<tr><th>Value</th><th>Square</th></tr>
<c:forEach var="x" begin="0" end="10" step="2" >
<tr><td><c:out value="${x }" /></td><td><c:out value="${x * x }" /></td></tr>
</c:forEach>
</table>


</body>
</html>