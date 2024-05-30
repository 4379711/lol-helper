package yalong.site.frame.panel.client;

import lombok.extern.slf4j.Slf4j;
import yalong.site.cache.AppCache;
import yalong.site.frame.bo.ComponentBO;
import yalong.site.frame.bo.ItemBO;
import yalong.site.frame.panel.base.BaseComboBox;

import java.awt.*;
import java.awt.event.ItemListener;

/**
 * @author yaLong
 */
@Slf4j
public class GameStatusBox extends BaseComboBox<ItemBO> {

	public GameStatusBox() {
		this.setItems();
		this.addItemListener(itemListener());
	}

	private ItemListener itemListener() {
		return e -> {
			int stateChange = e.getStateChange();
			//选中返回1
			if (stateChange == 1 && AppCache.api != null) {
				ItemBO item = (ItemBO) e.getItem();
				try {
					AppCache.api.changeStatus(item.getValue());
				} catch (Exception ex) {
					log.error("改变游戏状态错误", ex);
				}
			}
		};
	}

	/**
	 * @return 带布局的盒子
	 */
	public static ComponentBO builder() {
		GameStatusBox box = new GameStatusBox();
		GridBagConstraints grid = new GridBagConstraints(
				// 第(0,3)个格子
				0, 3,
				// 占1列,占1行
				1, 1,
				//横向占100%长度,纵向占100%长度
				1, 1,
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
		this.addItem(new ItemBO("chat", "在线"));
		this.addItem(new ItemBO("away", "离开"));
		this.addItem(new ItemBO("dnd", "游戏中"));
		this.addItem(new ItemBO("offline", "离线"));
		this.addItem(new ItemBO("mobile", "手机在线"));
	}

}
