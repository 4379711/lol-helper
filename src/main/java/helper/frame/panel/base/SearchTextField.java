package helper.frame.panel.base;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * 重写圆角搜索框 根据构造函数传入的size确定大小
 *
 * @author WuYi
 */
public class SearchTextField extends JTextField {
	private Shape shape;

	public SearchTextField(int size) {
		super(size);
		setOpaque(false);
	}

	protected void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		// 开启抗锯齿
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setColor(getBackground());
		g2D.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
		super.paintComponent(g2D);
	}

	protected void paintBorder(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		// 开启抗锯齿
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setColor(getForeground());
		g2D.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
	}

	public boolean contains(int x, int y) {
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
		}
		return shape.contains(x, y);
	}
}
