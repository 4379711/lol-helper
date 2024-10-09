package helper.frame.panel.client;

import helper.cache.FrameUserSettingPersistence;
import helper.frame.panel.base.BaseCheckBox;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * 选人阶段显示队友战绩
 *
 * @author WuYi
 */
public class ShowTeamBox extends BaseCheckBox {
	public ShowTeamBox() {
		this.setText("显示队友战绩");
		this.setSelected(FrameUserSettingPersistence.showMatchHistory);
		this.addItemListener(listener());
	}

	private ItemListener listener() {
		return e -> FrameUserSettingPersistence.showMatchHistory = e.getStateChange() == ItemEvent.SELECTED;

	}
}
