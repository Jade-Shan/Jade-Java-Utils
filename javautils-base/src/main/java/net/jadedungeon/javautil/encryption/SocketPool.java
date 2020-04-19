package net.jadedungeon.javautil.encryption;

import javax.net.SocketFactory;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;

public class SocketPool extends Pool<Socket, IOException> {
	public static SocketFactory getSocketFactory(boolean secure) {
		return secure ? secureFactory : plainFactory;
	}

	private static SocketFactory plainFactory, secureFactory;

	static {
		plainFactory = SocketFactory.getDefault();
		try {
			SSLContext sslc = SSLContext.getInstance("TLS");
			sslc.init(new KeyManager[0], new X509TrustManager[] {
				new X509TrustManager() {
					@Override
					public void checkClientTrusted(X509Certificate[] certs, String s) {/* */}

					@Override
					public void checkServerTrusted(X509Certificate[] certs, String s) {/* */}

					@Override
					public X509Certificate[] getAcceptedIssuers() {
						return new X509Certificate[0];
					}
				}
			}, null);
			secureFactory = sslc.getSocketFactory();
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
	}

	private SocketFactory sf;
	private SocketAddress sa;
	private int timeout;

	public SocketPool(String host, int port, int timeout) {
		this(host, port, false, timeout);
	}

	public SocketPool(String host, int port, boolean secure, int timeout) {
		this(getSocketFactory(secure), new InetSocketAddress(host, port), timeout);
	}

	public SocketPool(SocketFactory sf, SocketAddress sa, int timeout) {
		super(timeout, 1000);
		this.sf = sf;
		this.sa = sa;
		this.timeout = timeout;
	}

	@Override
	protected Socket makeObject() throws IOException {
		Socket socket = sf.createSocket();
		try {
			socket.connect(sa, timeout);
			socket.setSoTimeout(timeout);
			return socket;
		} catch (IOException e) {
			socket.close();
			throw e;
		}
	}

	@Override
	protected void destroyObject(Socket socket) {
		try {
			socket.close();
		} catch (IOException e) {/* */}
	}
}