package helper.frame.panel.client;

import helper.cache.FrameInnerCache;
import helper.frame.panel.base.BaseButton;
import lombok.extern.slf4j.Slf4j;

import java.awt.event.ActionListener;

/**
 * @author @_@
 */
@Slf4j
public class BanBox extends BaseButton {

	public BanBox() {
		this.setText("禁用英雄");
		this.addActionListener(actionListener());
	}

	private ActionListener actionListener() {
		return e -> {
			if (FrameInnerCache.championBanFrame != null) {
				FrameInnerCache.championBanFrame.setVisible(true);
			} else {
				ChampionBanFrame selectFrame = new ChampionBanFrame("禁用英雄");
				FrameInnerCache.championBanFrame = selectFrame;
				selectFrame.setVisible(true);
			}
		};
	}

}
