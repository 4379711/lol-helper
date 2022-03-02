package yalong.site.frame.panel.base;

import javax.swing.*;

/**
 * @author yaLong
 * @date 2022/2/14
 */
public class BaseCheckBox extends JCheckBox {
    public BaseCheckBox() {
        this.setSelected(true);
        this.setFocusPainted(false);
        this.setOpaque(false);
        this.setBorder(null);
    }

}
