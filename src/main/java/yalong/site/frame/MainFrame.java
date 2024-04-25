package yalong.site.frame;

import yalong.site.cache.FrameSetting;
import yalong.site.frame.panel.TabPane;
import yalong.site.frame.utils.FrameMsgUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * 主窗体
 *
 * @author yaLong
 * @date 2022/2/11
 */
public class MainFrame extends JFrame {
	private static MainFrame frame;

	private static volatile boolean  hasPainted = false;

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
	}

	public static void start() {
		frame = new MainFrame();
		TabPane tabPane = TabPane.builder();
		frame.add(tabPane);
		frame.setVisible(true);
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				hasPainted = true;
			}
		});

		//等组件渲染 再显示帮助信息
		while (!hasPainted) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException ignored) {
			}
		}
		helpMsg();
	}

    private static void helpMsg(){
	    FrameMsgUtil.sendLine("如果想发送所有人消息,在游戏聊天框内自己下拉选择");
	    FrameMsgUtil.sendLine("按DELETE建,标记喷人语句为已屏蔽,方便对照修改");
        FrameMsgUtil.sendLine("按END键,一键夸人");
        FrameMsgUtil.sendLine("按HOME建,一键喷人");
        FrameMsgUtil.sendLine("按F1发送队友战绩和得分,可在选英雄界面使用");
        FrameMsgUtil.sendLine("按F2发送对方战绩和得分,只能在游戏开始后使用");
	    FrameMsgUtil.sendLine("盲仔按T,自动插眼+闪现+W,默认按键为4DW");
	    FrameMsgUtil.sendLine("秒选/禁用英雄选择英雄后自动生效");
        FrameMsgUtil.sendLine("双击鼠标左键清空当前显示内容");
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
