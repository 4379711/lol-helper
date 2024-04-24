package yalong.site.frame.utils;

import lombok.extern.slf4j.Slf4j;
import yalong.site.cache.FrameSetting;
import yalong.site.frame.panel.result.ResultTextPane;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.util.Date;

/**
 * @author yaLong
 * @date 2022/2/12
 */
@Slf4j
public class FrameMsgUtil {
	public static ResultTextPane resultPane = null;

	public static void clear() {
		if (resultPane != null) {
			resultPane.setText(null);
		}
	}

	public static void sendLine(String msg) {
		if ("".equals(msg) || msg == null) {
			return;
		}
		if (resultPane != null) {
			Document document = resultPane.getDocument();
			String time = FrameSetting.SIMPLE_DATE_FORMAT.format(new Date());
			try {
				document.insertString(document.getLength(), time, FrameSetting.GREEN_ATTR);
				document.insertString(document.getLength(), msg + System.lineSeparator(), FrameSetting.BLACK_ATTR);
			} catch (BadLocationException e) {
				log.error("面板消息错误", e);
			}
			//设置光标到最后一行
			resultPane.setCaretPosition(document.getLength());
		}
	}

}
