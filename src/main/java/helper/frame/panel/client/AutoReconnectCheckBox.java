package helper.frame.panel.client;

import helper.cache.FrameUserSettingPersistence;
import helper.frame.panel.base.BaseCheckBox;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author @_@
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
