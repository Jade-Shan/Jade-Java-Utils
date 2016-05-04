package jadeutils.net.http;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class HttpProxy {
	private SocketAddress sa;
	private String username, password;

	public HttpProxy(String host, int port) {
		this(host, port, null, null);
	}

	public HttpProxy(SocketAddress sa) {
		this(sa, null, null);
	}

	public HttpProxy(String host, int port, String username, String password) {
		this(new InetSocketAddress(host, port), username, password);
	}

	public HttpProxy(SocketAddress sa, String username, String password) {
		this.sa = sa;
		this.username = username;
		this.password = password;
	}

	public SocketAddress getSocketAddress() {
		return sa;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}