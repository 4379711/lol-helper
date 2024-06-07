package yalong.site.frame.panel.client;

import lombok.extern.slf4j.Slf4j;
import yalong.site.bo.ProductsMatchHistoryBO;
import yalong.site.cache.AppCache;
import yalong.site.cache.FrameInnerCache;
import yalong.site.cache.FrameSetting;
import yalong.site.cache.GameDataCache;
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

}
