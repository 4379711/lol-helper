package yalong.site.utils;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import yalong.site.services.KeyBoardListener;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author liuyalong
 */
public class KeyBoardUtil {
    static {
        //关闭日志
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
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

    public static void addListener(KeyBoardListener listener) {
        GlobalScreen.addNativeKeyListener(listener);
    }

    public static void removeListener(KeyBoardListener listener) {
        GlobalScreen.removeNativeKeyListener(listener);
    }


}