package yalong.site.frame.panel.base;

import org.jb2011.lnf.beautyeye.ch9_menu.BECheckBoxMenuItemUI;

import javax.swing.*;

/**
 * @author yaLong
 * @date 2022/2/14
 */
public class BaseCheckBox extends JCheckBox {
    public BaseCheckBox() {
        BECheckBoxMenuItemUI.CheckBoxMenuItemIcon icon = new BECheckBoxMenuItemUI.CheckBoxMenuItemIcon();
        this.setSelected(true);
        this.setFocusPainted(false);
        this.setIcon(icon);
        this.setOpaque(false);
        this.setBorder(null);
    }

}
