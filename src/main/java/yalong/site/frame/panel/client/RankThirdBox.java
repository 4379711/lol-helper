package yalong.site.frame.panel.client;

import lombok.extern.slf4j.Slf4j;
import yalong.site.cache.FrameCache;
import yalong.site.frame.bo.ComponentBO;
import yalong.site.frame.bo.ItemBO;
import yalong.site.frame.panel.base.BaseComboBox;
import yalong.site.frame.panel.client.listener.RankBoxItemListener;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author yaLong
 */
@Slf4j
public class RankThirdBox extends BaseComboBox<ItemBO> {

	public RankThirdBox() {
		this.setItems();
		this.addItemListener(new RankBoxItemListener());
		this.addItemListener(listener());
	}

	/**
	 * @return 带布局的盒子
	 */
	public static ComponentBO builder() {
		RankThirdBox box = new RankThirdBox();
		GridBagConstraints grid = new GridBagConstraints(
				// 第(2,0)个格子
				2, 0,
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

	public void setItems() {
		this.addItem(new ItemBO("I", "I"));
		this.addItem(new ItemBO("II", "II"));
		this.addItem(new ItemBO("III", "III"));
		this.addItem(new ItemBO("IV", "IV"));
	}

	private ItemListener listener() {
		return e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				ItemBO item = (ItemBO) e.getItem();
				FrameCache.currentRankBO.setThirdRank(item.getValue());
			}
		};
	}

}
