package helper.frame.panel.client;

import helper.cache.FrameUserSettingPersistence;
import helper.frame.panel.base.BaseCheckBox;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author @_@
 * @date 2022/2/14
 */
public class AutoAcceptCheckBox extends BaseCheckBox {
	public AutoAcceptCheckBox() {
		this.setText("自动接受对局");
		this.setSelected(FrameUserSettingPersistence.autoAccept);
		this.addItemListener(listener());
	}

	private ItemListener listener() {
		return e -> FrameUserSettingPersistence.autoAccept = e.getStateChange() == ItemEvent.SELECTED;
	}

}
