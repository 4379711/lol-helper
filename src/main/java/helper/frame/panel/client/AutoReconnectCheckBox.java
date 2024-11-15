package helper.frame.panel.client;

import helper.cache.AppCache;
import helper.frame.panel.base.BaseCheckBox;
import helper.frame.utils.FrameConfigUtil;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author @_@
 * @date 2022/2/14
 */
public class AutoReconnectCheckBox extends BaseCheckBox {
	public AutoReconnectCheckBox() {
		this.setText("掉线自动重连");
		this.setSelected(AppCache.settingPersistence.getAutoReconnect());
		this.addItemListener(listener());
	}

	private ItemListener listener() {
		return e -> {
			AppCache.settingPersistence.setAutoReconnect(e.getStateChange() == ItemEvent.SELECTED);
			FrameConfigUtil.save();
		};
	}

}
