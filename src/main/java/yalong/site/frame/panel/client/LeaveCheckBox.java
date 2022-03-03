package yalong.site.frame.panel.client;

import org.jnativehook.keyboard.NativeKeyEvent;
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
public class LeaveCheckBox extends BaseCheckBox {
    private Thread thread;
    private final KeyBoardListener keyBoardListener;

    public LeaveCheckBox() {
        this.setText("挂机模式");
        this.setSelected(GlobalData.leave);
        this.addItemListener(listener());
        this.addActionListener(i -> {
            LeaveCheckBox source = (LeaveCheckBox) i.getSource();
            //选中状态下弹框提示
            if (source.getSelectedObjects() != null) {
                JOptionPane.showMessageDialog(null, "按F11开启和暂停");
            }
        });

        //注册F11热键,用来开启和暂停
        keyBoardListener = new KeyBoardListener(i -> {
            if (i == NativeKeyEvent.VC_F11) {
                GlobalData.leave = !GlobalData.leave;
            }
        });

    }

    private ItemListener listener() {
        return e -> {
            //直接关闭,用热键控制开关
            GlobalData.leave = false;
            if (e.getStateChange() == ItemEvent.SELECTED) {
                KeyBoardUtil.addListener(keyBoardListener);
                thread = new Thread(KeyService::leave);
                thread.start();
            } else {
                KeyBoardUtil.removeListener(keyBoardListener);
                thread.interrupt();
            }
        };
    }

    /**
     * @return 带布局的盒子
     */
    public static ComponentBO builder() {
        LeaveCheckBox box = new LeaveCheckBox();
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
