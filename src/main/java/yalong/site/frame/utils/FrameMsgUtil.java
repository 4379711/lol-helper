package yalong.site.frame.utils;

import yalong.site.bo.GlobalData;
import yalong.site.frame.panel.result.ResultTextPane;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.util.Date;

import static yalong.site.bo.GlobalData.SIMPLE_DATE_FORMAT;

/**
 * @author yaLong
 * @date 2022/2/12
 */
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
            String time = SIMPLE_DATE_FORMAT.format(new Date());
            try {
                document.insertString(document.getLength(), time, GlobalData.GREEN_ATTR);
                document.insertString(document.getLength(), msg + System.lineSeparator(), GlobalData.BLACK_ATTR);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
            //设置光标到最后一行
            resultPane.setCaretPosition(document.getLength());
        }
    }


}
