package yalong.site.frame.panel.client;

import yalong.site.cache.FrameUserSettingPersistence;
import yalong.site.frame.panel.base.BaseCheckBox;

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

	private ItemListener listener() {
		return e -> FrameUserSettingPersistence.sendScore = e.getStateChange() == ItemEvent.SELECTED;

	}

}
