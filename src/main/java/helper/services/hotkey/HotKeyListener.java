package helper.services.hotkey;

import helper.cache.AppCache;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * @author @_@
 * @date 2022/3/3 23:31
 */
public class HotKeyListener implements NativeKeyListener {
	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		HotKeyConsumerMapping hotKeyConsumer = HotKeyFactory.getHotKeyConsumer(e.getKeyCode());
		if (hotKeyConsumer != null && !HotKeyService.registerHook) {
			//屏蔽按键
			if (!AppCache.stopAuto || e.getKeyCode() == NativeKeyEvent.VC_PAUSE) {
				hotKeyConsumer.getHotKeyConsumer().build().accept(e.getKeyCode());
			}
		}
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
	}
}
