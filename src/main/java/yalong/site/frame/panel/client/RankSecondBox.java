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
public class RankSecondBox extends BaseComboBox<ItemBO> {

	public RankSecondBox() {
		this.setItems();
		this.addItemListener(listener());
	}

	public void setItems() {
		this.addItem(new ItemBO("UNRANKED", "没有段位"));
		this.addItem(new ItemBO("CHALLENGER", "最强王者"));
		this.addItem(new ItemBO("GRANDMASTER", "傲世宗师"));
		this.addItem(new ItemBO("MASTER", "超凡大师"));
		this.addItem(new ItemBO("DIAMOND", "璀璨钻石"));
		this.addItem(new ItemBO("EMERALD", "流光翡翠"));
		this.addItem(new ItemBO("PLATINUM", "华贵铂金"));
		this.addItem(new ItemBO("GOLD", "荣耀黄金"));
		this.addItem(new ItemBO("SILVER", "不屈白银"));
		this.addItem(new ItemBO("BRONZE", "英勇黄铜"));
		this.addItem(new ItemBO("IRON", "坚韧黑铁"));
	}

	private ItemListener listener() {
		return e -> {
			if (e.getStateChange() == ItemEvent.SELECTED && AppCache.api != null) {
				ItemBO item = (ItemBO) e.getItem();
				FrameUserSetting.currentRankBO.setSecondRank(item.getValue());
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
