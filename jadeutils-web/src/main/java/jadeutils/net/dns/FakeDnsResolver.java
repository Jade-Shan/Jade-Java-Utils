package jadeutils.net.dns;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.http.conn.DnsResolver;

public class FakeDnsResolver implements DnsResolver {

	@Override
	public InetAddress[] resolve(String arg0) throws UnknownHostException {
		return new InetAddress[] { InetAddress.getByAddress(//
				new byte[] { 1, 1, 1, 1 }) };
	}

}
