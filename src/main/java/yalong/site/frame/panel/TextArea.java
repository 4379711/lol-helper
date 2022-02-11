package yalong.site.frame.panel;

import yalong.site.frame.ui.MyTabbedPaneUI;

import javax.swing.*;

/**
 * @author yaLong
 */
public class TextArea extends JTextArea {
    public TextArea() {
        this.setBackground(MyTabbedPaneUI.END_COLOR_SELECT);
        this.setLineWrap(false);
        this.setBorder(null);
    }
}
