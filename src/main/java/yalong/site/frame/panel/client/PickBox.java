package yalong.site.frame.panel.client;

import lombok.extern.slf4j.Slf4j;
import yalong.site.bo.ChampionBO;
import yalong.site.cache.FrameUserSetting;
import yalong.site.cache.GameDataCache;
import yalong.site.frame.panel.base.BaseButton;

import java.awt.event.ActionListener;

/**
 * @author yaLong
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
				FrameUserSetting.pickChampionId = championBO.getId();
				pickBox.setText("秒选(" + name + ")");
			});
			selectFrame.setVisible(true);
		};
	}

}
