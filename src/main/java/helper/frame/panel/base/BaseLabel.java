package helper.frame.panel.base;

import javax.swing.*;
import java.awt.*;

/**
 * @author WuYi
 */
public class BaseLabel extends JLabel {
	private Color backgroundColor;
	private int arcWidth;
	private int arcHeight;
	private Dimension fixedSize; // 用于存储固定大小

	public BaseLabel(String text, Color bgColor, int arcWidth, int arcHeight) {
		super(text);
		this.backgroundColor = bgColor;
		this.arcWidth = arcWidth;
		this.arcHeight = arcHeight;

		setOpaque(false); // 使组件透明，以便显示自定义背景
		setForeground(Color.WHITE); // 设置文本颜色
		setHorizontalAlignment(SwingConstants.CENTER); // 文本居中
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();

		// 启用抗锯齿
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// 设置背景颜色并绘制圆角矩形
		g2.setColor(backgroundColor);
		g2.fillRoundRect(-1, -1, getWidth() + 1, getHeight() + 1, arcWidth, arcHeight);

		// 调用父类的 paintComponent 方法，以绘制文本
		super.paintComponent(g2);
		g2.dispose();
	}

	@Override
	protected void paintBorder(Graphics g) {
		// 如果需要绘制边框，可以在此处添加代码
		//g.drawRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
	}
}