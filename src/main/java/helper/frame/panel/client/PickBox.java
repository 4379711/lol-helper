package helper.frame.panel.client;

import helper.cache.FrameInnerCache;
import helper.frame.panel.base.BaseButton;
import lombok.extern.slf4j.Slf4j;

import java.awt.event.ActionListener;

/**
 * @author @_@
 */
@Slf4j
public class PickBox extends BaseButton {

	public PickBox() {
		this.setText("秒选英雄");
		this.addActionListener(actionListener());
	}

	private ActionListener actionListener() {
		return e -> {
			if (FrameInnerCache.championSelectFrame != null) {
				FrameInnerCache.championSelectFrame.setVisible(true);
			} else {
				ChampionSelectFrame selectFrame = new ChampionSelectFrame("选择英雄");
				FrameInnerCache.championSelectFrame = selectFrame;
				selectFrame.setVisible(true);
			}
		};
	}

}
