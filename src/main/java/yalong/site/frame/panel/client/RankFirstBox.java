package yalong.site.frame.panel.client;

import lombok.extern.slf4j.Slf4j;
import yalong.site.cache.AppCache;
import yalong.site.cache.FrameUserSetting;
import yalong.site.frame.bo.ItemBO;
import yalong.site.frame.panel.base.BaseComboBox;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author yaLong
 */
@Slf4j
public class RankFirstBox extends BaseComboBox<ItemBO> {

	public RankFirstBox() {
		this.setItems();
		this.addItemListener(listener());
	}

	public void setItems() {
		this.addItem(new ItemBO("RANKED_SOLO_5x5", "单排/双排"));
		this.addItem(new ItemBO("RANKED_FLEX_SR", "灵活组排 5v5"));
		this.addItem(new ItemBO("RANKED_FLEX_TT", "灵活组排 3v3"));
		this.addItem(new ItemBO("RANKED_TFT", "云顶之弈"));
	}

	private ItemListener listener() {
		return e -> {
			if (e.getStateChange() == ItemEvent.SELECTED && AppCache.api != null) {
				ItemBO item = (ItemBO) e.getItem();
				FrameUserSetting.currentRankBO.setFirstRank(item.getValue());
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
