package net.jadedungeon.javautil.net.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.http.conn.DnsResolver;

import net.jadedungeon.javautil.encryption.ByteArrayQueue;
import net.jadedungeon.javautil.encryption.Numbers;
import net.jadedungeon.javautil.encryption.Streams;

public class HttpUtil {
	private static final int RESP_MAX_SIZE = 65536;
	private static final String HEX_DIGITS = "0123456789ABCDEF";

	private static void copyResponse(InputStream in, ByteArrayQueue baq,
			byte[] buffer, int length) throws IOException //
	{
		int bytesToRead = length;
		while (bytesToRead > 0) {
			int bytesRead = in.read(buffer, 0,
					Math.min(RESP_MAX_SIZE, bytesToRead));
			if (bytesRead < 0) {
				throw new IOException("Connection Lost");
			}
			if (bytesRead == 0) {
				throw new IOException("Zero Bytes Read");
			}
			bytesToRead -= bytesRead;
			if (baq != null) {
				baq.add(buffer, 0, bytesRead);
			}
		}
	}

	private static final byte[] CRLF = { '\r', '\n' };
	private static final byte[] COLON = { ':', ' ' };
	private static final byte[] GET = "GET ".getBytes();
	private static final byte[] HEAD = "HEAD ".getBytes();
	private static final byte[] POST = "POST ".getBytes();
	private static final byte[] HTTP11 = " HTTP/1.1\r\n".getBytes();
	private static final byte[] HOST = "Host: ".getBytes();
	private static final byte[] PROXY_AUTH = "Proxy-Authorization: ".getBytes();
	private static final byte[] CONTENT_LENGTH = "Content-Length: ".getBytes();
	private static final byte[] HEAD_END = ("Accept-Encoding: gzip\r\n"
			+ "Connection: Keep-Alive\r\n\r\n").getBytes();

	static void send(OutputStream out, String path, String host,
			String proxyAuth, ByteArrayQueue requestBody,
			Map<String, List<String>> requestHeaders, boolean head)
			throws IOException //
	{
		ByteArrayQueue headerBaq = new ByteArrayQueue();
		if (requestBody == null) {
			headerBaq.add(head ? HEAD : GET).add(path.getBytes()).add(HTTP11);
		} else {
			String length = "" + requestBody.length();
			headerBaq.add(POST).add(path.getBytes()).add(HTTP11)
					.add(CONTENT_LENGTH).add(length.getBytes()).add(CRLF);
		}
		headerBaq.add(HOST).add(host.getBytes()).add(CRLF);
		if (proxyAuth != null) {
			headerBaq.add(PROXY_AUTH).add(proxyAuth.getBytes()).add(CRLF);
		}
		if (requestHeaders != null) {
			for (Map.Entry<String, List<String>> entry : requestHeaders
					.entrySet()) {
				byte[] key = entry.getKey().getBytes();
				for (String value : entry.getValue()) {
					headerBaq.add(key).add(COLON).add(value.getBytes())
							.add(CRLF);
				}
			}
		}
		headerBaq.add(HEAD_END);

		Streams.copy(headerBaq.getInputStream(), out);
		if (requestBody != null) {
			Streams.copy(requestBody.getInputStream(), out);
		}
	}

	public static int recv(InputStream in, ByteArrayQueue responseBody,
			Map<String, List<String>> responseHeaders, boolean head,
			boolean[] connectionClose) throws IOException //
	{
		// Response Header
		boolean gzip = false, close = false;
		int status = 0, contentLength = 0;
		StringBuilder sb = new StringBuilder();
		while (true) {
			int b = in.read();
			if (b < 0) {
				throw new IOException("Connection Lost");
			}
			if (b == '\r') {
				continue;
			}
			if (b != '\n') {
				sb.append((char) b);
				continue;
			}
			if (sb.length() == 0) {
				if (status == 100) {
					status = 0;
					continue;
				}
				break;
			}
			if (status == 0) {
				String[] ss = sb.toString().split(" ");
				if (ss.length < 2) {
					throw new IOException("Response Error: [" + sb + "]");
				}
				status = Numbers.parseInt(ss[1]);
			} else if (status != 100) {
				int index = sb.indexOf(": ");
				if (index >= 0) {
					String key = sb.substring(0, index).toUpperCase();
					String value = sb.substring(index + 2);
					switch (key) {
					case "CONNECTION":
						if (value.equalsIgnoreCase("close")) {
							close = true;
						}
						break;
					case "CONTENT-LENGTH":
						contentLength = Numbers.parseInt(value);
						break;
					case "CONTENT-ENCODING":
						if (value.equalsIgnoreCase("gzip")) {
							gzip = true;
						}
						break;
					case "TRANSFER-ENCODING":
						if (value.equalsIgnoreCase("chunked")) {
							contentLength = -1;
						}
						break;
					}
					if (responseHeaders != null) {
						List<String> values = responseHeaders.get(key);
						if (values == null) {
							values = new ArrayList<>();
							responseHeaders.put(key, values);
						}
						values.add(value);
					}
				}
			}
			sb.setLength(0);
		}
		if (connectionClose != null && connectionClose.length > 0) {
			connectionClose[0] = close;
		}

		// Response Body
		if (head || contentLength == 0) {
			return status;
		}
		byte[] buffer = new byte[RESP_MAX_SIZE];
		if (contentLength > 0) {
			if (gzip) {
				ByteArrayQueue gzipBody = new ByteArrayQueue();
				copyResponse(in, gzipBody, buffer, contentLength);
				if (responseBody != null) {
					try (GZIPInputStream gzipis = new GZIPInputStream(
							gzipBody.getInputStream())) {
						Streams.copy(gzipis, responseBody.getOutputStream());
					} catch (IOException e) {
						// Ignored
					}
				}
			} else {
				copyResponse(in, responseBody, buffer, contentLength);
			}
			return status;
		}

		/*
		 * Response Body (Chunked) >=0: waiting size and CRLF -1: waiting next
		 * block (CRLF) -2: waiting terminator (CRLF)
		 */
		int chunkSize = 0;
		ByteArrayQueue gzipBody = gzip ? new ByteArrayQueue() : responseBody;
		while (true) {
			int b = in.read();
			if (b < 0) {
				throw new IOException("Connection Lost");
			}
			if (chunkSize < 0) {
				if (b == '\n') {
					if (chunkSize == -2) {
						break;
					}
					chunkSize = 0;
				}
			} else if (b == '\n') {
				if (chunkSize == 0) {
					chunkSize = -2;
				} else {
					copyResponse(in, gzipBody, buffer, chunkSize);
					chunkSize = -1;
				}
			} else {
				b = HEX_DIGITS.indexOf(Character.toUpperCase(b));
				if (b >= 0) {
					chunkSize = chunkSize * 16 + b;
				}
			}
		}
		if (gzip && responseBody != null) {
			try (GZIPInputStream gzipis = new GZIPInputStream(
					gzipBody.getInputStream())) {
				Streams.copy(gzipis, responseBody.getOutputStream());
			} catch (IOException e) {
				// Ignored
			}
		}
		return status;
	}

