package yalong.site.frame.panel;

import yalong.site.frame.panel.about.AboutPanel;
import yalong.site.frame.panel.client.ClientPanel;
import yalong.site.frame.panel.result.ResultPanel;
import yalong.site.frame.ui.MyTabbedPaneUI;

import javax.swing.*;
import java.awt.*;

/**
 * 导航页签
 *
 * @author yaLong
 * @date 2022/2/11
 */
public class TabPane extends JTabbedPane {

    static {
        //设置外边距,因为是左侧布局,所以top代表左边距,left代表上边距
        UIManager.put("TabbedPane.tabAreaInsets", new Insets(10, 8, 0, 0));
    }

    public TabPane() {
        super(JTabbedPane.LEFT);
        this.setUI(new MyTabbedPaneUI());
    }

    public static TabPane builder() {
        TabPane tabPane = new TabPane();
        tabPane.add(ClientPanel.builder());
        tabPane.add(ResultPanel.builder());
        tabPane.add(AboutPanel.builder());
        return tabPane;
    }

}
