package yalong.site.frame.panel.client;

import yalong.site.bo.GlobalData;
import yalong.site.frame.bo.ComponentBO;
import yalong.site.frame.panel.base.BaseButton;
import yalong.site.frame.utils.FrameMsgUtil;

import java.awt.*;
import java.awt.event.ActionListener;

/**
 * @author yaLong
 * @date 2022/2/11
 */
public class RivalButton extends BaseButton {

    public RivalButton() {
        super("对喷模式");
        this.addActionListener(listener());
    }

    private ActionListener listener() {
        return e -> {
            if (GlobalData.data != null && GlobalData.data.size() > 0) {
                try {
                    GlobalData.service.sendMsg2game(GlobalData.data);
                } catch (Exception ex) {
                    FrameMsgUtil.sendLine(ex.getMessage());
                }
            }
        };

    }

    /**
     * @return 带布局的盒子
     */
    public static ComponentBO builder() {
        RivalButton box = new RivalButton();
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
                0, 0
        );
        return new ComponentBO(box, grid);
    }


}
