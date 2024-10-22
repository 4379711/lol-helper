package helper.site.utils;

import cn.hutool.core.img.ImgUtil;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import static cn.hutool.core.img.ImgUtil.IMAGE_TYPE_PNG;

/**
 * @author WuYi
 */
public class ImageUtil {

	/**
	 * 设置圆角
	 *
	 * @param image 图像
	 */
	public static Image makeRoundedCorner(Image image) {
		BufferedImage bufferedImage = ImgUtil.castToBufferedImage(image, IMAGE_TYPE_PNG);
		int size = Math.min(bufferedImage.getWidth(), bufferedImage.getHeight());

		// 放大图像
		int extraSize = 9; // 增加的尺寸用于放大和消除黑色边缘
		int newSize = size + extraSize;
		Image scaledImage = bufferedImage.getScaledInstance(newSize, newSize, Image.SCALE_SMOOTH);
		BufferedImage scaledBufferedImage = ImgUtil.castToBufferedImage(scaledImage, IMAGE_TYPE_PNG);

		// 创建一个新的 BufferedImage 用于存储圆形图像
		BufferedImage circularImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2 = circularImage.createGraphics();
		// 开启抗锯齿
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

		// 绘制圆形剪切区域
		g2.setClip(new Ellipse2D.Float(0, 0, size, size));

		// 计算图像绘制的起始位置，使得图像在圆形区域内居中
		int x = (newSize - size) / 2;
		int y = (newSize - size) / 2;

		g2.drawImage(scaledBufferedImage, -x, -y, newSize, newSize, null);
		g2.dispose();

		return circularImage;
	}

	/**
	 * @param image 图片
	 * @param text  文本
	 */
	public static Image addNumberToImage(Image image, String text) {

		int iconWidth = image.getWidth(null);
		int iconHeight = image.getHeight(null);

		// 将 Image 转换为 BufferedImage 以进行操作
		BufferedImage bufferedImage = new BufferedImage(iconWidth, iconHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bufferedImage.createGraphics();

		// 将原始图像绘制到 BufferedImage 上
		g2d.drawImage(image, 0, 0, null);

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// 设置字体和颜色
		Font font = new Font("Arial", Font.BOLD, 10);
		g2d.setFont(font);
		g2d.setColor(Color.green);

		// 计算文本的位置
		FontMetrics fm = g2d.getFontMetrics();
		int textWidth = fm.stringWidth(text);
		int x = (iconWidth - textWidth) / 2;
		int y = iconHeight - fm.getHeight() + fm.getAscent();

		// 绘制文本
		g2d.drawString(text, x, y);
		g2d.dispose();

		return bufferedImage;
	}

}