	static int request(Socket socket, String path, String host,
			String proxyAuth, ByteArrayQueue requestBody,
			Map<String, List<String>> requestHeaders, boolean head,
			ByteArrayQueue responseBody,
			Map<String, List<String>> responseHeaders, boolean[] connectionClose)
			throws IOException //
	{
		send(socket.getOutputStream(), path, host, proxyAuth, requestBody,
				requestHeaders, head);
		return recv(socket.getInputStream(), responseBody, responseHeaders,
				head, connectionClose);
	}

	private static int request(HttpProxy httpProxy, DnsResolver resolver, //
			String href, ByteArrayQueue requestBody,
			Map<String, List<String>> requestHeaders, boolean head,
			ByteArrayQueue responseBody,
			Map<String, List<String>> responseHeaders, int timeout)
			throws IOException //
	{
		URL url = new URL(href);
		InetAddress address = resolver.resolve(url.getHost())[0];
		HttpParam param = new HttpParam(httpProxy, url, address);
		try (Socket socket = param.socketFactory.createSocket()) {
			socket.setSoTimeout(timeout);
			socket.connect(param.socketAddress);
			return request(socket, param.path, param.host, param.proxyAuth,
					requestBody, requestHeaders, head, responseBody,
					responseHeaders, null);
		}
	}

	public static int head(DnsResolver resolver, String url,
			Map<String, List<String>> requestHeaders,
			Map<String, List<String>> responseHeaders, int timeout)
			throws IOException //
	{
		return head(null, resolver, url, requestHeaders, responseHeaders, timeout);
	}

	public static int get(DnsResolver resolver, String url, Map<String, List<String>> requestHeaders,
			ByteArrayQueue responseBody,
			Map<String, List<String>> responseHeaders, int timeout)
			throws IOException //
	{
		return get(null, resolver, url, requestHeaders, responseBody, responseHeaders,
				timeout);
	}

	public static int post(DnsResolver resolver, String url, ByteArrayQueue requestBody,
			Map<String, List<String>> requestHeaders,
			ByteArrayQueue responseBody,
			Map<String, List<String>> responseHeaders, int timeout)
			throws IOException //
	{
		return post(null, resolver, url, requestBody, requestHeaders, responseBody,
				responseHeaders, timeout);
	}

	public static int head(HttpProxy httpProxy, DnsResolver resolver, String url,
			Map<String, List<String>> requestHeaders,
			Map<String, List<String>> responseHeaders, int timeout)
			throws IOException //
	{
		return request(httpProxy, resolver, url, null, requestHeaders, true, null,
				responseHeaders, timeout);
	}

	public static int get(HttpProxy httpProxy, DnsResolver resolver, String url,
			Map<String, List<String>> requestHeaders,
			ByteArrayQueue responseBody,
			Map<String, List<String>> responseHeaders, int timeout)
			throws IOException //
	{
		return request(httpProxy, resolver, url, null, requestHeaders, false,
				responseBody, responseHeaders, timeout);
	}

	public static int post(HttpProxy httpProxy, DnsResolver resolver, String url,
			ByteArrayQueue requestBody,
			Map<String, List<String>> requestHeaders,
			ByteArrayQueue responseBody,
			Map<String, List<String>> responseHeaders, int timeout)
			throws IOException //
	{
		return request(httpProxy, resolver, url, requestBody, requestHeaders, false,
				responseBody, responseHeaders, timeout);
	}
}