package yalong.site.frame.panel;

import org.jb2011.lnf.beautyeye.ch4_scroll.BEScrollPaneUI;
import org.jb2011.lnf.beautyeye.ch6_textcoms.BETextAreaUI;
import yalong.site.frame.bo.GlobalDataBO;
import yalong.site.frame.ui.MyTabbedPaneUI;

import javax.swing.*;
import java.awt.*;

/**
 * 输入程序运行结果
 *
 * @author yaLong
 */
public class ResultPanel extends JScrollPane {
    public static TextArea resultArea;
    static {
        resultArea=new TextArea();
        resultArea.setEditable(false);
    }
    public ResultPanel() {
        super(resultArea);
        this.setName("执行结果");
        // 设置透明
        this.setOpaque(false);
        this.setBorder(null);
        this.getViewport().setOpaque(false);
        this.setAutoscrolls(true);
        this.setMaximumSize(new Dimension(GlobalDataBO.WIDTH,GlobalDataBO.HEIGHT));
    }
}
