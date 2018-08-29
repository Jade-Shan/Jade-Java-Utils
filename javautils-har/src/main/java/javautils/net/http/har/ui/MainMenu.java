package javautils.net.http.har.ui;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainMenu {
	private JFrame mainFrame;
	private JMenuBar bar = new JMenuBar();
	private JMenuItem mItmOpenFile;
	private JMenuItem mItmSaveFile;
	private JMenuItem mItmPrint;
	private JMenuItem mItmQuit;
	private JMenuItem mItmSearch;
	private JMenuItem mItmFilter;
	private JMenuItem mItmTree;
	private JMenuItem mItmRaw;

	public MainMenu(JFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.bar = new JMenuBar();
		this.mainFrame.setJMenuBar(this.bar);
	}

	public void initMainMenu() {
		JMenu mFile = new JMenu("文件");
		bar.add(mFile);
		mItmOpenFile = new JMenuItem("打开");
		mFile.add(mItmOpenFile);
		mItmSaveFile = new JMenuItem("保存");
		mFile.add(mItmSaveFile);
		mFile.addSeparator();
		mItmPrint = new JMenuItem("打印");
		mFile.add(mItmPrint);
		mFile.addSeparator();
		mItmQuit = new JMenuItem("退出");
		mFile.add(mItmQuit);
		//
		JMenu mEdit = new JMenu("编辑");
		bar.add(mEdit);
		mItmSearch = new JMenuItem("查找");
		mEdit.add(mItmSearch);
		mItmFilter = new JMenuItem("过滤");
		mEdit.add(mItmFilter);
		//
		JMenu mView = new JMenu("视图");
		bar.add(mView);
		mItmTree = new JMenuItem("树形");
		mView.add(mItmTree);
		mItmRaw = new JMenuItem("原始");
		mView.add(mItmRaw);
	}

}
