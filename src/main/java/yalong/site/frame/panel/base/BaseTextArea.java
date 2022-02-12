package yalong.site.frame.panel.base;

import yalong.site.frame.ui.MyTabbedPaneUI;

import javax.swing.*;

/**
 * @author yaLong
 */
public class BaseTextArea extends JTextArea {
    public BaseTextArea() {
        this.setBackground(MyTabbedPaneUI.END_COLOR_SELECT);
        this.setBorder(null);
    }
}
