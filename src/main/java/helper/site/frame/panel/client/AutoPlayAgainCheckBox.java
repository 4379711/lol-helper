package helper.site.frame.panel.client;

import helper.site.cache.AppCache;
import helper.site.frame.panel.base.BaseCheckBox;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author @_@
 * @date 2024/4/23
 */
public class AutoPlayAgainCheckBox extends BaseCheckBox {
	public AutoPlayAgainCheckBox() {
		this.setText("自动再来一局");
		this.setSelected(AppCache.settingPersistence.getAutoPlayAgain());
		this.addItemListener(listener());
	}

	private ItemListener listener() {
		return e -> AppCache.settingPersistence.setAutoPlayAgain(e.getStateChange() == ItemEvent.SELECTED);
	}

}
