package jadeutils.net.http;

import jadeutils.encryption.ByteArrayQueue;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.http.conn.DnsResolver;

public class Pipeline {
	public static class Request {
		private Object id;
		private String path;
		private boolean head;
		private ByteArrayQueue body = null;
		private HashMap<String, List<String>> headers = new HashMap<>();

		public Request(Object id, String path) {
			this(id, path, false);
		}

		public Request(Object id, String path, boolean head) {
			this.id = id;
			this.path = path;
			this.head = head;
		}

		public Object getId() {
			return id;
		}

		public String getPath() {
			return path;
		}

		public boolean isHead() {
			return head;
		}

		public ByteArrayQueue getBody() {
			return body;
		}

		public void setBody(ByteArrayQueue body) {
			this.body = body;
		}

		public HashMap<String, List<String>> getHeaders() {
			return headers;
		}
	}

	public static class Response {
		private Object id;
		private int status = 0;
		private ByteArrayQueue body = new ByteArrayQueue();
		private LinkedHashMap<String, List<String>> headers = new LinkedHashMap<>();

		public Response(Object id) {
			this.id = id;
		}

		public Object getId() {
			return id;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public ByteArrayQueue getBody() {
			return body;
		}

		public HashMap<String, List<String>> getHeaders() {
			return headers;
		}
	}

	static void pipeline(Socket socket, String host, List<Request> requests,
			List<Response> responses, boolean[] connectionClose)
			throws IOException {
		OutputStream out = socket.getOutputStream();
		for (Request request : requests) {
			HttpUtil.send(out, request.getPath(), host, null,
					request.getBody(), request.getHeaders(), request.isHead());
		}
		InputStream in = socket.getInputStream();
		for (Request request : requests) {
			boolean[] connectionClose_ = connectionClose == null ? new boolean[1]
					: connectionClose;
			Response response = new Response(request.getId());
			response.setStatus(HttpUtil.recv(in, response.getBody(),
					response.getHeaders(), request.isHead(), connectionClose_));
			responses.add(response);
			if (connectionClose_[0] == true) {
				return;
			}
		}
	}

	public static void pipeline(DnsResolver resolver, String url, List<Request> requests,
			List<Response> responses, int timeout) throws IOException {
		pipeline(null, resolver, url, requests, responses, timeout);
	}

	public static void pipeline(HttpProxy httpProxy, DnsResolver resolver, String href,
			List<Request> requests, List<Response> responses, int timeout)
			throws IOException //
	{
		URL url = new URL(href);
		InetAddress address = resolver.resolve(url.getHost())[0];
		HttpParam param = new HttpParam(httpProxy, url, address);
		try (Socket socket = param.socketFactory.createSocket()) {
			socket.setSoTimeout(timeout);
			socket.connect(param.socketAddress);
			pipeline(socket, param.host, requests, responses, null);
		}
	}
}