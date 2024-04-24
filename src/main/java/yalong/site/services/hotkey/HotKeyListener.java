package yalong.site.services.hotkey;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

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
			hotKeyConsumer.build().accept(e.getKeyCode());
		}
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
	}
}
