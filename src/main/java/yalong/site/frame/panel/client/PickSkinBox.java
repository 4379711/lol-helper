package yalong.site.frame.panel.client;

import lombok.extern.slf4j.Slf4j;
import yalong.site.cache.FrameInnerCache;
import yalong.site.cache.GameDataCache;
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
public class PickSkinBox extends BaseComboBox<ItemBO> {

	public PickSkinBox() {
		this.addItem(new ItemBO(null, "选择炫彩皮肤"));
		this.addItemListener(listener());
	}

	/**
	 * @return 带布局的盒子
	 */
	public static ComponentBO builder() {
		PickSkinBox box = new PickSkinBox();
		FrameInnerCache.pickSkinBox = box;
		GridBagConstraints grid = new GridBagConstraints(
				// 第(0,4)个格子
				0, 4,
				// 占3列,占1行
				3, 1,
				//横向占100%长度,纵向占100%长度
				100, 100,
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
					GameDataCache.skinId = null;
				} else {
					GameDataCache.skinId  = Integer.parseInt(item.getValue());
				}

			}
		};
	}

}
