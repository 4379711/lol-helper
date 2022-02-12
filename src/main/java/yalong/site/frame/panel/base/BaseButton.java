package yalong.site.frame.panel.base;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import javax.swing.*;

/**
 * @author yaLong
 * @date 2022/2/11
 */
public class BaseButton extends JButton {
    public BaseButton(String name) {
        super(name);
        //取消点击后的焦点
        this.setFocusPainted(false);
        BEButtonUI ui = new BEButtonUI();
        ui.setNormalColor(BEButtonUI.NormalColor.lightBlue);
        this.setUI(ui);
    }
}
