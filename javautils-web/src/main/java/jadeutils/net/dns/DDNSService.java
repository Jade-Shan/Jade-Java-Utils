package jadeutils.net.dns;

import jadeutils.encryption.Bytes;
import jadeutils.encryption.Numbers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.xbill.DNS.ARecord;
import org.xbill.DNS.DClass;
import org.xbill.DNS.Flags;
import org.xbill.DNS.Header;
import org.xbill.DNS.Message;
import org.xbill.DNS.Name;
import org.xbill.DNS.Opcode;
import org.xbill.DNS.Rcode;
import org.xbill.DNS.Record;
import org.xbill.DNS.Section;
import org.xbill.DNS.Type;

public class DDNSService {
	private ConcurrentHashMap<String, Record> dynamicRecords = new ConcurrentHashMap<>();
	private HashMap<String, Record[]> aRecords = new HashMap<>();
	private HashMap<String, Record[]> aaaaRecords = new HashMap<>();
	private HashMap<String, Record[]> nsRecords = new HashMap<>();
	private HashMap<String, Record[]> mxRecords = new HashMap<>();

	/**
	 * 把IP地址从字符串转为byte数组。
	 * 
	 * @param ipStr
	 * @return
	 * @throws ParseException
	 */
	public byte[] parseIpAddress(String ipStr) throws ParseException {
		String[] ss = ipStr.split("\\.");
		if (ss.length < 4) {
			throw new ParseException("erro ip address String: " + ipStr, 1);
		}
		byte[] ip = new byte[4];
		for (int j = 0; j < 4; j++) {
			ip[j] = (byte) Numbers.parseInt(ss[j]);
		}
		return ip;
	}

	/**
	 * 把所有主机名统一成以`.`结尾 的绝对主机名
	 * 
	 * @param hostName
	 * @return
	 */
	public String absoluteHostName(String hostName) {
		return (hostName.endsWith(".") ? hostName : hostName + ".")
				.toLowerCase();
	}

