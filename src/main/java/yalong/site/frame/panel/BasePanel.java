package yalong.site.frame.panel;

import javax.swing.*;
import java.awt.*;

/**
 * @author yaLong
 * @date 2022/2/11
 */
public class BasePanel extends JPanel {

    public BasePanel() {
        //设置背景
        this.setBackground(null);
        // 设置透明
        this.setOpaque(false);
        this.setBorder(null);
    }
}
