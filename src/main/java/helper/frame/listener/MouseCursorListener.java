package helper.frame.listener;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 鼠标更改样式事件
 *
 * @author WuYi
 */
public class MouseCursorListener extends MouseAdapter {
    @Override
    public void mouseEntered(MouseEvent e) {
        e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // 将光标变为手指图标
        e.getComponent().repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        e.getComponent().setCursor(Cursor.getDefaultCursor()); // 将光标恢复为默认图标
        e.getComponent().repaint();
    }
}
