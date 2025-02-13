package helper.frame.utils;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;

/**
 * @author @_@
 */
@Slf4j
public class FrameTipUtil {
	// 创建一个始终置顶的 JFrame 作为父组件
	private static final JFrame topFrame = new JFrame();

	static {
		topFrame.setAlwaysOnTop(true);
		topFrame.setUndecorated(true);
		topFrame.setSize(0, 0);
		topFrame.setLocationRelativeTo(null);
	}

	public static int retryMsg(String msg) {
		Object[] options = {" 重试 ", " 退出 "};
		return JOptionPane.showOptionDialog(topFrame, msg, "无法运行", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
	}

	public static void errorMsg(String msg) {
		JOptionPane.showMessageDialog(topFrame, msg, "错误", JOptionPane.ERROR_MESSAGE);
	}

	public static void infoMsg(String msg) {
		JOptionPane.showMessageDialog(topFrame, msg, "提示", JOptionPane.INFORMATION_MESSAGE);
	}

	public static String inputMsg(String msg) {
		return JOptionPane.showInputDialog(topFrame, msg, "输入", JOptionPane.PLAIN_MESSAGE);
	}

	public static int optionMsg(String msg, String title) {
		Object[] options = {" 确定 ", " 取消 "};
		return JOptionPane.showOptionDialog(topFrame, msg, title, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
	}
}
