<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.concurrent.ConcurrentSkipListMap"%>
<%
	Map<String, String> remoteMap = (Map<String, String>) application
			.getAttribute("REMOTE_MAP");
	if (remoteMap == null) {
		remoteMap = new ConcurrentSkipListMap<String, String>();
		application.setAttribute("REMOTE_MAP", remoteMap);
	}
	String host = request.getParameter("host");
	if (host != null && !host.isEmpty()) {
		remoteMap.put(host, request.getRemoteAddr());
	} else {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> e : remoteMap.entrySet()) {
			sb.append("{\"host\":\"").append(e.getKey());
			sb.append("\", \"ip\":\"");
			sb.append(e.getValue()).append("\"},");
		}
		int len = sb.length();
		out.print("[" + (len == 0 ? sb : sb.substring(0, len - 1))
				+ "]");
	}
%>