package helper.site.frame.panel.client;

import com.alibaba.fastjson2.JSONObject;
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

	private void loadItems() {
		try {
			if (AppCache.settingPersistence.getCareerChampionId() != null && AppCache.api != null) {
				// 根据所选英雄获取皮肤
				List<SkinBO> skin = AppCache.api.getSkinByChampionId(AppCache.settingPersistence.getCareerChampionId());
				for (SkinBO bo : skin) {
					ItemBO itemBO = new ItemBO(String.valueOf(bo.getId()), bo.getName());
					itemBO.setOther(bo.getContentId());
					box.addItem(itemBO);
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
						String skinId = item.getValue();
						// 设置生涯背景
						JSONObject body = new JSONObject(2);
						body.put("key", "backgroundSkinId");
						body.put("value", skinId);
						AppCache.api.setBackgroundSkin(body.toJSONString());
						//皮肤增强
						if (item.getOther() != null) {
							body.put("key", "backgroundSkinAugments");
							body.put("value", item.getOther());
							AppCache.api.setBackgroundSkin(body.toJSONString());
						}
					} catch (Exception exception) {
						log.error("设置生涯背景接口错误", exception);
					}
				}

			}
		};
	}
}
