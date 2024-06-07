package yalong.site.frame.panel.client;

import yalong.site.cache.FrameUserSettingPersistence;
import yalong.site.frame.panel.base.BaseCheckBox;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author yaLong
 * @date 2022/2/14
 */
public class AutoReconnectCheckBox extends BaseCheckBox {
	public AutoReconnectCheckBox() {
		this.setText("掉线自动重连");
		this.setSelected(FrameUserSettingPersistence.autoReconnect);
		this.addItemListener(listener());
	}

	private ItemListener listener() {
		return e -> FrameUserSettingPersistence.autoReconnect = e.getStateChange() == ItemEvent.SELECTED;
	}

}
