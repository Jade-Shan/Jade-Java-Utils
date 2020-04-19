package net.jadedungeon.javautil.net.dns;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.conn.DnsResolver;

import net.jadedungeon.javautil.net.InterfaceUtils;

public class FakeResolver implements DnsResolver {
	private boolean isFakeOnly;
	private Map<String, String> nameToIP;

	/**
	 * get ip by host name from fake name-ip table
	 * 
	 * @param nameToIP
	 *            fake name-ip table
	 * @param isFakeOnly
	 *            only return result in fake name-ip table
	 */
	public FakeResolver(Map<String, String> nameToIP, boolean isFakeOnly) {
		this.nameToIP = null != nameToIP ? nameToIP : new HashMap<>();
		this.isFakeOnly = isFakeOnly;
	}

	/**
	 * get ip by host name from fake name-ip table
	 * 
	 * @param hostname
	 *            hostname
	 * @return ip ip address
	 * @throws UnknownHostException
	 */
	private InetAddress getFakeAddress(String hostname) throws UnknownHostException {
		InetAddress addr = null;
		if (nameToIP.containsKey(hostname)) {
			byte[] ip = InterfaceUtils.parseIpv4ToByts(//
					nameToIP.get(hostname));
			addr = InetAddress.getByAddress(hostname, ip);
		} else {
			throw new UnknownHostException(hostname);
		}
		return addr;
	}

	@Override
	public InetAddress[] resolve(String hostname) throws UnknownHostException {
		InetAddress addr = null;
		if (isFakeOnly) {
			addr = getFakeAddress(hostname);
		} else {
			try {
				addr = InetAddress.getByName(hostname);
			} catch (UnknownHostException e) {
				addr = getFakeAddress(hostname);
			}
		}
		return new InetAddress[] { addr };
	}

}
