package helper.frame.panel.client;

import helper.cache.FrameUserSettingPersistence;
import helper.frame.panel.base.BaseCheckBox;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author @_@
 * @date 2022/2/14
 */
public class SendScoreCheckBox extends BaseCheckBox {

	public SendScoreCheckBox() {
		this.setText("发送战绩");
		this.setSelected(FrameUserSettingPersistence.sendScore);
		this.addItemListener(listener());
	}

	private ItemListener listener() {
		return e -> FrameUserSettingPersistence.sendScore = e.getStateChange() == ItemEvent.SELECTED;

	}

}
