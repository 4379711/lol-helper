package helper.frame.panel.client;

import helper.cache.AppCache;
import helper.frame.panel.base.BaseCheckBox;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author @_@
 * @date 2022/2/11
 */
public class CommunicateCheckBox extends BaseCheckBox {
	public CommunicateCheckBox() {
		this.setText("互动模式");
		this.setSelected(AppCache.settingPersistence.getCommunicate());
		this.addItemListener(listener());
	}

	private ItemListener listener() {
		return e -> AppCache.settingPersistence.setCommunicate(e.getStateChange() == ItemEvent.SELECTED);
	}

}
