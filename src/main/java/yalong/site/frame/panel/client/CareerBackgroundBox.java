package yalong.site.frame.panel.client;

import lombok.extern.slf4j.Slf4j;
import yalong.site.bo.LeagueClientBO;
import yalong.site.bo.SkinBO;
import yalong.site.cache.AppCache;
import yalong.site.cache.FrameCache;
import yalong.site.cache.FrameInnerCache;
import yalong.site.frame.bo.ComponentBO;
import yalong.site.frame.bo.ItemBO;
import yalong.site.frame.panel.base.BaseComboBox;
import yalong.site.http.RequestLcuUtil;
import yalong.site.services.lcu.LinkLeagueClientApi;
import yalong.site.utils.ProcessUtil;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.List;

/**
 * @author chengshuangxiong
 */
@Slf4j
public class CareerBackgroundBox extends BaseComboBox<ItemBO> {

    public CareerBackgroundBox() {
        this.addItem(new ItemBO(null, "选择生涯背景"));
        this.addItemListener(listener());
    }

    /**
     * @return 带布局的盒子
     */
    public static ComponentBO builder() {
        CareerBackgroundBox box = new CareerBackgroundBox();
        FrameInnerCache.careerBackgroundBox = box;
        GridBagConstraints grid = new GridBagConstraints(
                // 第(0,5)个格子
                0, 5,
                // 占1列,占1行
                1, 1,
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

    private ItemListener listener() {
        return e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                ItemBO item = (ItemBO) e.getItem();
                if (item.getValue() == null) {
                    FrameCache.careerChampionId = null;
                } else {
                    FrameCache.careerChampionId = Integer.parseInt(item.getValue());
                    try {
                        // 先清空上一个英雄的皮肤
                        for (int i = FrameInnerCache.careerBackgroundSkinBox.getItemCount() - 1; i >= 1; i--) {
                            FrameInnerCache.careerBackgroundSkinBox.removeItemAt(i);
                        }
                        // 根据所选英雄获取皮肤
                        List<SkinBO> skin = AppCache.api.getSkinByChampionId(FrameCache.careerChampionId);
                        for (SkinBO bo : skin) {
                            FrameInnerCache.careerBackgroundSkinBox.addItem(new ItemBO(String.valueOf(bo.getId()), bo.getName()));
                        }
                    } catch (IOException ex) {
                        log.error("选择生涯背景接口错误", ex);
                    }
                }

            }
        };
    }

}
