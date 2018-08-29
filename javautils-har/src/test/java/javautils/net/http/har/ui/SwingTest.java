package javautils.net.http.har.ui;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.junit.Test;

public class SwingTest {

	@Test
	public void test() {
		// org.junit.Assert.fail("Not yet implemented");
		this.showWindow01();
		try {
			Thread.sleep(5 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void showWindow01() {
		try {
			UIManager.put("control", new Color(128, 128, 128));
			UIManager.put("info", new Color(128, 128, 128));
			UIManager.put("nimbusBase", new Color(18, 30, 49));
			UIManager.put("nimbusAlertYellow", new Color(248, 187, 0));
			UIManager.put("nimbusDisabledText", new Color(128, 128, 128));
			UIManager.put("nimbusFocus", new Color(115, 164, 209));
			UIManager.put("nimbusGreen", new Color(176, 179, 50));
			UIManager.put("nimbusInfoBlue", new Color(66, 139, 221));
			UIManager.put("nimbusLightBackground", new Color(18, 30, 49));
			UIManager.put("nimbusOrange", new Color(191, 98, 4));
			UIManager.put("nimbusRed", new Color(169, 46, 34));
			UIManager.put("nimbusSelectedText", new Color(255, 255, 255));
			UIManager.put("nimbusSelectionBackground", new Color(104, 93, 156));
			UIManager.put("text", new Color(230, 230, 230));
			// String styleName = "";
			// // styleName =
			// //
			// com.sun.java.swing.plaf.windows.WindowsLookAndFeel.class.getName();
			// // styleName =
			// //
			// com.sun.java.swing.plaf.motif.MotifLookAndFeel.class.getName();
			// styleName =
			// javax.swing.plaf.nimbus.NimbusLookAndFeel.class.getName();
			// UIManager.setLookAndFeel(styleName);
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) //
		{
			e.printStackTrace();
		}

		JFrame frame = new JFrame("测试窗口");
		frame.setSize(300, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//
		JPanel panel = new JPanel();
		frame.setContentPane(panel);
		//
		JButton btn = new JButton("确定");
		panel.add(btn);
		btn = new JButton("取消");
		panel.add(btn);
		//
		frame.setVisible(true);
	}

}
