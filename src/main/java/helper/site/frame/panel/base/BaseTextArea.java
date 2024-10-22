package helper.site.frame.panel.base;

import helper.site.frame.ui.MyTabbedPaneUI;

import javax.swing.*;

/**
 * @author @_@
 */
public class BaseTextArea extends JTextArea {
	public BaseTextArea() {
		this.setBackground(MyTabbedPaneUI.END_COLOR_SELECT);
		this.setBorder(null);
	}
}
