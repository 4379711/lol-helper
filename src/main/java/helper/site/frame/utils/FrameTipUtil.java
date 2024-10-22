package helper.site.frame.utils;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;

/**
 * @author @_@
 */
@Slf4j
public class FrameTipUtil {
	public static int continueRun(String msg) {
		Object[] options = {" 重试 ", " 退出 "};
		return JOptionPane.showOptionDialog(null, msg, "无法运行", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
	}

	public static void errorOccur(String msg) {
		JOptionPane.showMessageDialog(null, msg, "错误", JOptionPane.ERROR_MESSAGE);
	}
}
