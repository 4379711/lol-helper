package helper.services.hotkey;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author @_@
 */
@Data
@Slf4j
public class HotKeyService {

	private static volatile boolean hasHook = false;
    public static boolean registerHook = false;

	private HotKeyService() {
	}

	public static void start() {
		new HotKeyService().hook();
        registerHook = false;
		HotKeyFactory.loadDefaultHotKeys();
	}



	private synchronized void hook() {
		if (hasHook) {
			return;
		}
		try {
			//关闭日志
			Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
			logger.setLevel(Level.OFF);
			logger.setUseParentHandlers(false);
			if (!GlobalScreen.isNativeHookRegistered()) {
				GlobalScreen.registerNativeHook();
			}
		} catch (Exception e) {
			log.error("hook按键失败", e);
			throw new RuntimeException(e);
		}

		//注册新的虚拟机来关闭钩子
		Runtime run = Runtime.getRuntime();
		run.addShutdownHook(new Thread(() -> {
			try {
				if (GlobalScreen.isNativeHookRegistered()) {
					GlobalScreen.unregisterNativeHook();
				}
			} catch (Exception e) {
				log.error("取消hook按键失败", e);
			}
		}));

		//注册监听器的实现
		GlobalScreen.addNativeKeyListener(new HotKeyListener());
        log.info("hook按键成功");
		hasHook = true;
	}

    /**
     * 新增方法临时添加监听器并返回按键
     */
    public static synchronized void listenForKeyRegister(KeyEventCallback callback) {
        if (!hasHook) {
            log.error("Hook未注册，无法进行临时监听");
            throw new RuntimeException();
        }
        NativeKeyListener listener = new NativeKeyListener() {
            @Override
            public void nativeKeyPressed(NativeKeyEvent e) {
                callback.onKeyPressed(e);
                // 解除监听
                GlobalScreen.removeNativeKeyListener(this);
            }

            @Override
            public void nativeKeyReleased(NativeKeyEvent e) {
            }

            @Override
            public void nativeKeyTyped(NativeKeyEvent e) {
            }
        };
        GlobalScreen.addNativeKeyListener(listener);
    }

    // 回调接口
    public interface KeyEventCallback {
        void onKeyPressed(NativeKeyEvent e);
    }
}
