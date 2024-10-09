package helper.frame.panel.client;

import helper.cache.AppCache;
import helper.cache.FrameUserSetting;
import helper.frame.bo.ItemBO;
import helper.frame.panel.base.BaseComboBox;
import lombok.extern.slf4j.Slf4j;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author @_@
 */
@Slf4j
public class RankThirdBox extends BaseComboBox<ItemBO> {

	public RankThirdBox() {
		this.setItems();
		this.addItemListener(listener());
	}

	public void setItems() {
		this.addItem(new ItemBO("I", "I"));
		this.addItem(new ItemBO("II", "II"));
		this.addItem(new ItemBO("III", "III"));
		this.addItem(new ItemBO("IV", "IV"));
	}

	private ItemListener listener() {
		return e -> {
			if (e.getStateChange() == ItemEvent.SELECTED && AppCache.api != null) {
				ItemBO item = (ItemBO) e.getItem();
				FrameUserSetting.currentRankBO.setThirdRank(item.getValue());
				if (!FrameUserSetting.currentRankBO.isNull()) {
					try {
						AppCache.api.setRank(FrameUserSetting.currentRankBO);
					} catch (Exception ex) {
						log.error("设置rank错误", ex);
					}
				}
			}
		};
	}

}
