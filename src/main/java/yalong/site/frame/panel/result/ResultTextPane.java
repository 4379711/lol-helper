package yalong.site.frame.panel.result;

import yalong.site.frame.panel.TabPane;
import yalong.site.frame.ui.MyTabbedPaneUI;
import yalong.site.frame.utils.FrameMsgUtil;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author yaLong
 */
public class ResultTextPane extends JTextPane {

    public ResultTextPane() {
        //设置不可编辑
        this.setEditable(false);
        this.setOpaque(false);
        this.setBackground(MyTabbedPaneUI.END_COLOR_SELECT);
        this.setBorder(null);
        //设置全局结果展示面板
        FrameMsgUtil.resultPane = this;
        FrameMsgUtil.sendLine("双击鼠标左键可以清空屏幕内容");
        // 双击清屏
        this.addMouseListener(clear());
        // 出现信息时,自动跳转到tab面板
        this.getDocument().addDocumentListener(link());
    }

    private DocumentListener link() {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                ResultPanel resultPanel = (ResultPanel) getParent().getParent();
                TabPane tabPane = (TabPane) resultPanel.getParent();
                tabPane.setSelectedComponent(resultPanel);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        };
    }

    private MouseListener clear() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //如果是双击,清屏
                if (e.getClickCount() == 2) {
                    FrameMsgUtil.clear();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }

    public static ResultTextPane builder() {
        return new ResultTextPane();
    }

}
