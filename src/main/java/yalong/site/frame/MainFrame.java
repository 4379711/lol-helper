package yalong.site.frame;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import yalong.site.frame.bo.GlobalDataBO;
import yalong.site.frame.panel.TabPane;

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
        this.setTitle("lol-helper");
        //退出直接关闭程序
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //固定大小
        this.setResizable(false);
        this.setSize(GlobalDataBO.WIDTH, GlobalDataBO.HEIGHT);
        //窗口置顶
        this.setAlwaysOnTop(true);
        //窗口居中
        this.setLocationRelativeTo(null);
    }

    public static void start() {
        MainFrame frame = new MainFrame();
        TabPane tabPane = TabPane.builder();
        frame.add(tabPane);
        frame.setVisible(true);
    }

}
