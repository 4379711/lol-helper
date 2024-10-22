package helper.site.frame.panel.client;

import helper.site.cache.AppCache;
import helper.site.frame.panel.base.BaseCheckBox;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author WuYi
 */
public class PickSkinCheckBox extends BaseCheckBox {
	public PickSkinCheckBox() {
		this.setText("选择炫彩");
		this.setSelected(AppCache.settingPersistence.getPickSkin());
		this.addItemListener(listener());
	}

	private ItemListener listener() {
		return e -> AppCache.settingPersistence.setPickSkin(e.getStateChange() == ItemEvent.SELECTED);
	}

}
