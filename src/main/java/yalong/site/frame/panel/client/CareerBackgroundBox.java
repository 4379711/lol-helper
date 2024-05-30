package yalong.site.frame.panel.client;

import lombok.extern.slf4j.Slf4j;
import yalong.site.bo.ChampionBO;
import yalong.site.cache.FrameUserSetting;
import yalong.site.cache.GameDataCache;
import yalong.site.frame.bo.ComponentBO;
import yalong.site.frame.panel.base.BaseButton;

import java.awt.*;
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

	/**
	 * @return 带布局的盒子
	 */
	public static ComponentBO builder() {
		CareerBackgroundBox box = new CareerBackgroundBox();
		GridBagConstraints grid = new GridBagConstraints(
				// 第(0,5)个格子
				1, 3,
				// 占1列,占1行
				1, 1,
				//横向占100%长度,纵向占100%长度
				1, 1,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.CENTER, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 0, 0, 0),
				// 增加组件的首选宽度和高度
				0, 0
		);
		return new ComponentBO(box, grid);
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
