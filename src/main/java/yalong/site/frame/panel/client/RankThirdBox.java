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
public class RankThirdBox extends BaseComboBox<ItemBO> {


    public RankThirdBox() {
        this.setItems();
        this.addItemListener(listener());
    }

    public void setItems() {
        this.addItem(new ItemBO("IV", "IV"));
        this.addItem(new ItemBO("III", "III"));
        this.addItem(new ItemBO("II", "II"));
        this.addItem(new ItemBO("I", "I"));
    }

    private ItemListener listener() {
        return e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                ItemBO item = (ItemBO) e.getItem();
                GlobalData.currentRankBO.setThirdRank(item.getValue());
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
        RankThirdBox box = new RankThirdBox();
        GridBagConstraints grid = new GridBagConstraints(
                // 第(2,0)个格子
                2, 0,
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
