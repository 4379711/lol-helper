package yalong.site.frame.combox;

import org.jb2011.lnf.beautyeye.ch14_combox.BEComboBoxUI;

import javax.swing.*;

/**
 * @author yaLong
 */
public class BaseComboBox<E> extends JComboBox<E> {

    public BaseComboBox() {
        BEComboBoxUI ui = new BEComboBoxUI();
        this.setUI(ui);
    }

}
