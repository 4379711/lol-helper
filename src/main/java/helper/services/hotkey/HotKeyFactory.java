package helper.services.hotkey;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson2.JSON;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

/**
 * @author @_@
 */
public class HotKeyFactory {
	public final static String HOTKEY_FILE = "hotkey.json";
	public static ArrayList<HotKeyConsumerMapping> HOT_KEY_LIST = new ArrayList<>();

	public static void loadDefaultHotKeys() {
		boolean flag = loadFile();
		if (!flag) {
			HOT_KEY_LIST.add(new HotKeyConsumerMapping(NativeKeyEvent.VC_PAUSE, new ControlAutoFuncConsumer()));
			HOT_KEY_LIST.add(new HotKeyConsumerMapping(NativeKeyEvent.VC_F1, new SendMyTeamScoreConsumer()));
			HOT_KEY_LIST.add(new HotKeyConsumerMapping(NativeKeyEvent.VC_F2, new SendOtherTeamScoreConsumer()));
			HOT_KEY_LIST.add(new HotKeyConsumerMapping(NativeKeyEvent.VC_END, new CaiHongPiConsumer()));
			HOT_KEY_LIST.add(new HotKeyConsumerMapping(NativeKeyEvent.VC_HOME, new GarbageWordConsumer()));
			HOT_KEY_LIST.add(new HotKeyConsumerMapping(NativeKeyEvent.VC_DELETE, new MarkWordDeleteConsumer()));
		}
	}

	public static HotKeyConsumerMapping getHotKeyConsumer(int keyCode) {
		Optional<HotKeyConsumerMapping> optional = HOT_KEY_LIST.stream().filter(item -> item.getKeyCode() == keyCode).findFirst();
		return optional.orElse(null);
	}

	public static void clearHotKeys() {
		HOT_KEY_LIST.clear();
		loadDefaultHotKeys();
	}

	/**
	 * 保存 HOT_KEY_LIST 到 JSON 文件
	 */
	public static void saveFile() {
		String jsonString = JSON.toJSONString(HOT_KEY_LIST);
		FileUtil.writeUtf8String(jsonString, new File(HOTKEY_FILE));
	}

	/**
	 * 从 JSON 文件加载 HOT_KEY_LIST
	 */
	public static boolean loadFile() {
		boolean exist = FileUtil.exist(new File(HOTKEY_FILE));
		if (!exist) {
			return false;
		}
		String jsonString = FileUtil.readUtf8String(new File(HOTKEY_FILE));
		ArrayList<HotKeyConsumerMapping> loadedList = (ArrayList<HotKeyConsumerMapping>) JSON.parseArray(jsonString, HotKeyConsumerMapping.class);
		for (HotKeyConsumerMapping mapping : loadedList) {
			mapping.instantiateConsumer();
			HOT_KEY_LIST.add(mapping);
		}
		return true;
	}
}
