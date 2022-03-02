package yalong.site.frame.panel.client;

import yalong.site.frame.bo.ComponentBO;
import yalong.site.frame.panel.base.BaseButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * @author yaLong
 * @date 2022/2/11
 */
public class HelpButton extends BaseButton {

    public HelpButton() {
        super("帮助");
        this.addActionListener(listener());
    }

    private ActionListener listener() {
        return e -> JOptionPane.showMessageDialog(getParent(), "俺帮不了你");
    }

    /**
     * @return 带布局的盒子
     */
    public static ComponentBO builder() {
        HelpButton box = new HelpButton();
        GridBagConstraints grid = new GridBagConstraints(
                // 第(2,2)个格子
                2, 2,
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
