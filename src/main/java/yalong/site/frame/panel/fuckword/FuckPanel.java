package yalong.site.frame.panel.fuckword;

import yalong.site.cache.FrameSetting;

import javax.swing.*;
import java.awt.*;

/**
 * 喷人语录
 *
 * @author yaLong
 */
public class FuckPanel extends JScrollPane {
	public FuckPanel(FuckTextPane textPane) {
		super(textPane);
		this.setName("喷人语录");
		// 设置透明
		this.setOpaque(false);
		this.setBorder(null);
		this.getViewport().setOpaque(false);
		this.setAutoscrolls(true);
		this.setMaximumSize(new Dimension(FrameSetting.WIDTH, FrameSetting.HEIGHT));
	}

	public static FuckPanel builder() {
		return new FuckPanel(FuckTextPane.builder());

	}

}
