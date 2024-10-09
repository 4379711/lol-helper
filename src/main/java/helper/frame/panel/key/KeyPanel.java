package helper.frame.panel.key;

import helper.frame.panel.base.BasePanel;

import javax.swing.*;

/**
 * 自定义按键
 *
 * @author @_@
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
		keyPanel.add(SelectPanel.builder());
		keyPanel.add(KeyTextPane.builder());
		return keyPanel;
	}

}
