package yalong.site.frame.panel.base;


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
    }
}
