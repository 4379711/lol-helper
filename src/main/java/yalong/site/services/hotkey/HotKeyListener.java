package yalong.site.services.hotkey;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import yalong.site.cache.AppCache;

/**
 * @author yaLong
 * @date 2022/3/3 23:31
 */
public class HotKeyListener implements NativeKeyListener {
	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		HotKeyConsumer hotKeyConsumer = HotKeyFactory.getHotKeyConsumer(e.getKeyCode());
		if (hotKeyConsumer != null) {
			//屏蔽按键
			if (!AppCache.stopAuto || e.getKeyCode() == NativeKeyEvent.VC_PAUSE) {
				hotKeyConsumer.build().accept(e.getKeyCode());
			}
		}
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
	}
}
