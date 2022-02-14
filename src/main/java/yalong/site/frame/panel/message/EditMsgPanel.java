package yalong.site.frame.panel.message;

import yalong.site.bo.GlobalData;

import javax.swing.*;
import java.awt.*;

/**
 * 输入要发送的信息
 *
 * @author yaLong
 */
public class EditMsgPanel extends JScrollPane {
    public EditMsgPanel() {
        super(new MessageTextArea());
        this.setName("对喷内容");
        this.setMaximumSize(new Dimension(GlobalData.WIDTH, GlobalData.HEIGHT));
        // 设置透明
        this.setOpaque(false);
        this.setBorder(null);
        this.getViewport().setOpaque(false);

    }

    public static EditMsgPanel builder() {
        return new EditMsgPanel();
    }

}
