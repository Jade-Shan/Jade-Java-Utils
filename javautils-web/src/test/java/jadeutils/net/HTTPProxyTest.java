package jadeutils.net;

import jadeutils.net.dns.FakeDnsResolver;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.Properties;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HTTPProxyTest {

	// 配置文件位置
	private static final String CONF_FILE_PROP = "http-proxy.properties";
	private static Properties prop = new Properties();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		InputStream confIn = new BufferedInputStream(Thread.currentThread()
				.getContextClassLoader().getResourceAsStream(CONF_FILE_PROP));
		prop = new Properties();
		prop.load(confIn);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	/*
	 * proxy config in `http-proxy.properties` default is `127.0.0.1:7070`
	 */
	public void test() throws ClientProtocolException, IOException {
		Registry<ConnectionSocketFactory> reg = RegistryBuilder
				.<ConnectionSocketFactory> create()
				.register("http", new ProxySocketFactory())
				.register(
						"https",
						new ProxySSLSocketFactory(SSLContexts
								.createSystemDefault())).build();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(
				reg, new FakeDnsResolver());
		CloseableHttpClient httpclient = HttpClients.custom()
				.setConnectionManager(cm).build();
		try {
			InetSocketAddress socksaddr = new InetSocketAddress(
					prop.getProperty("proxy.host", "172.0.0.1"),
					Integer.parseInt(prop.getProperty("proxy.port", "30")));
			HttpClientContext context = HttpClientContext.create();
			context.setAttribute("socks.address", socksaddr);

			HttpGet request = new HttpGet("http://www.facebook.com");

			System.out.println("Executing request " + request
					+ " via SOCKS proxy " + socksaddr);
			CloseableHttpResponse response = httpclient.execute(request,
					context);
			try {
				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
				int i = -1;
				InputStream stream = response.getEntity().getContent();
				while ((i = stream.read()) != -1) {
					System.out.print((char) i);
				}
				EntityUtils.consume(response.getEntity());
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
	}

}
