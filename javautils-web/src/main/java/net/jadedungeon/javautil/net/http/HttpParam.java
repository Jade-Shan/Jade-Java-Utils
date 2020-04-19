package net.jadedungeon.javautil.net.http;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URL;
import java.util.Base64;

import javax.net.SocketFactory;

import net.jadedungeon.javautil.net.SocketPool;

public class HttpParam {
	SocketFactory socketFactory;
	SocketAddress socketAddress;
	String path, host, proxyAuth;

	HttpParam(HttpProxy httpProxy, URL url, InetAddress address) {
		if (httpProxy == null) {
			try {
				int port = url.getPort();
				String query = url.getQuery();
				socketFactory = SocketPool.getSocketFactory(//
						url.getProtocol().equals("https"));
				socketAddress = new InetSocketAddress(//
						address, port == -1 ? url.getDefaultPort() : port);
				path = url.getPath() + (query == null ? "" : "?" + query);
				host = url.getHost() + (port == -1 ? "" : ":" + port);
			} catch (Exception e) {
				socketFactory = SocketPool.getSocketFactory(false);
				socketAddress = new InetSocketAddress("localhost", 80);
				path = "/";
				host = "localhost";
			}
			proxyAuth = null;
		} else {
			socketFactory = SocketPool.getSocketFactory(false);
			socketAddress = httpProxy.getSocketAddress();
			path = url.getRef();
			try {
				int port = url.getPort();
				host = url.getHost() + (port == -1 ? "" : ":" + port);
			} catch (Exception e) {
				host = "localhost";
			}
			String username = httpProxy.getUsername();
			if (username == null) {
				proxyAuth = null;
			} else {
				String password = httpProxy.getPassword();
				proxyAuth = "Basic " + //
				Base64.getEncoder().encodeToString(//
						(username + ":" + (//
								password == null ? "" : password)).getBytes());
			}
		}
	}
}
