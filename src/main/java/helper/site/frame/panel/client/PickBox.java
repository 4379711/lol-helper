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
public class PickBox extends BaseButton {
	private final PickBox pickBox;

	public PickBox() {
		pickBox = this;
		this.setText("秒选英雄");
		this.addActionListener(actionListener());
	}

	private ActionListener actionListener() {
		return e -> {
			ChampionSelectFrame selectFrame = new ChampionSelectFrame("选择英雄", (name) -> {
				ChampionBO championBO = GameDataCache.allChampion.stream().filter(i -> name.equals(i.getName())).findFirst().get();
				AppCache.settingPersistence.setPickChampionId(championBO.getId());
				pickBox.setText("秒选(" + name + ")");
			});
			selectFrame.setVisible(true);
		};
	}

}
