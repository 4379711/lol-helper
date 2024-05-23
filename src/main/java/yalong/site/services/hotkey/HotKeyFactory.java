package yalong.site.services.hotkey;

import org.jnativehook.keyboard.NativeKeyEvent;
import yalong.site.frame.utils.DiyKeyUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yalong
 */
public class HotKeyFactory {
	private static final HashMap<Integer, HotKeyConsumer> HOT_KEY_MAP = new HashMap<>(10);

	public static void loadDefaultHotKeys() {
		HOT_KEY_MAP.put(NativeKeyEvent.VC_F1, new SendMyTeamScoreConsumer());
		HOT_KEY_MAP.put(NativeKeyEvent.VC_F2, new SendOtherTeamScoreConsumer());
		HOT_KEY_MAP.put(NativeKeyEvent.VC_END, new CaiHongPiConsumer());
		HOT_KEY_MAP.put(NativeKeyEvent.VC_HOME, new GarbageWordConsumer());
		HOT_KEY_MAP.put(NativeKeyEvent.VC_DELETE, new MarkWordDeleteConsumer());
	}

	public static HotKeyConsumer getHotKeyConsumer(int keyCode) {
		return HOT_KEY_MAP.get(keyCode);
	}

	public static void clearHotKeys() {
		HOT_KEY_MAP.clear();
		loadDefaultHotKeys();
	}

	public static void applyDiyKey() {
		ArrayList<String> list = DiyKeyUtil.loadKey();
		Map<Integer, HotKeyConsumer> map = DiyKeyUtil.parseKey2Consumer(list);
		applyDiyKey(map);
	}

	public static void applyDiyKey(Map<Integer, HotKeyConsumer> map) {
		clearHotKeys();
		HOT_KEY_MAP.putAll(map);
	}

}
