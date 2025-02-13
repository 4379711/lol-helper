package helper.frame.panel.base;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author WuYi
 */
public class HoverImage extends JLabel {
    private boolean hovered = false;
    private final ImageIcon overlayIcon;

    public HoverImage(Icon icon, ImageIcon overlayIcon, MouseAdapter clickListener) {
        super(icon);
        this.overlayIcon = overlayIcon;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovered = true;
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // 将光标变为手指图标
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovered = false;
                setCursor(Cursor.getDefaultCursor()); // 将光标恢复为默认图标
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                clickListener.mouseClicked(e);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (hovered) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.SrcOver.derive(0.8f)); // 设置透明度为80%
            g2d.drawImage(overlayIcon.getImage(), 0, 0, getWidth(), getHeight(), null); // 绘制覆盖图标
            g2d.dispose();
        }
    }
}
