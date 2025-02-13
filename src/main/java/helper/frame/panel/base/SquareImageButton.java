package helper.frame.panel.base;

import helper.frame.listener.MouseCursorListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author WuYi
 */
public class SquareImageButton extends JButton {
    private final Image image;
    private final Image imageHover;
    private boolean hovered = false;

    public SquareImageButton(Image image, Dimension size) {
        this(image, null, size);
    }

    public SquareImageButton(Image image, Image imageHover, Dimension size) {
        this.image = image;
        this.imageHover = imageHover;
        setPreferredSize(size);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // 添加鼠标事件监听器
        addMouseListener(new MouseCursorListener());
        if (imageHover != null) {
            addMouseListener(new HoverListener());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        // 启用抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 绘制图片
        if ((getModel().isArmed() || hovered) && imageHover != null) {
            g2d.drawImage(imageHover, 0, 0, getWidth(), getHeight(), this);
        } else {
            g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
        g2d.dispose();
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // 不绘制边框
    }

    @Override
    public boolean contains(int x, int y) {
        Shape shape = new Rectangle(0, 0, getWidth(), getHeight());
        return shape.contains(x, y);
    }

    // 悬停监听器
    private class HoverListener extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            hovered = true;
            repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            hovered = false;
            repaint();
        }
    }
}
