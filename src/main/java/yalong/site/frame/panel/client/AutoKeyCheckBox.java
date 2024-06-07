package yalong.site.frame.panel.client;

import yalong.site.cache.FrameUserSettingPersistence;
import yalong.site.frame.panel.base.BaseCheckBox;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author yaLong
 * @date 2022/2/11
 */
public class AutoKeyCheckBox extends BaseCheckBox {

	public AutoKeyCheckBox() {
		this.setText("一键连招");
		this.setSelected(FrameUserSettingPersistence.autoKey);
		this.addItemListener(listener());
	}

	private ItemListener listener() {
		return e -> FrameUserSettingPersistence.autoKey = e.getStateChange() == ItemEvent.SELECTED;
	}

}
