package helper.frame.panel.client;

import helper.cache.AppCache;
import helper.frame.bo.ItemBO;
import helper.frame.panel.base.BaseComboBox;
import lombok.extern.slf4j.Slf4j;

import java.awt.event.ItemListener;

/**
 * @author @_@
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

	public void setItems() {
		this.addItem(new ItemBO("chat", "在线"));
		this.addItem(new ItemBO("away", "离开"));
		this.addItem(new ItemBO("dnd", "游戏中"));
		this.addItem(new ItemBO("offline", "离线"));
		this.addItem(new ItemBO("mobile", "手机在线"));
	}

}
