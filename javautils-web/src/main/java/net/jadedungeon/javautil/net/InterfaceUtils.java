package net.jadedungeon.javautil.net;

//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class InterfaceUtils {
	
	public static byte[] parseIpv4ToByts(String ipv4Str) {
         byte[] ipv4addr = {0, 0, 0, 0};
		 String[] ipv4StrArr = ipv4Str.split("\\.");
         ipv4addr[0] = (byte) Integer.parseInt(ipv4StrArr[0]);
         ipv4addr[1] = (byte) Integer.parseInt(ipv4StrArr[1]);
         ipv4addr[2] = (byte) Integer.parseInt(ipv4StrArr[2]);
         ipv4addr[3] = (byte) Integer.parseInt(ipv4StrArr[3]);
         return ipv4addr;
	}
	/**
	 * 获取本地IP地址
	 *
	 * @return return local ip address
	 * @throws UnknownHostException unkno host
	 * @throws SocketException socket err
	 */
	public static String getLocalIP() throws UnknownHostException,
			SocketException //
	{
		String ip = "127.0.0.1";
		if (isWindowsOS()) {
			ip = InetAddress.getLocalHost().getHostAddress();
		} else {
			try {
				for (Enumeration<NetworkInterface> en = NetworkInterface
						.getNetworkInterfaces(); en.hasMoreElements();) //
				{
					NetworkInterface intf = en.nextElement();
					String name = intf.getName();
					if (!name.contains("docker") && !name.contains("lo")) {
						for (Enumeration<InetAddress> enumIpAddr = intf
								.getInetAddresses(); enumIpAddr
								.hasMoreElements();) //
						{
							InetAddress inetAddress = enumIpAddr.nextElement();
							if (!inetAddress.isLoopbackAddress()) {
								String ipaddress = inetAddress.getHostAddress()
										.toString();
								if (!ipaddress.contains("::")
										&& !ipaddress.contains("0:0:")
										&& !ipaddress.contains("fe80")) //
								{
									ip = ipaddress;
								}
							}
						}
					}
				}
			} catch (SocketException ex) {
				ex.printStackTrace();
			}
		}
		return ip;
	}

	/**
	 * 判断操作系统是否是Windows
	 *
	 * @return
	 */
	private static boolean isWindowsOS() {
		boolean isWindowsOS = false;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			isWindowsOS = true;
		}
		return isWindowsOS;
	}
	private static void getLocalMac(InetAddress ia) throws SocketException {

		// TODO Auto-generated method stub

		//获取网卡，获取地址

		byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();

		System.out.println("mac数组长度："+mac.length);

		StringBuffer sb = new StringBuffer("");

		for(int i=0; i<mac.length; i++) {

			if(i!=0) {

				sb.append("-");

			}

			//字节转换为整数

			int temp = mac[i]&0xff;

			String str = Integer.toHexString(temp);

			System.out.println("每8位:"+str);

			if(str.length()==1) {

				sb.append("0"+str);

			}else {

				sb.append(str);

			}

		}

		System.out.println("本机MAC地址:"+sb.toString().toUpperCase());

	}


	/**
	 * get local host name
	 * @return local host name
	 * @throws UnknownHostException unknow host
	 */
	public static String getLocalHostName() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostName();
	}

	public static void main(String[] args) throws UnknownHostException,
			SocketException {
		System.out.println(getLocalIP());
		System.out.println(getLocalHostName());
		//得到IP，输出PC-201309011313/122.206.73.83

		InetAddress ia = InetAddress.getLocalHost();

		System.out.println(ia);

		getLocalMac(ia);

	}
}
