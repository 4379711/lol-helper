package yalong.site.frame.panel.client;

import lombok.extern.slf4j.Slf4j;
import yalong.site.cache.FrameCache;
import yalong.site.cache.FrameInnerCache;
import yalong.site.frame.bo.ComponentBO;
import yalong.site.frame.bo.ItemBO;
import yalong.site.frame.panel.base.BaseComboBox;
import yalong.site.frame.panel.base.BasePanel;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author yaLong
 */
@Slf4j
public class RightEmptyBox extends BasePanel {

	/**
	 * @return 带布局的盒子
	 */
	public static ComponentBO builder() {
		RightEmptyBox box = new RightEmptyBox();
		GridBagConstraints grid = new GridBagConstraints(
				// 第(1,3)个格子
				3, 0,
				// 占1列,占1行
				1, 5,
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

}
