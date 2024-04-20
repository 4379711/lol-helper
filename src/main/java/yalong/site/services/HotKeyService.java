package yalong.site.services;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import yalong.site.bo.GlobalData;
import yalong.site.frame.utils.FrameMsgUtil;
import yalong.site.utils.MyUser32Util;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author yalong
 */
@Data
public class HotKeyService {
    public static Robot robot;
    /**
     * 屏幕大小
     */
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static int keyDelay = 100;
    private static int nextLineNo = 0;

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

    public static void start() {
        GlobalScreen.addNativeKeyListener(new HotKeyListener(sendTeamScoreF2()));
        GlobalScreen.addNativeKeyListener(new HotKeyListener(moYanT()));
        GlobalScreen.addNativeKeyListener(new HotKeyListener(caiHongEnd()));
        GlobalScreen.addNativeKeyListener(new HotKeyListener(garbageHome()));
        GlobalScreen.addNativeKeyListener(new HotKeyListener(markWordDelete()));
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

    public static Consumer<Integer> sendTeamScoreF2() {
        return i -> {
            if (GlobalData.sendScore && i == NativeKeyEvent.VC_F2) {
                switch (GlobalData.gameStatus) {
                    case ChampSelect:
                        for (String s : GlobalData.myTeamScore) {
                            sendMsg("我方" + s);
                        }
                        break;
                    case InProgress:
                        for (String s : GlobalData.otherTeamScore) {
                            sendMsg("对方" + s);
                        }
                        break;
                    default:
                        break;
                }

            }

        };
    }

    /**
     * 瞎子光速摸眼
     */
    public static Consumer<Integer> moYanT() {
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

    public static String requestCaiHongPiText() {
        String result = HttpUtil.get("https://api.shadiao.pro/chp");
        return JSONUtil.parseObj(result).getByPath("data.text", String.class);
    }

    public static Consumer<Integer> caiHongEnd() {
        return i -> {
            if (GlobalData.communicate && i == NativeKeyEvent.VC_END) {
                String s = requestCaiHongPiText();
                if (!"".equals(s)) {
                    sendMsg(s);
                }
            }
        };
    }

    public static Consumer<Integer> garbageHome() {
        return i -> {
            if (GlobalData.communicate && i == NativeKeyEvent.VC_HOME) {
                String s = GlobalData.communicateWords.get(nextLineNo);
                sendMsg(s);
                int size = GlobalData.communicateWords.size();
                nextLineNo = (nextLineNo + 1) % size;
                GlobalData.lastCommunicateWord = s;
            }
        };
    }

    public static Consumer<Integer> markWordDelete() {
        return i -> {
            if (GlobalData.communicate && i == NativeKeyEvent.VC_DELETE) {
                String lastCommunicateWord = GlobalData.lastCommunicateWord;
                if (lastCommunicateWord != null && !"".equals(lastCommunicateWord)) {
                    FrameMsgUtil.sendLine("[屏蔽]:" + lastCommunicateWord);
                }
            }
        };
    }

}
