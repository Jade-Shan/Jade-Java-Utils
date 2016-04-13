package jadeutils.net.http;

import jadeutils.encryption.ByteArrayQueue;
import jadeutils.net.SocketPool;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class HttpPool extends SocketPool {
	private String path_, host, proxyAuth;

	private HttpPool(HttpParam param, int timeout) {
		super(param.sf, param.sa, timeout);
		path_ = param.path;
		host = param.host;
		proxyAuth = param.proxyAuth;
	}

	public HttpPool(String url, int timeout) {
		this(null, url, timeout);
	}

	public HttpPool(HttpProxy httpProxy, String url, int timeout) {
		this(new HttpParam(httpProxy, url), timeout);
	}

	private int request(String path, ByteArrayQueue requestBody,
			Map<String, List<String>> requestHeaders, boolean head,
			ByteArrayQueue responseBody,
			Map<String, List<String>> responseHeaders) throws IOException {
		Socket socket = borrowObject();
		try {
			boolean[] connectionClose = { false };
			int status = HttpUtil.request(socket, path_ + path, host,
					proxyAuth, requestBody, requestHeaders, head, responseBody,
					responseHeaders, connectionClose);
			if (connectionClose[0]) {
				socket.close();
			} else {
				returnObject(socket);
			}
			socket = null;
			return status;
		} finally {
			if (socket != null) {
				socket.close();
			}
		}
	}

	private void requestWithOutResponse(String path,
			ByteArrayQueue requestBody,
			Map<String, List<String>> requestHeaders, boolean head)
			throws IOException {
		Socket socket = borrowObject();
		try {
			HttpUtil.send(socket.getOutputStream(), path_ + path, host,
					proxyAuth, requestBody, requestHeaders, head);
			returnObject(socket);
		} finally {
			if (socket != null) {
				socket.close();
			}
		}
	}

	public int head(String path, Map<String, List<String>> requestHeaders,
			Map<String, List<String>> responseHeaders) throws IOException {
		return request(path, null, requestHeaders, true, null, responseHeaders);
	}

	public int get(String path, Map<String, List<String>> requestHeaders,
			ByteArrayQueue responseBody,
			Map<String, List<String>> responseHeaders) throws IOException {
		return request(path, null, requestHeaders, false, responseBody,
				responseHeaders);
	}

	public int post(String path, ByteArrayQueue requestBody,
			Map<String, List<String>> requestHeaders,
			ByteArrayQueue responseBody,
			Map<String, List<String>> responseHeaders) throws IOException {
		return request(path, requestBody, requestHeaders, false, responseBody,
				responseHeaders);
	}

	public void postWithOutResponse(String path, ByteArrayQueue requestBody,
			Map<String, List<String>> requestHeaders) throws IOException {
		requestWithOutResponse(path, requestBody, requestHeaders, false);
	}

	public void pipeline(List<Pipeline.Request> requests,
			List<Pipeline.Response> responses) throws IOException {
		Socket socket = borrowObject();
		try {
			boolean[] connectionClose = { false };
			Pipeline.pipeline(socket, host, requests, responses,
					connectionClose);
			if (connectionClose[0]) {
				socket.close();
			} else {
				returnObject(socket);
			}
			socket = null;
		} finally {
			if (socket != null) {
				socket.close();
			}
		}
	}
}