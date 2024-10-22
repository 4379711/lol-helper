package helper.frame.panel.client;

import helper.cache.AppCache;
import helper.frame.panel.base.BaseCheckBox;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author @_@
 */
public class AutoSearchCheckBox extends BaseCheckBox {
	public AutoSearchCheckBox() {
		this.setText("自动寻找对局");
		this.setSelected(AppCache.settingPersistence.getAutoSearch());
		this.addItemListener(listener());
	}

	private ItemListener listener() {
		return e -> AppCache.settingPersistence.setAutoSearch(e.getStateChange() == ItemEvent.SELECTED);
	}

}
