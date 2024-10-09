package helper.frame.panel.history;

import helper.bo.ProductsMatchHistoryBO;
import helper.cache.AppCache;
import helper.cache.FrameInnerCache;
import helper.cache.FrameSetting;
import helper.cache.GameDataCache;
import helper.frame.panel.base.BaseButton;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * @author @_@
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
							matchPanel.resetIndex();
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
