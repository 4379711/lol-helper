package helper.frame.panel.client;

import helper.cache.FrameInnerCache;
import helper.frame.panel.base.BaseButton;
import lombok.extern.slf4j.Slf4j;

import java.awt.event.ActionListener;

/**
 * @author chengshuangxiong
 */
@Slf4j
public class CareerBackgroundBox extends BaseButton {

	public CareerBackgroundBox() {
		this.setText("选择生涯背景英雄");
		this.addActionListener(actionListener());
	}

	private ActionListener actionListener() {
		return e -> {
			if (FrameInnerCache.careerBackSkinFrame != null) {
				FrameInnerCache.careerBackSkinFrame.setVisible(true);
			} else {
				CareerBackSkinFrame skinFrame = new CareerBackSkinFrame("生涯背景英雄");
				FrameInnerCache.careerBackSkinFrame = skinFrame;
				skinFrame.setVisible(true);
			}
		};
	}

}
