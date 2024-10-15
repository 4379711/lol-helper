package helper.frame.panel.client;

import helper.vo.ChampionVO;
import helper.cache.FrameUserSetting;
import helper.cache.GameDataCache;
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
			ChampionSelectFrame selectFrame = new ChampionSelectFrame("选择生涯背景英雄", (name) -> {
				if (!GameDataCache.allChampion.isEmpty()) {
					ChampionVO championVO = GameDataCache.allChampion.stream().filter(i -> name.equals(i.getName())).findFirst().get();
					FrameUserSetting.careerChampionId = championVO.getId();
				}

			});
			selectFrame.setVisible(true);
		};
	}

}
