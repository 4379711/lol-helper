package yalong.site.services;

import org.jnativehook.keyboard.NativeKeyEvent;
import yalong.site.bo.GlobalData;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.function.Consumer;

/**
 * @author yaLong
 * @date 2022/3/3 21:16
 */
public class KeyService {
    public static Random random = new Random();
    public static Robot robot;
    /**
     * 屏幕大小
     */
    public static Dimension screenSize;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    }

    /**
     * 瞎子光速摸眼
     */
    public static Consumer<Integer> moyan() {
        return i -> {
            if (GlobalData.moyan && i == NativeKeyEvent.VC_T) {
                // 按键:4DW
                robot.keyPress(KeyEvent.VK_4);
                robot.keyRelease(KeyEvent.VK_4);
                robot.delay(50);
                robot.keyPress(KeyEvent.VK_D);
                robot.keyRelease(KeyEvent.VK_D);
                robot.delay(50);
                //w可能没按到,多试几次
                for (int j = 0; j < 5; j++) {
                    robot.keyPress(KeyEvent.VK_W);
                    robot.delay(50);
                    robot.keyRelease(KeyEvent.VK_W);
                    robot.delay(50);
                }
            }
        };
    }

    /**
     * 挂机模式,自己乱动
     */
    public static void leave() {
        while (true) {
            if (GlobalData.leave) {
                // 过滤屏幕下边的1/4区域
                int xx = screenSize.width / 4;
                int yy = screenSize.height / 4;
                int x = random.nextInt(xx * 4);
                int y = random.nextInt(yy * 3);
                //移动
                robot.mouseMove(x, y);
                //鼠标右键点击
                robot.mousePress(KeyEvent.BUTTON3_DOWN_MASK);
                robot.delay(100);
                robot.mouseRelease(KeyEvent.BUTTON3_DOWN_MASK);
            }
            robot.delay(3000);
        }
    }


}
