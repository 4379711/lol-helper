package yalong.site.frame.panel.client;

import lombok.extern.slf4j.Slf4j;
import yalong.site.bo.ProductsMatchHistoryBO;
import yalong.site.cache.AppCache;
import yalong.site.cache.FrameInnerCache;
import yalong.site.cache.FrameSetting;
import yalong.site.cache.GameDataCache;
import yalong.site.frame.bo.ComponentBO;
import yalong.site.frame.panel.base.BaseButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * @author yaLong
 */
@Slf4j
public class HistoryButton extends BaseButton {
	public HistoryButton() {
		this.setText("战绩查询");
		this.addActionListener(actionListener());
	}

	private ActionListener actionListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameDataCache.cacheLcuAll();
				if (FrameInnerCache.matchPanel == null && FrameInnerCache.matchFrame == null) {
					JFrame jFrame = new JFrame("战绩查询");
					FrameInnerCache.matchFrame = jFrame;
					Image image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/assets/logo.png"));
					jFrame.setIconImage(image);
					jFrame.setSize(FrameSetting.MATCH_WIDTH, FrameSetting.MATCH_HEIGHT);
					//窗口居中
					jFrame.setLocationRelativeTo(null);

					MatchPanel matchPanel = new MatchPanel();
					jFrame.add(matchPanel);
					jFrame.setVisible(true);
					if (GameDataCache.me != null) {
						try {
							ProductsMatchHistoryBO pmh = AppCache.api.getProductsMatchHistoryByPuuid(GameDataCache.me.getPuuid(), 0, FrameSetting.PAGE_SIZE - 1);
							matchPanel.setData(pmh, GameDataCache.me.getPuuid());
						} catch (IOException ex) {
							throw new RuntimeException(ex);
						}
					}
				} else {
					FrameInnerCache.matchFrame.setVisible(true);
				}

			}
		};
	}

	/**
	 * @return 带布局的盒子
	 */
	public static ComponentBO builder() {
		HistoryButton box = new HistoryButton();
		GridBagConstraints grid = new GridBagConstraints(
				// 第(0,3)个格子
				2, 4,
				// 占1列,占1行
				1, 1,
				//横向占100%长度,纵向占100%长度
				1, 1,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.CENTER, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 0, 0, 0),
				// 增加组件的首选宽度和高度
				0, 0
		);
		return new ComponentBO(box, grid);
	}

}
