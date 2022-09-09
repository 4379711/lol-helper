package yalong.site.frame.panel.client;

import cn.hutool.core.io.FileUtil;
import yalong.site.bo.GlobalData;
import yalong.site.frame.bo.ComponentBO;
import yalong.site.frame.panel.base.BaseCheckBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

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
    public static ComponentBO builder()  {
        String userFile="words.txt";
        //喷人的词语从文件读取
        boolean exist = FileUtil.exist(userFile);
        if(!exist){
            //读取默认文件
            try {
                InputStream inputStream = AutoSendCheckBox.class.getResourceAsStream("/fuck.txt");
                //复制默认文件
                FileUtil.writeFromStream(inputStream,new File(userFile));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //加载到内存
        try {
            InputStream inputStream = new FileInputStream(userFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                GlobalData.communicateWords.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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
