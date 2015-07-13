<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Map"%>
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
<title>threads</title>
</head>
<body>
	<pre>
<%
	for (Map.Entry<Thread, StackTraceElement[]> stackTrackEntry : Thread
			.getAllStackTraces().entrySet()) //
	{
		Thread thread = (Thread) stackTrackEntry.getKey();
		StackTraceElement[] stack = (StackTraceElement[]) stackTrackEntry
				.getValue();
		if (thread.equals(Thread.currentThread())) {
			continue;
		}
		out.print("\nThread: " + thread.getName() + "\n");
		for (StackTraceElement element : stack) {
			out.print("\t" + element + "\n");
		}
	}
%>
</pre>
</body>
</html>