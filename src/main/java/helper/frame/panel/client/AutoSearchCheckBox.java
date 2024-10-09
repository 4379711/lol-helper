package helper.frame.panel.client;

import helper.cache.FrameUserSettingPersistence;
import helper.frame.panel.base.BaseCheckBox;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author @_@
 */
public class AutoSearchCheckBox extends BaseCheckBox {
	public AutoSearchCheckBox() {
		this.setText("自动寻找对局");
		this.setSelected(FrameUserSettingPersistence.autoSearch);
		this.addItemListener(listener());
	}

	private ItemListener listener() {
		return e -> FrameUserSettingPersistence.autoSearch = e.getStateChange() == ItemEvent.SELECTED;
	}

}
