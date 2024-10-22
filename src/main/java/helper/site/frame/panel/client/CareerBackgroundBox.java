package helper.site.frame.panel.client;

import helper.site.bo.ChampionBO;
import helper.site.cache.AppCache;
import helper.site.cache.GameDataCache;
import helper.site.frame.panel.base.BaseButton;
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
					ChampionBO championBO = GameDataCache.allChampion.stream().filter(i -> name.equals(i.getName())).findFirst().get();
					AppCache.settingPersistence.setCareerChampionId(championBO.getId());
				}

			});
			selectFrame.setVisible(true);
		};
	}

}
