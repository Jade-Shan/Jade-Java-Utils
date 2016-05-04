package jadeutils.net.http;

import jadeutils.encryption.Base64;
import jadeutils.net.SocketPool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URL;

import javax.net.SocketFactory;

public class HttpParam {
	SocketFactory sf;
	SocketAddress sa;
	String path, host, proxyAuth;

	HttpParam(HttpProxy httpProxy, String url) {
		if (httpProxy == null) {
			try {
				URL url_ = new URL(url);
				int port = url_.getPort();
				String query = url_.getQuery();
				sf = SocketPool.getSocketFactory(url_.getProtocol().equals(
						"https"));
				sa = new InetSocketAddress(url_.getHost(),
						port == -1 ? url_.getDefaultPort() : port);
				path = url_.getPath() + (query == null ? "" : "?" + query);
				host = url_.getHost() + (port == -1 ? "" : ":" + port);
			} catch (IOException e) {
				sf = SocketPool.getSocketFactory(false);
				sa = new InetSocketAddress("localhost", 80);
				path = "/";
				host = "localhost";
			}
			proxyAuth = null;
		} else {
			sf = SocketPool.getSocketFactory(false);
			sa = httpProxy.getSocketAddress();
			path = url;
			try {
				URL url_ = new URL(url);
				int port = url_.getPort();
				host = url_.getHost() + (port == -1 ? "" : ":" + port);
			} catch (IOException e) {
				host = "localhost";
			}
			String username = httpProxy.getUsername();
			if (username == null) {
				proxyAuth = null;
			} else {
				String password = httpProxy.getPassword();
				proxyAuth = "Basic "
						+ Base64.encode((username + ":" + (password == null ? ""
								: password)).getBytes());
			}
		}
	}
}
