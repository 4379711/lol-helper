package yalong.site.frame.panel.client;

import lombok.extern.slf4j.Slf4j;
import yalong.site.frame.bo.ComponentBO;
import yalong.site.frame.panel.base.BasePanel;

import java.awt.*;

/**
 * @author yaLong
 */
@Slf4j
public class BottomEmptyBox extends BasePanel {

	/**
	 * @return 带布局的盒子
	 */
	public static ComponentBO builder() {
		BottomEmptyBox box = new BottomEmptyBox();
		GridBagConstraints grid = new GridBagConstraints(
				// 第(1,3)个格子
				0, 5,
				// 占1列,占1行
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

}
