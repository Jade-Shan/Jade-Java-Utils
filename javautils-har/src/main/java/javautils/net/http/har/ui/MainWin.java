package javautils.net.http.har.ui;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainWin {

	private JFrame mainFrame;
	private MainMenu mainMenu;

	public MainWin() {
		this.mainFrame = new JFrame("测试窗口");
		this.mainMenu = new MainMenu(mainFrame);
	}

	public void init() {
		System.out.println("init");
		this.initLookAndFell();
		this.initMainFrame();
		this.mainMenu.initMainMenu();
		//
		mainFrame.setVisible(true);
	}

	public void initMainFrame() {
		mainFrame.setSize(300, 200);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//
		JPanel panel = new JPanel();
		mainFrame.setContentPane(panel);
		//
		JButton btn = new JButton("确定");
		btn.setToolTipText("组件可以加提示");
		panel.add(btn);
		btn = new JButton("取消");
		panel.add(btn);
	}

	public void initLookAndFell() {
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
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				// 只在Nimbus风格下替换成暗色风格
				// UIManager.setLookAndFeel(javax.swing.plaf.nimbus.NimbusLookAndFeel.class.getName());
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
	}

	public static void main(String[] args) {
		System.out.println("Hello");
		MainWin m = new MainWin();
		m.init();
	}

}
