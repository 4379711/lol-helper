package yalong.site.frame;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import yalong.site.frame.bo.GlobalDataBO;
import yalong.site.frame.ui.MyTabbedPaneUI;

import javax.swing.*;
import java.awt.*;

/**
 * 主窗体
 *
 * @author yaLong
 * @date 2022/2/11
 */
public class MainFrame extends JFrame {
    static {
        //设置窗口边框样式为弱立体感半透明
        BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
        try {
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception ignored) {
        }
        //防止白屏
        System.setProperty("sun.java2d.noddraw", "true");
        //关闭右上角的的设置按钮
        UIManager.put("RootPane.setupButtonVisible", false);
    }

    public MainFrame() throws HeadlessException {
        super();
        //去掉装饰
        this.setUndecorated(true);
        this.setTitle("测试小酱油");
        this.setSize(GlobalDataBO.WIDTH, GlobalDataBO.HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

}
