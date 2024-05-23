package yalong.site.frame.panel.client;

import lombok.extern.slf4j.Slf4j;
import yalong.site.cache.FrameCache;
import yalong.site.cache.FrameInnerCache;
import yalong.site.frame.bo.ComponentBO;
import yalong.site.frame.bo.ItemBO;
import yalong.site.frame.panel.base.BaseComboBox;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author yaLong
 */
@Slf4j
public class BanBox extends BaseComboBox<ItemBO> {

	public BanBox() {
		this.addItem(new ItemBO(null, "禁用英雄"));
		this.addItemListener(listener());
	}

	/**
	 * @return 带布局的盒子
	 */
	public static ComponentBO builder() {
		BanBox box = new BanBox();
		FrameInnerCache.banBox = box;
		GridBagConstraints grid = new GridBagConstraints(
				// 第(2,3)个格子
				2, 3,
				// 占1列,占1行
				1, 1,
				//横向占100%长度,纵向占100%长度
				2, 2,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.CENTER, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 0, 0, 0),
				// 增加组件的首选宽度和高度
				0, 0
		);
		return new ComponentBO(box, grid);
	}

	private ItemListener listener() {
		return e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				ItemBO item = (ItemBO) e.getItem();
				if (item.getValue() == null) {
					FrameCache.banChampionId = null;
				} else {
					FrameCache.banChampionId = Integer.parseInt(item.getValue());
				}

			}
		};
	}

}
