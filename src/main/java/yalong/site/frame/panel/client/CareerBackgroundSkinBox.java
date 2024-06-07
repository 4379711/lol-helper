package yalong.site.frame.panel.client;

import lombok.extern.slf4j.Slf4j;
import yalong.site.bo.SkinBO;
import yalong.site.cache.AppCache;
import yalong.site.cache.FrameUserSetting;
import yalong.site.frame.bo.ItemBO;
import yalong.site.frame.panel.base.BaseComboBox;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.List;

/**
 * @author chengshuangxiong
 */
@Slf4j
public class CareerBackgroundSkinBox extends BaseComboBox<ItemBO> {
	private final CareerBackgroundSkinBox box;

	public CareerBackgroundSkinBox() {
		box = this;
		initItems();
		this.addItemListener(listener());
		this.addPopupMenuListener(popupMenuListener());
	}

	private void loadItems(){
		try {
			if (FrameUserSetting.careerChampionId != null && AppCache.api != null) {
				// 根据所选英雄获取皮肤
				List<SkinBO> skin = AppCache.api.getSkinByChampionId(FrameUserSetting.careerChampionId);
				for (SkinBO bo : skin) {
					box.addItem(new ItemBO(String.valueOf(bo.getId()), bo.getName()));
				}
			}
		} catch (IOException ex) {
			log.error("获取生涯背景皮肤接口错误", ex);
		}
	}
	private void initItems() {
		box.removeAllItems();
		box.addItem(new ItemBO(null, "选择生涯背景皮肤"));
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
					try {
						int skinId = Integer.parseInt(item.getValue());
						// 设置生涯背景
						AppCache.api.setBackgroundSkin(skinId);
					} catch (Exception exception) {
						log.error("设置生涯背景接口错误", exception);
					}
				}

			}
		};
	}
}
