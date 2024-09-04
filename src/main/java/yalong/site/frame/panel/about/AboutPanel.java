package yalong.site.frame.panel.about;

import yalong.site.frame.panel.base.BasePanel;
import yalong.site.frame.panel.base.BaseTextArea;

import java.awt.*;

/**
 * @author yaLong
 * @date 2022/2/11
 */
public class AboutPanel extends BasePanel {

	public AboutPanel() {
		this.setName("关  于");
	}

	public static AboutPanel builder() {
		AboutPanel aboutPanel = new AboutPanel();
		BaseTextArea textArea = new BaseTextArea();
		textArea.setOpaque(false);
		textArea.setText(
				"本软件永久免费\n"
						+ "加入qq群获取最新版本\n"
						+ "qq群号:882050965(已满加入2群)\n"
						+ "qq2群号:1001245686"
		);
		textArea.setEditable(false);
		textArea.setForeground(Color.red);
		aboutPanel.add(textArea);
		return aboutPanel;
	}

}
