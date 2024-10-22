package helper.site.frame.panel.client;

import helper.site.bo.ChampionBO;
import helper.site.cache.AppCache;
import helper.site.cache.GameDataCache;
import helper.site.frame.panel.base.BaseButton;
import lombok.extern.slf4j.Slf4j;

import java.awt.event.ActionListener;

/**
 * @author @_@
 */
@Slf4j
public class BanBox extends BaseButton {
	private final BanBox banBox;

	public BanBox() {
		banBox = this;
		this.setText("禁用英雄");
		this.addActionListener(actionListener());
	}

	private ActionListener actionListener() {
		return e -> {
			ChampionSelectFrame selectFrame = new ChampionSelectFrame("禁用英雄", (name) -> {
				if (!GameDataCache.allChampion.isEmpty()) {
					ChampionBO championBO = GameDataCache.allChampion.stream().filter(i -> name.equals(i.getName())).findFirst().get();
					AppCache.settingPersistence.setBanChampionId(championBO.getId());
				}

				banBox.setText("禁用(" + name + ")");
			});
			selectFrame.setVisible(true);
		};
	}

}
