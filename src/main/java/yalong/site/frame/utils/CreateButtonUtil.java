package yalong.site.frame.utils;

import yalong.site.frame.panel.base.BaseButton;

import javax.swing.*;
import java.awt.*;

/**
 * @author yaLong
 */
public class CreateButtonUtil {

    private void addButton(JComponent component, String name) {
        //布局
        GridBagLayout layout = new GridBagLayout();
        component.setLayout(layout);

        //放俩空盒子,把按钮挤到下边
        JPanel empty0 = new JPanel();
        empty0.setOpaque(false);
        GridBagConstraints grid0 = new GridBagConstraints(
                // 第(0,0)个格子
                0, 0,
                // 占1列,占2行
                1, 2,
                //横向占100%长度,纵向占100%长度
                100, 100,
                //居中,组件小的话就两边铺满窗格
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                // 窗格之间的距离
                new Insets(0, 0, 0, 0),
                // 增加组件的首选宽度和高度
                10, 10
        );

        JPanel empty1 = new JPanel();
        empty1.setOpaque(false);
        GridBagConstraints grid1 = new GridBagConstraints();
        grid1.gridx = 1;
        grid1.gridy = 0;
        //纵向占100%长度
        grid1.weighty = 100;
        // 只调整组件的高度
        grid1.fill = GridBagConstraints.BOTH;

        BaseButton clearResultButton = new BaseButton(name);
        GridBagConstraints grid2 = new GridBagConstraints();
        grid2.gridy = 1;
        grid2.gridx = 1;
        // 设置右下角的距离
        grid2.insets = new Insets(0, 0, 1, 5);

        component.add(empty0, grid0);
        component.add(empty1, grid1);
        component.add(clearResultButton, grid2);
    }
}
