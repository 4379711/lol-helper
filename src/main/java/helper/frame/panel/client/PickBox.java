package helper.frame.panel.client;

import helper.bo.ChampionBO;
import helper.cache.AppCache;
import helper.cache.GameDataCache;
import helper.frame.panel.base.BaseButton;
import helper.frame.utils.FrameConfigUtil;
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
				FrameConfigUtil.save();
				pickBox.setText("秒选(" + name + ")");
			});
			selectFrame.setVisible(true);
		};
	}

}
