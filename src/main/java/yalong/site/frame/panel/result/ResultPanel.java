package yalong.site.frame.panel.result;

import yalong.site.cache.FrameSetting;

import javax.swing.*;
import java.awt.*;

/**
 * 输入程序运行结果
 *
 * @author yaLong
 */
public class ResultPanel extends JScrollPane {
	public ResultPanel(ResultTextPane area) {
		super(area);
		this.setName("帮助信息");
		// 设置透明
		this.setOpaque(false);
		this.setBorder(null);
		this.getViewport().setOpaque(false);
		this.setAutoscrolls(true);
		this.setMaximumSize(new Dimension(FrameSetting.WIDTH, FrameSetting.HEIGHT));
	}

	public static ResultPanel builder() {
		ResultTextPane pane = ResultTextPane.builder();
		return new ResultPanel(pane);
	}

}
