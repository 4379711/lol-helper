package helper.frame.panel.client;

import helper.cache.AppCache;
import helper.frame.panel.base.BaseCheckBox;
import helper.frame.utils.FrameConfigUtil;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * 选人阶段显示队友战绩
 *
 * @author WuYi
 */
public class ShowTeamBox extends BaseCheckBox {
	public ShowTeamBox() {
		this.setText("显示队友战绩");
		this.setSelected(AppCache.settingPersistence.getShowMatchHistory());
		this.addItemListener(listener());
	}

	private ItemListener listener() {
		return e -> {
			AppCache.settingPersistence.setShowMatchHistory(e.getStateChange() == ItemEvent.SELECTED);
			FrameConfigUtil.save();
		};
	}
}
