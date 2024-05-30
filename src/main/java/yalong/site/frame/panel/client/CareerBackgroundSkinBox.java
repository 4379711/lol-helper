package yalong.site.frame.panel.client;

import lombok.extern.slf4j.Slf4j;
import yalong.site.bo.SkinBO;
import yalong.site.cache.AppCache;
import yalong.site.cache.FrameUserSetting;
import yalong.site.frame.bo.ComponentBO;
import yalong.site.frame.bo.ItemBO;
import yalong.site.frame.panel.base.BaseComboBox;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
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

	private void initItems() {
		box.removeAllItems();
		box.addItem(new ItemBO(null, "选择生涯背景皮肤"));
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

	/**
	 * @return 带布局的盒子
	 */
	public static ComponentBO builder() {
		CareerBackgroundSkinBox box = new CareerBackgroundSkinBox();
		GridBagConstraints grid = new GridBagConstraints(
				// 第(1,5)个格子
				2, 3,
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

	private PopupMenuListener popupMenuListener() {
		return new PopupMenuListener() {
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				//重新加载皮肤数据
				initItems();
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {

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
