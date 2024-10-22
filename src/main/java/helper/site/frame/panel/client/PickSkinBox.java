package helper.site.frame.panel.client;

import helper.site.bo.SkinBO;
import helper.site.cache.AppCache;
import helper.site.frame.bo.ItemBO;
import helper.site.frame.panel.base.BaseComboBox;
import lombok.extern.slf4j.Slf4j;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.List;

/**
 * @author @_@
 */
@Slf4j
public class PickSkinBox extends BaseComboBox<ItemBO> {
	private final PickSkinBox box;

	public PickSkinBox() {
		box = this;
		initItems();
		this.addItemListener(listener());
		this.addPopupMenuListener(popupMenuListener());
	}

	private void loadItems() {
		try {
			if (AppCache.api != null) {
				List<SkinBO> currentChampionSkins = AppCache.api.getCurrentChampionSkins();
				for (SkinBO bo : currentChampionSkins) {
					box.addItem(new ItemBO(String.valueOf(bo.getId()), bo.getName()));
				}
			}

		} catch (IOException ex) {
			log.error("获取炫彩皮肤接口错误", ex);
		}
	}

	private void initItems() {
		box.removeAllItems();
		box.addItem(new ItemBO(null, "选择炫彩皮肤"));
	}

	private PopupMenuListener popupMenuListener() {
		return new PopupMenuListener() {
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				//重新加载皮肤数据
				initItems();
				loadItems();
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				initItems();
			}
		};
	}

	private ItemListener listener() {
		return e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				ItemBO item = (ItemBO) e.getItem();
				if (item.getValue() != null) {
					int skinId = Integer.parseInt(item.getValue());
					try {
						AppCache.api.setCurrentChampionSkins(skinId);
					} catch (IOException ex) {
						log.error("设置炫彩皮肤接口错误", ex);
					}
				}
			}
		};
	}

}
