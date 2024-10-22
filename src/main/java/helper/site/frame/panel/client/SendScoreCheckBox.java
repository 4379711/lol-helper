package helper.site.frame.panel.client;

import helper.site.cache.AppCache;
import helper.site.frame.panel.base.BaseCheckBox;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author @_@
 * @date 2022/2/14
 */
public class SendScoreCheckBox extends BaseCheckBox {

	public SendScoreCheckBox() {
		this.setText("发送战绩");
		this.setSelected(AppCache.settingPersistence.getSendScore());
		this.addItemListener(listener());
	}

	private ItemListener listener() {
		return e -> AppCache.settingPersistence.setSendScore(e.getStateChange() == ItemEvent.SELECTED);

	}

}
