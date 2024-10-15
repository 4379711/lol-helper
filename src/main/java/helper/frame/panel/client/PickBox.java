package helper.frame.panel.client;

import helper.vo.ChampionVO;
import helper.cache.FrameUserSetting;
import helper.cache.GameDataCache;
import helper.frame.panel.base.BaseButton;
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
				ChampionVO championVO = GameDataCache.allChampion.stream().filter(i -> name.equals(i.getName())).findFirst().get();
				FrameUserSetting.pickChampionId = championVO.getId();
				pickBox.setText("秒选(" + name + ")");
			});
			selectFrame.setVisible(true);
		};
	}

}
