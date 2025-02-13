package helper.frame.panel;

import helper.frame.panel.about.AboutPanel;
import helper.frame.panel.client.ClientPanel;
import helper.frame.panel.fuckword.FuckPanel;
import helper.frame.panel.history.HistoryPane;
import helper.frame.panel.keymapping.ScrollableJPanel;
import helper.frame.panel.result.ResultPanel;

import javax.swing.*;
import java.awt.*;

/**
 * 导航页签
 *
 * @author @_@
 * @date 2022/2/11
 */
public class TabPane extends JTabbedPane {

	static {
		//设置外边距,因为是左侧布局,所以top代表左边距,left代表上边距
		UIManager.put("TabbedPane.tabAreaInsets", new Insets(10, 8, 0, 0));
	}

	public TabPane() {
		super(JTabbedPane.LEFT);
	}

	public static TabPane builder() {
		TabPane tabPane = new TabPane();
		tabPane.add(ClientPanel.builder());
		tabPane.add(HistoryPane.builder());
		tabPane.add(ScrollableJPanel.build());
		tabPane.add(FuckPanel.builder());
		tabPane.add(ResultPanel.builder());
		tabPane.add(AboutPanel.builder());
		return tabPane;
	}

}
