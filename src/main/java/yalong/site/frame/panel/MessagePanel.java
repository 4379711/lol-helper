package yalong.site.frame.panel;

import org.jb2011.lnf.beautyeye.ch4_scroll.BEScrollBarUI;
import org.jb2011.lnf.beautyeye.ch4_scroll.BEScrollPaneUI;
import yalong.site.frame.bo.GlobalDataBO;
import yalong.site.frame.ui.MyTabbedPaneUI;

import javax.swing.*;
import java.awt.*;

/**
 * 输入要发送的信息
 *
 * @author yaLong
 */
public class MessagePanel extends JScrollPane {
    public MessagePanel() {
        super(new TextArea());
        this.setName("消息");
        this.setMaximumSize(new Dimension(GlobalDataBO.WIDTH,GlobalDataBO.HEIGHT));
        // 设置透明
        this.setOpaque(false);
        this.setBorder(null);
        this.getViewport().setOpaque(false);

    }
}
