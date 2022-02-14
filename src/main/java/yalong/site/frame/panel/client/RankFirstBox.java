package yalong.site.frame.panel.client;

import yalong.site.bo.GlobalData;
import yalong.site.frame.bo.ComponentBO;
import yalong.site.frame.bo.ItemBO;
import yalong.site.frame.panel.base.BaseComboBox;
import yalong.site.frame.utils.FrameMsgUtil;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

/**
 * @author yaLong
 */
public class RankFirstBox extends BaseComboBox<ItemBO> {

    public RankFirstBox() {
        this.setItems();
        this.addItemListener(listener());
    }

    public void setItems() {
        this.addItem(new ItemBO("RANKED_SOLO_5x5", "单排/双排"));
        this.addItem(new ItemBO("RANKED_FLEX_SR", "灵活组排 5v5"));
        this.addItem(new ItemBO("RANKED_FLEX_TT", "灵活组排 3v3"));
        this.addItem(new ItemBO("RANKED_TFT", "云顶之弈"));
    }

    private ItemListener listener() {
        return e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                ItemBO item = (ItemBO) e.getItem();
                GlobalData.currentRankBO.setFirstRank(item.getValue());
                if (!GlobalData.currentRankBO.isNull()) {
                    try {
                        GlobalData.service.setRank(GlobalData.currentRankBO);
                    } catch (IOException ex) {
                        FrameMsgUtil.sendLine(ex.getMessage());
                    }
                }

            }
        };
    }

    /**
     * @return 带布局的盒子
     */
    public static ComponentBO builder() {
        RankFirstBox box = new RankFirstBox();
        GridBagConstraints grid = new GridBagConstraints(
                // 第(0,0)个格子
                0, 0,
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

}
