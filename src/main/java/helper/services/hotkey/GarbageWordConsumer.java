package helper.services.hotkey;

import helper.cache.AppCache;
import helper.cache.FrameUserSettingPersistence;
import helper.cache.GlobalData;
import helper.utils.KeyEventUtil;

import java.util.function.Consumer;

/**
 * @author @_@
 */
public class GarbageWordConsumer implements HotKeyConsumer {
	private static int nextLineNo = 0;

	@Override
	public Consumer<Integer> build() {
		return i -> {
			if (GlobalData.settingState.getCommunicate()) {
				String s = AppCache.garbageWordList.get(nextLineNo);
				KeyEventUtil.sendMsg(s);
				int size = AppCache.garbageWordList.size();
				nextLineNo = (nextLineNo + 1) % size;
				AppCache.lastGarbageWord = s;
			}
		};
	}
}
