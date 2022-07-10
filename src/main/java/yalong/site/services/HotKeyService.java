package yalong.site.services;

import lombok.Data;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import yalong.site.bo.GlobalData;
import yalong.site.utils.MyUser32Util;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author yalong
 */
@Data
public class HotKeyService {
    private static int keyDelay = 100;
    public static Random random = new Random();
    public static Robot robot;
    /**
     * 屏幕大小
     */
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


    public HotKeyService() {
        GlobalScreen.addNativeKeyListener(new HotKeyListener(sendOtherTeamScore()));
        GlobalScreen.addNativeKeyListener(new HotKeyListener(moyan()));
        GlobalScreen.addNativeKeyListener(new HotKeyListener(battle()));
    }


    static {
        //关闭日志
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);
        try {
            robot = new Robot();
            GlobalScreen.registerNativeHook();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //注册新的虚拟机来关闭钩子
        Runtime run = Runtime.getRuntime();
        run.addShutdownHook(new Thread(() -> {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException e) {
                e.printStackTrace();
            }
        }));
    }

    private static void sendMsg(String s) {
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(keyDelay);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.delay(keyDelay);
        MyUser32Util.sendString(s);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(keyDelay);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.delay(keyDelay);
    }

    public static Consumer<Integer> sendOtherTeamScore() {
        return i -> {
            if (GlobalData.autoSend && i == NativeKeyEvent.VC_F2) {
                for (String s : GlobalData.otherTeamScore) {
                    sendMsg(s);
                }
            }

        };
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
                robot.delay(keyDelay);
                robot.keyPress(KeyEvent.VK_D);
                robot.keyRelease(KeyEvent.VK_D);
                robot.delay(keyDelay);
                //w可能没按到,多试几次
                for (int j = 0; j < 5; j++) {
                    robot.keyPress(KeyEvent.VK_W);
                    robot.delay(keyDelay);
                    robot.keyRelease(KeyEvent.VK_W);
                    robot.delay(keyDelay);
                }
            }
        };
    }

    public static Consumer<Integer> battle() {
        return i -> {
            if (GlobalData.battle && i == NativeKeyEvent.VC_F1) {
                int i1 = random.nextInt(GlobalData.battleWords.size());
                String s = GlobalData.battleWords.get(i1);
                sendMsg(s);
            }
        };
    }

}
