package yalong.site.frame.panel.client;

import yalong.site.bo.GlobalData;
import yalong.site.frame.bo.ComponentBO;
import yalong.site.frame.panel.base.BaseCheckBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author yaLong
 * @date 2022/2/11
 */
public class CommunicateCheckBox extends BaseCheckBox {
    public CommunicateCheckBox() {
        this.setText("互动模式");
        this.setSelected(GlobalData.communicate);
        this.addItemListener(listener());
        this.addActionListener(i -> {
            CommunicateCheckBox source = (CommunicateCheckBox) i.getSource();
            //选中状态下弹框提示
            if (source.getSelectedObjects() != null) {
                JOptionPane.showMessageDialog(null, "按Home键喷队友,按End键鼓励队友,按delete标记屏蔽词");
            }
        });

    }

    private ItemListener listener() {
        return e -> GlobalData.communicate = e.getStateChange() == ItemEvent.SELECTED;
    }

    /**
     * @return 带布局的盒子
     */
    public static ComponentBO builder() {
        CommunicateCheckBox box = new CommunicateCheckBox();
        GridBagConstraints grid = new GridBagConstraints(
                // 第(1,2)个格子
                1, 2,
                // 占1列,占1行
                1, 1,
                //横向占100%长度,纵向占100%长度
                100, 100,
                //居中,组件小的话就两边铺满窗格
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                // 窗格之间的距离
                new Insets(0, 0, 0, 0),
                // 增加组件的首选宽度和高度
                0, 0);
        return new ComponentBO(box, grid);
    }


}
