package helper.frame.panel.history;

import helper.bo.SpgProductsMatchHistoryBO;
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
import java.util.List;

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
				if (GameDataCache.leagueClient.getRegion() == null) {
					JOptionPane.showMessageDialog(null, "战绩查询暂仅支持国服 外服战绩请直接使用OPGG查询获得更好体验", "提示", JOptionPane.ERROR_MESSAGE);
				} else {
					JFrame jFrame = new JFrame("战绩查询");
					FrameInnerCache.sgpRecordFrame = jFrame;
					Image image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/assets/logo.png"));
					jFrame.setIconImage(image);
					jFrame.setSize(FrameSetting.MATCH_WIDTH, FrameSetting.MATCH_HEIGHT);
					//窗口居中
					jFrame.setLocationRelativeTo(null);

					SGPRecordPanel panel = new SGPRecordPanel();
					jFrame.add(panel);
					jFrame.setVisible(true);
					if (GameDataCache.me != null) {
						try {
							List<SpgProductsMatchHistoryBO> sgpRecordList = AppCache.sgpApi.getProductsMatchHistoryByPuuid(GameDataCache.leagueClient.getRegion(), GameDataCache.me.getPuuid(), 0, FrameSetting.PAGE_SIZE);
							panel.setData(sgpRecordList, GameDataCache.me.getPuuid(), null);
						} catch (IOException ex) {
							throw new RuntimeException(ex);
						}
					}
				}
			}

		};
	}

}
