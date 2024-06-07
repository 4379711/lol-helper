package yalong.site.frame.panel.client;

import yalong.site.cache.FrameUserSettingPersistence;
import yalong.site.frame.panel.base.BaseCheckBox;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author yaLong
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
