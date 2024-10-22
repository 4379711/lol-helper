package helper.site.frame.panel.base;

import javax.swing.*;
import java.awt.*;

/**
 * 圆角垂直组件
 *
 * @author WuYi
 */
public class RoundedVerticalPanel extends JPanel {
	private int cornerRadius;
	private Color bgColor;

	public RoundedVerticalPanel(int radius, Color color) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.cornerRadius = radius;
		this.bgColor = color;
		setOpaque(false); // 确保面板透明以显示圆角
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(bgColor);
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
	}

	@Override
	protected void paintBorder(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// 设置边框颜色和宽度
		g2.setColor(bgColor);
		g2.setStroke(new BasicStroke(2)); // 设置边框宽度
		g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
	}
}
