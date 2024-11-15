package helper.frame.panel.history;

import helper.bo.GameQueue;
import helper.cache.AppCache;
import helper.cache.FrameInnerCache;
import helper.cache.GameDataCache;
import helper.frame.bo.ItemBO;
import helper.frame.panel.base.BaseComboBox;
import helper.frame.utils.FrameConfigUtil;
import lombok.extern.slf4j.Slf4j;

import java.awt.event.ItemListener;

/**
 * @author WuYi
 */
@Slf4j
public class GameModeBox extends BaseComboBox<ItemBO> {

	public GameModeBox() {
		FrameInnerCache.gameModeBox = this;

	}

	private ItemListener itemListener() {
		return e -> {
			ItemBO item = (ItemBO) e.getItem();
			AppCache.settingPersistence.setSelectMode(Integer.valueOf(item.getValue()));
			FrameConfigUtil.save();
		};
	}

	public void setItems() {
		ItemBO selectItem = new ItemBO();
		for (Integer key : GameDataCache.selectGameQueueList.keySet()) {
			GameQueue gameQueue = GameDataCache.selectGameQueueList.get(key);
			if (gameQueue.getId().equals(AppCache.settingPersistence.getSelectMode())) {
				selectItem.setValue(gameQueue.getId().toString());
				selectItem.setDisplayValue(gameQueue.getName());
			}
			this.addItem(new ItemBO(gameQueue.getId().toString(), gameQueue.getName()));
		}
		this.setSelectedItem(selectItem);
		this.addItemListener(itemListener());
	}
}
