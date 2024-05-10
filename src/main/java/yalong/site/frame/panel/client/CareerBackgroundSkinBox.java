package yalong.site.frame.panel.client;

import lombok.extern.slf4j.Slf4j;
import yalong.site.bo.LeagueClientBO;
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

/**
 * @author chengshuangxiong
 */
@Slf4j
public class CareerBackgroundSkinBox extends BaseComboBox<ItemBO> {

    public CareerBackgroundSkinBox() {
        this.addItem(new ItemBO(null, "请先选择英雄"));
        this.addItemListener(listener());
    }

    /**
     * @return 带布局的盒子
     */
    public static ComponentBO builder() {
        CareerBackgroundSkinBox box = new CareerBackgroundSkinBox();
        FrameInnerCache.careerBackgroundSkinBox = box;
        GridBagConstraints grid = new GridBagConstraints(
                // 第(1,5)个格子
                0, 4,
                // 占1列,占1行
                2, 1,
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
                    FrameCache.careerSkinChampionId = null;
                } else {
                    FrameCache.careerSkinChampionId = Integer.parseInt(item.getValue());
                    try {
                        // 设置生涯背景
                        AppCache.api.setBackgroundSkin(FrameCache.careerSkinChampionId);
                    }catch (Exception exception){
                        log.error("选择生涯背景接口错误", exception);
                    }
                }

            }
        };
    }
}
