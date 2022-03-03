package yalong.site.frame.panel.about;

import yalong.site.frame.panel.base.BasePanel;
import yalong.site.frame.panel.base.BaseTextArea;

import java.awt.*;

/**
 * @author yaLong
 * @date 2022/2/11
 */
public class AboutPanel extends BasePanel {

    public AboutPanel() {
        this.setName("关  于");
    }

    public static AboutPanel builder() {
        AboutPanel aboutPanel = new AboutPanel();
        BaseTextArea textArea = new BaseTextArea();
        textArea.setOpaque(false);
        textArea.setText("作者: YaLong\n\n"
                + "本项目开源,源代码地址:\n"
                + "https://github.com/4379711/lol-helper\n\n"
                + "本项目使用RIOT公开的LCU API开发,地址:\n"
                + "https://developer.riotgames.com/docs/lol/\n"
                + "https://riot-api-libraries.readthedocs.io/\n\n"
                + "本人承诺:绝不上传用户任何数据!!!"
        );
        textArea.setEditable(false);
        textArea.setForeground(Color.red);
        aboutPanel.add(textArea);
        return aboutPanel;
    }

}
