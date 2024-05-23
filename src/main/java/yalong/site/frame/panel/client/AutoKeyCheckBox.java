package yalong.site.frame.panel.client;

import yalong.site.cache.FrameCache;
import yalong.site.frame.bo.ComponentBO;
import yalong.site.frame.panel.base.BaseCheckBox;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author yaLong
 * @date 2022/2/11
 */
public class AutoKeyCheckBox extends BaseCheckBox {

	public AutoKeyCheckBox() {
		this.setText("一键连招");
		this.setSelected(FrameCache.autoKey);
		this.addItemListener(listener());
	}

	/**
	 * @return 带布局的盒子
	 */
	public static ComponentBO builder() {
		AutoKeyCheckBox box = new AutoKeyCheckBox();
		GridBagConstraints grid = new GridBagConstraints(
				// 第(2,2)个格子
				2, 2,
				// 占1列,占1行
				1, 1,
				//横向占100%长度,纵向占100%长度
				2, 2,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.CENTER, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 0, 0, 0),
				// 增加组件的首选宽度和高度
				0, 0);
		return new ComponentBO(box, grid);
	}

	private ItemListener listener() {
		return e -> FrameCache.autoKey = e.getStateChange() == ItemEvent.SELECTED;
	}

}
