package yalong.site.frame.button;

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
