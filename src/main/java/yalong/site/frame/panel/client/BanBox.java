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
					FrameUserSetting.banChampionId = championBO.getId();
				}

				banBox.setText("禁用(" + name + ")");
			});
			selectFrame.setVisible(true);
		};
	}

}
