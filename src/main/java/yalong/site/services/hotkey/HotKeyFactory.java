package yalong.site.services.hotkey;

import org.jnativehook.keyboard.NativeKeyEvent;

import java.util.HashMap;

/**
 * @author yalong
 */
public class HotKeyFactory {
	private static final HashMap<Integer, HotKeyConsumer> HOT_KEY_MAP = new HashMap<>(10);

	static {
		HOT_KEY_MAP.put(NativeKeyEvent.VC_F1, new SendMyTeamScoreConsumer());
		HOT_KEY_MAP.put(NativeKeyEvent.VC_F2, new SendOtherTeamScoreConsumer());
		HOT_KEY_MAP.put(NativeKeyEvent.VC_T, new MoyanConsumer());
		HOT_KEY_MAP.put(NativeKeyEvent.VC_END, new CaiHongPiConsumer());
		HOT_KEY_MAP.put(NativeKeyEvent.VC_HOME, new GarbageWordConsumer());
		HOT_KEY_MAP.put(NativeKeyEvent.VC_DELETE, new MarkWordDeleteConsumer());
	}

	public static HotKeyConsumer getHotKeyConsumer(int keyCode) {
		return HOT_KEY_MAP.get(keyCode);
	}

}
