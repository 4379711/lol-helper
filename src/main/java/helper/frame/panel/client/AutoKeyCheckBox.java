package helper.frame.panel.client;

import helper.cache.AppCache;
import helper.frame.panel.base.BaseCheckBox;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author @_@
 * @date 2022/2/11
 */
public class AutoKeyCheckBox extends BaseCheckBox {

	public AutoKeyCheckBox() {
		this.setText("一键连招");
		this.setSelected(AppCache.settingPersistence.getAutoKey());
		this.addItemListener(listener());
	}

	private ItemListener listener() {
		return e -> AppCache.settingPersistence.setAutoKey(e.getStateChange() == ItemEvent.SELECTED);
	}

}
