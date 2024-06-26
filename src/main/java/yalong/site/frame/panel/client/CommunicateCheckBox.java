package yalong.site.frame.panel.client;

import yalong.site.cache.FrameUserSettingPersistence;
import yalong.site.frame.panel.base.BaseCheckBox;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author yaLong
 * @date 2022/2/11
 */
public class CommunicateCheckBox extends BaseCheckBox {
	public CommunicateCheckBox() {
		this.setText("互动模式");
		this.setSelected(FrameUserSettingPersistence.communicate);
		this.addItemListener(listener());
	}

	private ItemListener listener() {
		return e -> FrameUserSettingPersistence.communicate = e.getStateChange() == ItemEvent.SELECTED;
	}

}
