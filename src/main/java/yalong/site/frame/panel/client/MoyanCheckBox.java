package yalong.site.frame.panel.client;

import yalong.site.bo.GlobalData;
import yalong.site.frame.bo.ComponentBO;
import yalong.site.frame.panel.base.BaseCheckBox;
import yalong.site.services.KeyBoardListener;
import yalong.site.services.KeyService;
import yalong.site.utils.KeyBoardUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author yaLong
 * @date 2022/2/11
 */
public class MoyanCheckBox extends BaseCheckBox {
    private final KeyBoardListener keyBoardListener;

    public MoyanCheckBox() {
        this.setText("光速摸眼");
        this.setSelected(GlobalData.moyan);
        this.addItemListener(listener());
        this.addActionListener(i -> {
            MoyanCheckBox source = (MoyanCheckBox) i.getSource();
            //选中状态下弹框提示
            if (source.getSelectedObjects() != null) {
                JOptionPane.showMessageDialog(null, "盲仔按T光速摸眼");
            }
        });
        //注册热键T,光速摸眼
        keyBoardListener = new KeyBoardListener(KeyService.moyan());
    }

    private ItemListener listener() {

        return e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                GlobalData.moyan = true;
                KeyBoardUtil.addListener(keyBoardListener);
            } else {
                GlobalData.moyan = false;
                KeyBoardUtil.removeListener(keyBoardListener);
            }
        };
    }

    /**
     * @return 带布局的盒子
     */
    public static ComponentBO builder() {
        MoyanCheckBox box = new MoyanCheckBox();
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
