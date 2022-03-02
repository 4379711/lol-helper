package yalong.site.frame;

import yalong.site.bo.GlobalData;
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
    private static MainFrame frame;

    public MainFrame() throws HeadlessException {
        super();
        this.setTitle("lol-helper");
        Image image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/assets/logo.png"));
        this.setIconImage(image);
        //退出直接关闭程序
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //固定大小
        this.setResizable(false);
        this.setSize(GlobalData.WIDTH, GlobalData.HEIGHT);
        //窗口居中
        this.setLocationRelativeTo(null);
        //窗口置顶
        // this.setAlwaysOnTop(true);
    }

    public static void start() {
        frame = new MainFrame();
        TabPane tabPane = TabPane.builder();
        frame.add(tabPane);
        frame.setVisible(true);
    }

    public static void hiddenFrame() {
        if (frame != null) {
            frame.dispose();
        }
    }

    public static int continueRun(String msg) {
        Object[] options = {" 重试 ", " 退出 "};
        return JOptionPane.showOptionDialog(null, msg, "无法运行", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
    }

}