	/**
	 * 把新的主机名：IP映射添加到集合中去。
	 * 
	 * @param hostName
	 * @param ip
	 * @param ttl
	 */
	private void addIp(String hostName, String ip, int ttl) {
		try {
			ARecord rec = createARecord(hostName, parseIpAddress(ip), ttl);
			dynamicRecords.put(hostName, rec);
			System.out.println("add rec: " + hostName + " -> " + ip);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 合建 一条解析 记录
	 * 
	 * @param hostName
	 * @param ip
	 * @param ttl
	 * @return
	 */
	private ARecord createARecord(String hostName, byte[] ip, int ttl) {
		ARecord rec = null;
		try {
			Name name = new Name(absoluteHostName(hostName));
			InetAddress addr = InetAddress.getByAddress(ip);
			rec = new ARecord(name, DClass.IN, ttl, addr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rec;
	}

	private ARecord findARecordInPublicDNS(String hostName, int ttl) {
		ARecord rec = null;
		try {
			InetAddress addr = InetAddress.getByName(hostName);
			rec = this.createARecord(hostName, addr.getAddress(), ttl);
			dynamicRecords.put(hostName, rec);
			System.out.println("add rec: " + hostName + " -> "
					+ addr.getAddress());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rec;
	}

	/**
	 * 输出数据包的内容
	 * 
	 * @param packet
	 * @param send
	 */
	private void dump(DatagramPacket packet, boolean send) {
		StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
		Bytes.dump(out, packet.getData(), packet.getOffset(),
				packet.getLength());
		System.out.println("\n\ndumpstart ============================");
		System.out.println((send ? "Sent to " : "Received from ")
				+ packet.getAddress().getHostAddress());
		System.out.println(sw.toString());
		System.out.println("\n\ndumpend ============================");
	}

	private Message getError(Header header, int rcode, Record question) {
		Message response = new Message();
		response.setHeader(header);
		response.removeAllRecords(Section.QUESTION);
		response.removeAllRecords(Section.ANSWER);
		response.removeAllRecords(Section.AUTHORITY);
		response.removeAllRecords(Section.ADDITIONAL);
		if (rcode == Rcode.SERVFAIL) {
			response.addRecord(question, Section.QUESTION);
		}
		header.setRcode(rcode);
		return response;
	}

	private Message process(Message request) {
		Header reqHeader = request.getHeader();
		if (reqHeader.getFlag(Flags.QR)) {
			return null;
		}
		Record question = request.getQuestion();
		if (reqHeader.getRcode() != Rcode.NOERROR) {
			return getError(reqHeader, Rcode.FORMERR, question);
		}
		if (reqHeader.getOpcode() != Opcode.QUERY) {
			return getError(reqHeader, Rcode.NOTIMP, question);
		}

		Message response = new Message(reqHeader.getID());
		Header respHeader = response.getHeader();
		respHeader.setFlag(Flags.QR);
		respHeader.setFlag(Flags.AA);
		if (reqHeader.getFlag(Flags.RD)) {
			respHeader.setFlag(Flags.RD);
		}
		response.addRecord(question, Section.QUESTION);

		String host = question.getName().toString(true).toLowerCase();
		Record[] records;
		switch (question.getType()) {
		case Type.A:
		case Type.ANY:
			records = aRecords.get(host);
			if (records == null) {
				Record record = dynamicRecords.get(host);
				if (null == record) {
					record = findARecordInPublicDNS(host, 60_000);
				}
				System.out.println("find record: " + host + " -> " + record);
				records = record == null ? null : new Record[] { record };
			}
			break;
		case Type.NS:
			records = nsRecords.get(host);
			break;
		case Type.MX:
			records = mxRecords.get(host);
			break;
		case Type.AAAA:
			records = aaaaRecords.get(host);
			break;
		default:
			return getError(reqHeader, Rcode.NOTIMP, question);
		}
		if (records == null) {
			return getError(reqHeader, Rcode.NXDOMAIN, question);
		}
		for (Record record : records) {
			response.addRecord(record, Section.ANSWER);
		}
		/*
		 * for (Record[] records_ : nsRecords.values()) { for (Record record :
		 * records_) { response.addRecord(record, Section.AUTHORITY); } }
		 */
		return response;
	}

	/**
	 * 在指定端口启动域名解析服务
	 * 
	 * @param port
	 */
	public void doService(int port, DNSUpdater dnsUpdater) {
		try (DatagramSocket socket = new DatagramSocket(port)) {
			Thread thread = new Thread() {
				@Override
				public void run() {
					while (true) {
						List<String[]> recs = dnsUpdater.updateDynamicRecords();
						if (null != recs && recs.size() > 0) {
							for (String[] rec : recs) {
								addIp(rec[0], rec[1], 5);
							}
						}
					}
				}
			};
			thread.start();

			socket.setSoTimeout(816);

			while (true) {
				// Receive
				byte[] buf = new byte[65536];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				try {
					socket.receive(packet);
					this.dump(packet, false);
					Message request;
					try {
						request = new Message(Bytes.left(buf,
								packet.getLength()));
					} catch (IOException e) {
						e.printStackTrace();
						continue;
					}
					// Service
					Message response = this.process(request);
					if (response == null) {
						continue;
					}
					// Send
					buf = response.toWire();
					try {
						packet = new DatagramPacket(buf, buf.length,
								packet.getSocketAddress());
						socket.send(packet);
						this.dump(packet, true);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					if (!(e instanceof SocketTimeoutException)) {
						e.printStackTrace();
					}
					try {
						Thread.sleep(16);
					} catch (InterruptedException ee) {/**/
					}
					continue;
				}
			}
		} catch (Error | RuntimeException | IOException e) {
			e.printStackTrace();
		} finally {

		}

	}

	public static void main(String[] args) {
		int port = 53;
		String apiAddr = "http://localhost:8383/addr.jsp";
		int apiTimeout = 10_000;

		DNSUpdater updater = new RemoteDNSUpdater(apiAddr, apiTimeout);
		DDNSService ddns = new DDNSService();
		ddns.doService(port, updater);
	}

}
