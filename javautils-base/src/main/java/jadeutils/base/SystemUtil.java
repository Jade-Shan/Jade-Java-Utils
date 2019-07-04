package jadeutils.base;

/*
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
*/

public class SystemUtil {

	/**
	 * 判断操作系统是否是Windows
	 *
	 * @return
	 */
	public static boolean isOSWindows() {
		boolean isWindowsOS = false;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			isWindowsOS = true;
		}
		return isWindowsOS;
	}

	public static void main(String[] args) {
		/*
		 * System.load("/var/crawl/sigar-amd64-winnt.dll"); //
		 * C:\var\crawl\sigar-amd64-winnt.dll Sigar sigar = new Sigar(); try { CpuInfo
		 * cpuInfos[] = sigar.getCpuInfoList(); for (int i = 0; i < cpuInfos.length;
		 * i++) { System.out.println("=============================");
		 * System.out.println("   mhz: " + cpuInfos[i].getMhz());
		 * System.out.println("vendor: " + cpuInfos[i].getVendor());
		 * System.out.println(" model: " + cpuInfos[i].getModel());
		 * System.out.println(" cache: " + cpuInfos[i].getCacheSize()); } } catch
		 * (SigarException e) { e.printStackTrace(); }
		 */
	}

}
