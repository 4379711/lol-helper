package yalong.site.frame.panel.match;

import yalong.site.frame.ui.MyTabbedPaneUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HistoryLine extends JPanel {
    // 创建用于显示图片的JLabel和用于显示文字的JLabel
    private JLabel championIcon = new JLabel();
    private JLabel spellIcon1 = new JLabel();
    private JLabel spellIcon2 = new JLabel();
    private JLabel item0 = new JLabel();
    private JLabel item1 = new JLabel();
    private JLabel item2 = new JLabel();
    private JLabel item3 = new JLabel();
    private JLabel item4 = new JLabel();
    private JLabel item5 = new JLabel();
    private JLabel item6 = new JLabel();
    private JLabel textLabel = new JLabel();

    public HistoryLine() {
        //设置不可编辑
        this.setOpaque(false);
        //设置背景颜色
        this.setBackground(MyTabbedPaneUI.END_COLOR_SELECT);
        //设置边框为null
        this.setBorder(null);
        // 使用GridBagLayout布局管理器
        GridBagConstraints grid1 = new GridBagConstraints(
                // 第(0,0)个格子
                0, 0,
                // 占2列,占2行
                1, 2,
                //横向占100%长度,纵向占100%长度
                2, 2,
                //居中,组件小的话就两边铺满窗格
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                // 窗格之间的距离
                new Insets(0, 0, 0, 0),
                // 增加组件的首选宽度和高度
                0, 0
        );
        GridBagConstraints grid2 = new GridBagConstraints(
                // 第(0,0)个格子
                1, 0,
                // 占3列,占1行
                1, 1,
                //横向占100%长度,纵向占100%长度
                2, 2,
                //居中,组件小的话就两边铺满窗格
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                // 窗格之间的距离
                new Insets(0, 0, 0, 0),
                // 增加组件的首选宽度和高度
                0, 0
        );
        GridBagConstraints grid3 = new GridBagConstraints(
                // 第(0,0)个格子
                1, 1,
                // 占3列,占1行
                1, 1,
                //横向占100%长度,纵向占100%长度
                2, 2,
                //居中,组件小的话就两边铺满窗格
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                // 窗格之间的距离
                new Insets(0, 0, 0, 0),
                // 增加组件的首选宽度和高度
                0, 0
        );
        GridBagConstraints grid4 = new GridBagConstraints(
                // 第(0,0)个格子
                3, 0,
                // 占3列,占1行
                7, 1,
                //横向占100%长度,纵向占100%长度
                2, 2,
                //居中,组件小的话就两边铺满窗格
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                // 窗格之间的距离
                new Insets(0, 0, 0, 0),
                // 增加组件的首选宽度和高度
                0, 0
        );
        GridBagConstraints grid5 = new GridBagConstraints(
                // 第(0,0)个格子
                3, 1,
                // 占3列,占1行
                7, 1,
                //横向占100%长度,纵向占100%长度
                2, 2,
                //居中,组件小的话就两边铺满窗格
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                // 窗格之间的距离
                new Insets(0, 0, 0, 0),
                // 增加组件的首选宽度和高度
                0, 0
        );

        // 图片部分，占据两行
        add(championIcon, grid1);

        // spellIcon1 的 gridy 设为 0
        grid2.gridy = 0;
        add(spellIcon1, grid2);

        // spellIcon2 的 gridy 设为 1，此处不需要再执行 grid2.gridy += 1;
        grid2.gridy = 1;
        add(spellIcon2, grid2);

/*        add(item0, grid4);
        add(item1, grid4);
        add(item2, grid4);
        add(item3, grid4);
        add(item4, grid4);
        add(item5, grid4);
        add(item6, grid4);

        add(new JLabel("测试"), grid5);
        add(new JLabel("测试"), grid5);
        add(new JLabel("测试"), grid5);
        add(new JLabel("测试"), grid5);
        add(new JLabel("测试"), grid5);
        add(new JLabel("测试"), grid5);
        add(new JLabel("测试"), grid5);*/


    }

    public void setImageIcons(ImageIcon championIcon, ArrayList<ImageIcon> spell) {
        this.championIcon.setIcon(championIcon);
        this.spellIcon1.setIcon(spell.get(0));
        this.spellIcon2.setIcon(spell.get(1));
    }

    public void setText(String text) {
        textLabel.setText(text);
    }

    @Override
    public Dimension getMaximumSize() {
        Dimension pref = getPreferredSize();
        return new Dimension(Integer.MAX_VALUE, pref.height);
    }

    public void setItemIcons(ArrayList<ImageIcon> itemsIcon) {
        this.item0.setIcon(itemsIcon.get(0));
        this.item1.setIcon(itemsIcon.get(1));
        this.item2.setIcon(itemsIcon.get(2));
        this.item3.setIcon(itemsIcon.get(3));
        this.item4.setIcon(itemsIcon.get(4));
        this.item5.setIcon(itemsIcon.get(5));
        this.item6.setIcon(itemsIcon.get(6));
    }
}
