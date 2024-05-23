package yalong.site.frame;

import lombok.extern.slf4j.Slf4j;
import yalong.site.cache.AppCache;
import yalong.site.cache.FrameInnerCache;
import yalong.site.cache.FrameSetting;
import yalong.site.frame.panel.TabPane;
import yalong.site.frame.utils.FrameMsgUtil;
import yalong.site.frame.utils.SaveFrameConfig;
import yalong.site.services.word.LoadGarbageWord;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * 主窗体
 *
 * @author yaLong
 * @date 2022/2/11
 */
@Slf4j
public class MainFrame extends JFrame {
	private static MainFrame frame;

	static {
		SaveFrameConfig.load();
	}

	public MainFrame() throws HeadlessException {
		super();
		this.setTitle("lol-helper");
		Image image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/assets/logo.png"));
		this.setIconImage(image);
		//退出直接关闭程序
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//固定大小
		this.setResizable(false);
		this.setSize(FrameSetting.WIDTH, FrameSetting.HEIGHT);
		//窗口居中
		this.setLocationRelativeTo(null);
		//窗口置顶
		// this.setAlwaysOnTop(true);
		this.addWindowListener(listener());
	}

	public WindowListener listener(){
		return new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
				log.info("配置加载完成");
				// 加载垃圾话
				AppCache.garbageWordList = LoadGarbageWord.loadWord();
				log.info("垃圾话加载完成");
			}

			@Override
			public void windowClosing(WindowEvent e) {
				SaveFrameConfig.save();
				log.info("配置保存完成");
			}

			@Override
			public void windowClosed(WindowEvent e) {

			}

			@Override
			public void windowIconified(WindowEvent e) {

			}

			@Override
			public void windowDeiconified(WindowEvent e) {

			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {

			}
		};
	}

	public static void start() {
		frame = new MainFrame();
		TabPane tabPane = TabPane.builder();
		frame.add(tabPane);
		frame.setVisible(false);
		FrameMsgUtil.helpMsg();
		FrameInnerCache.frame = frame;
	}

	public static void showFrame() {
		if (frame != null) {
			frame.setVisible(true);
		}
	}

	public static void hiddenFrame() {
		if (frame != null) {
			frame.setVisible(false);
		}
	}

	public static int continueRun(String msg) {
		Object[] options = {" 重试 ", " 退出 "};
		return JOptionPane.showOptionDialog(null, msg, "无法运行", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
	}

}
