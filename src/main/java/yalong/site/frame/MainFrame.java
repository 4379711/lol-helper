package yalong.site.frame;

import lombok.extern.slf4j.Slf4j;
import yalong.site.cache.AppCache;
import yalong.site.cache.FrameSetting;
import yalong.site.exception.RepeatProcessException;
import yalong.site.frame.panel.TabPane;
import yalong.site.frame.utils.FrameMsgUtil;
import yalong.site.frame.utils.SaveFrameConfig;
import yalong.site.services.word.GarbageWord;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * 主窗体
 *
 * @author yaLong
 * @date 2022/2/11
 */
@Slf4j
public class MainFrame extends JFrame {
	private static MainFrame frame;
	//用于创建后台托盘
	private static TrayIcon trayIcon;
	private static SystemTray tray;
	//用于检测软件是否重复打开
	private static RandomAccessFile RAF;
	private static boolean lockFlag;
	final static String LOCK_FILE = "lol-helper.lock";
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
//		this.setResizable(false);
		this.setSize(FrameSetting.WIDTH, FrameSetting.HEIGHT);
		//窗口居中
		this.setLocationRelativeTo(null);
		//窗口置顶
		// this.setAlwaysOnTop(true);
		this.addWindowListener(listener());
		initTray();
	}

	public WindowListener listener() {
		return new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
				log.info("配置加载完成");
				// 加载垃圾话
				AppCache.garbageWordList = GarbageWord.loadWord();
				log.info("垃圾话加载完成");
			}

			@Override
			public void windowClosing(WindowEvent e) {
				SaveFrameConfig.save();
				if (RAF != null) {
					try {
						RAF.close();
					} catch (IOException ex) {
						throw new RuntimeException(ex);
					}
				}
				log.info("配置保存完成");
			}

			@Override
			public void windowClosed(WindowEvent e) {

			}

			@Override
			public void windowIconified(WindowEvent e) {
				hideToTray();
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

	public void initTray() {
		tray = SystemTray.getSystemTray();
		// 加载图标，确保路径正确
		Image image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/assets/logo.png"));
		// 如果图标加载失败，使用默认图标
		if (image == null) {
			image = Toolkit.getDefaultToolkit().createImage(new byte[0]);
		}
		// 创建弹出菜单
		PopupMenu popup = new PopupMenu();

		// 创建菜单项
		MenuItem openItem = new MenuItem(("open"));
		MenuItem exitItem = new MenuItem(("exit"));

		// 添加菜单项到弹出菜单
		popup.add(openItem);
		popup.addSeparator();
		popup.add(exitItem);

		// 创建托盘图标
		trayIcon = new TrayIcon(image, "lol-helper", popup);
		trayIcon.setImageAutoSize(true);

		// 鼠标点击还原窗口
		trayIcon.addActionListener(e -> restoreFromTray());

		// 菜单项的动作监听器
		openItem.addActionListener(e -> restoreFromTray());

		exitItem.addActionListener(e -> {
			tray.remove(trayIcon);
			System.exit(0);
		});
	}

	/**
	 * 将应用程序隐藏到系统托盘
	 */
	private static void hideToTray() {
		if (tray != null && trayIcon != null) {
			try {
				tray.add(trayIcon);
				frame.setVisible(false);
				log.info("最小化到托盘");
			} catch (AWTException ex) {
				log.error("最小化到托盘失败" + ex.getMessage());
			}
		}
	}

	/**
	 * 从系统托盘还原应用程序
	 */
	private static void restoreFromTray() {
		if (tray != null && trayIcon != null) {
			tray.remove(trayIcon);
			frame.setVisible(true);
			frame.setState(Frame.NORMAL);
			frame.toFront();
		}
	}

	/**
	 * 查看文件是否有锁 用于判断软件是否重复打开
	 */
	public static void checkFileLock() {
		try {
			if (lockFlag) {
				return;
			}
			File file = new File(LOCK_FILE);
			// 打开文件通道
			RAF = new RandomAccessFile(file, "rw");
			FileChannel channel = RAF.getChannel();
			// 尝试获取独占锁
			FileLock lock = null;
			lock = channel.tryLock();
			if (lock == null) {
				RAF.close();
				lockFlag = false;
				throw new RepeatProcessException();
			} else {
				lockFlag = true;
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
