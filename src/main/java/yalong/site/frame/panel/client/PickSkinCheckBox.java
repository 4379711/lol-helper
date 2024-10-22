package yalong.site.frame.panel.client;

import yalong.site.cache.FrameUserSettingPersistence;
import yalong.site.frame.panel.base.BaseCheckBox;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author WuYi
 */
public class PickSkinCheckBox extends BaseCheckBox {
	public PickSkinCheckBox() {
		this.setText("选择炫彩");
		this.setSelected(FrameUserSettingPersistence.pickSkin);
		this.addItemListener(listener());
	}

	private ItemListener listener() {
		return e -> FrameUserSettingPersistence.pickSkin = e.getStateChange() == ItemEvent.SELECTED;
	}

}
