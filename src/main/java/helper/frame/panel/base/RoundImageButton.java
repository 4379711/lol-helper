package helper.frame.panel.base;

import helper.frame.listener.MouseCursorListener;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * @author WuYi
 */
public class RoundImageButton extends JButton {
    private final Image image;

    public RoundImageButton(Image image, Dimension size) {
        this.image = image;
        setPreferredSize(size);
        setContentAreaFilled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // 添加鼠标事件监听器
        addMouseListener(new MouseCursorListener());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        // 启用抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isArmed()) {
            g2d.setColor(Color.LIGHT_GRAY);
        } else {
            g2d.setColor(getBackground());
        }
        g2d.fillOval(0, 0, getSize().width, getSize().height);
        g2d.setClip(new Ellipse2D.Float(0, 0, getWidth(), getHeight()));
        g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        super.paintComponent(g2d);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // 不绘制边框
    }

    @Override
    public boolean contains(int x, int y) {
        Shape shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
        return shape.contains(x, y);
    }
}
