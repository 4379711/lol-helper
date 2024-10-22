package helper.site.frame.panel.key;

import helper.site.frame.panel.base.BasePanel;

import javax.swing.*;

/**
 * 自定义按键
 *
 * @author @_@
 */
public class SelectPanel extends BasePanel {
	public SelectPanel() {
		// 设置透明
		this.setOpaque(false);
		this.setBorder(null);
	}

	public static SelectPanel builder() {
		SelectPanel keyPanel = new SelectPanel();
		BoxLayout layout = new BoxLayout(keyPanel, BoxLayout.X_AXIS);
		keyPanel.setLayout(layout);
		keyPanel.add(HotKeySelectBox.builder());
		keyPanel.add(KeySelectBox.builder());
		return keyPanel;
	}

}
