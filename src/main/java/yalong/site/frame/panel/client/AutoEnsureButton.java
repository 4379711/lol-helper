package yalong.site.frame.panel.client;

import yalong.site.frame.panel.base.BaseButton;

/**
 * @author yaLong
 * @date 2022/2/11
 */
public class AutoEnsureButton extends BaseButton {
    public AutoEnsureButton() {
        super("确定");
        this.addActionListener(event -> {
            System.out.println("dianji");
        });
    }

}
