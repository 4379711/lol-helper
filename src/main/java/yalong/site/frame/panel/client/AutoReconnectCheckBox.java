package yalong.site.frame.panel.client;

import yalong.site.bo.GlobalData;
import yalong.site.frame.bo.ComponentBO;
import yalong.site.frame.panel.base.BaseCheckBox;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author yaLong
 * @date 2022/2/14
 */
public class AutoReconnectCheckBox extends BaseCheckBox {
    public AutoReconnectCheckBox() {
        this.setText("掉线自动重连");
        this.setSelected(GlobalData.autoReconnect);
        this.addItemListener(listener());
    }

    private ItemListener listener() {
        return e -> GlobalData.autoReconnect = e.getStateChange() == ItemEvent.SELECTED;
    }

    /**
     * @return 带布局的盒子
     */
    public static ComponentBO builder() {
        AutoReconnectCheckBox box = new AutoReconnectCheckBox();
        GridBagConstraints grid = new GridBagConstraints(
                // 第(1,1)个格子
                1, 1,
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
