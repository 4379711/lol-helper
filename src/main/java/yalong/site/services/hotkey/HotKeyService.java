package yalong.site.services.hotkey;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.GlobalScreen;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author yalong
 */
@Data
@Slf4j
public class HotKeyService {

	private static volatile boolean hasHook = false;

	private HotKeyService() {
	}

	public static void start() {
		new HotKeyService().hook();
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
			if(!GlobalScreen.isNativeHookRegistered()){
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
				if(GlobalScreen.isNativeHookRegistered()){
					GlobalScreen.unregisterNativeHook();
				}
			} catch (Exception e) {
				log.error("取消hook按键失败", e);
			}
		}));

		//注册监听器的实现
		GlobalScreen.addNativeKeyListener(new HotKeyListener());
		hasHook = true;
	}

}
