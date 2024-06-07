package yalong.site.frame.panel.client;

import lombok.extern.slf4j.Slf4j;
import yalong.site.bo.ChampionBO;
import yalong.site.cache.FrameUserSetting;
import yalong.site.cache.GameDataCache;
import yalong.site.frame.panel.base.BaseButton;

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
					ChampionBO championBO = GameDataCache.allChampion.stream().filter(i -> name.equals(i.getName())).findFirst().get();
					FrameUserSetting.careerChampionId = championBO.getId();
				}

			});
			selectFrame.setVisible(true);
		};
	}

}
