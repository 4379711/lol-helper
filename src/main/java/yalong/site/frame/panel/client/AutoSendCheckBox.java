package yalong.site.frame.panel.client;

import yalong.site.bo.GlobalData;
import yalong.site.frame.bo.ComponentBO;
import yalong.site.frame.panel.base.BaseCheckBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;

/**
 * @author yaLong
 * @date 2022/2/14
 */
public class AutoSendCheckBox extends BaseCheckBox {

    public AutoSendCheckBox() {
        this.setText("自动发送战绩");
        this.setSelected(GlobalData.autoSend);
        this.addItemListener(listener());
        this.addActionListener(i -> {
            AutoSendCheckBox source = (AutoSendCheckBox) i.getSource();
            //选中状态下弹框提示
            if (source.getSelectedObjects() != null) {
                JOptionPane.showMessageDialog(null, "按F2发送战绩");
            }
        });
    }

    private ItemListener listener() {
        return e -> GlobalData.autoSend = e.getStateChange() == ItemEvent.SELECTED;

    }

    /**
     * @return 带布局的盒子
     */
    public static ComponentBO builder() {
        //喷人的词语从文件读取
        URL resource = AutoSendCheckBox.class.getResource("/fuck.txt");
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(resource.getFile()));
            while ((line = reader.readLine()) != null) {
                GlobalData.battleWords.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        AutoSendCheckBox box = new AutoSendCheckBox();
        GridBagConstraints grid = new GridBagConstraints(
                // 第(2,1)个格子
                2, 1,
                // 占1列,占1行
                1, 1,
                //横向占100%长度,纵向占100%长度
                100, 100,
                //居中,组件小的话就两边铺满窗格
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                // 窗格之间的距离
                new Insets(0, 0, 0, 0),
                // 增加组件的首选宽度和高度
                0, 0
        );
        return new ComponentBO(box, grid);
    }

}
