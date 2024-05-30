package yalong.site.frame.panel.client;

import yalong.site.cache.FrameUserSettingPersistence;
import yalong.site.frame.bo.ComponentBO;
import yalong.site.frame.panel.base.BaseCheckBox;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author yaLong
 * @date 2022/2/14
 */
public class SendScoreCheckBox extends BaseCheckBox {

	public SendScoreCheckBox() {
		this.setText("发送战绩");
		this.setSelected(FrameUserSettingPersistence.sendScore);
		this.addItemListener(listener());
	}

	/**
	 * @return 带布局的盒子
	 */
	public static ComponentBO builder() {
		SendScoreCheckBox box = new SendScoreCheckBox();
		GridBagConstraints grid = new GridBagConstraints(
				// 第(0,2)个格子
				0, 2,
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

	private ItemListener listener() {
		return e -> FrameUserSettingPersistence.sendScore = e.getStateChange() == ItemEvent.SELECTED;

	}

}
