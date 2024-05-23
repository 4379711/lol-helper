package yalong.site.frame.panel.key;

import yalong.site.frame.panel.base.BasePanel;

import javax.swing.*;

/**
 * 自定义按键
 *
 * @author yaLong
 */
public class KeyPanel extends BasePanel {
	public KeyPanel() {
		this.setName("一键连招");
		// 设置透明
		this.setOpaque(false);
		this.setBorder(null);
	}

	public static KeyPanel builder() {
		KeyPanel keyPanel = new KeyPanel();
		BoxLayout layout = new BoxLayout(keyPanel, BoxLayout.Y_AXIS);
		keyPanel.setLayout(layout);
		keyPanel.add(HotKeySelectBox.builder());
		keyPanel.add(KeySelectBox.builder());
		keyPanel.add(KeyTextPane.builder());
		keyPanel.add(KeySaveBox.builder());
		return keyPanel;
	}

}
