package yalong.site.frame.panel.client;

import yalong.site.cache.FrameUserSettingPersistence;
import yalong.site.frame.panel.base.BaseCheckBox;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author yaLong
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
