package helper.frame.panel.base;

import helper.frame.constant.ColorConstant;

import java.awt.*;

/**
 * 水平分割线
 *
 * @author WuYi
 */
public class HorizontalDivider extends Canvas {
	private int width;

	public HorizontalDivider(int width) {
		this.width = width;
		setBackground(ColorConstant.DARK_THREE); // 设置背景为透明
	}

	@Override
	public void paint(Graphics g) {
		//g.setColor(Color.GRAY);
		g.fillRect(0, getHeight() / 2 - 1, width, 1); // 分隔线宽度为2像素
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, 1); // 设置分隔线高度为1像素
	}

	@Override
	public void update(Graphics g) {
		paint(g); // 覆盖update方法，防止背景重绘
	}
}
