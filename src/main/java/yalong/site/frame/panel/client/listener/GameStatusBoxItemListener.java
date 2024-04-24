package yalong.site.frame.panel.client.listener;

import lombok.extern.slf4j.Slf4j;
import yalong.site.cache.AppCache;
import yalong.site.frame.bo.ItemBO;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author yalong
 */
@Slf4j
public class GameStatusBoxItemListener implements ItemListener {
	@Override
	public void itemStateChanged(ItemEvent e) {
		int stateChange = e.getStateChange();
		//选中返回1
		if (stateChange == 1) {
			ItemBO item = (ItemBO) e.getItem();
			try {
				AppCache.api.changeStatus(item.getValue());
			} catch (Exception ex) {
				log.error("改变游戏状态错误", ex);
			}
		}
	}
}
