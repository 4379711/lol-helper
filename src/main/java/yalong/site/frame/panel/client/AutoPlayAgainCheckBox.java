package yalong.site.frame.panel.client;

import yalong.site.cache.FrameCache;
import yalong.site.frame.bo.ComponentBO;
import yalong.site.frame.panel.base.BaseCheckBox;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author yaLong
 * @date 2024/4/23
 */
public class AutoPlayAgainCheckBox extends BaseCheckBox {
	public AutoPlayAgainCheckBox() {
		this.setText("自动再来一局");
		this.setSelected(FrameCache.autoPlayAgain);
		this.addItemListener(listener());
	}

	/**
	 * @return 带布局的盒子
	 */
	public static ComponentBO builder() {
		AutoPlayAgainCheckBox box = new AutoPlayAgainCheckBox();
		GridBagConstraints grid = new GridBagConstraints(
				// 第(2,1)个格子
				2, 1,
				// 占1列,占1行
				1, 1,
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
		return e -> FrameCache.autoPlayAgain = e.getStateChange() == ItemEvent.SELECTED;
	}

}
