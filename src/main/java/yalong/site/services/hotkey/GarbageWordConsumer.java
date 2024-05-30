package yalong.site.services.hotkey;

import yalong.site.cache.AppCache;
import yalong.site.cache.FrameUserSettingPersistence;
import yalong.site.utils.KeyEventUtil;

import java.util.function.Consumer;

/**
 * @author yalong
 */
public class GarbageWordConsumer implements HotKeyConsumer {
	private static int nextLineNo = 0;

	@Override
	public Consumer<Integer> build() {
		return i -> {
			if (FrameUserSettingPersistence.communicate) {
				String s = AppCache.garbageWordList.get(nextLineNo);
				KeyEventUtil.sendMsg(s);
				int size = AppCache.garbageWordList.size();
				nextLineNo = (nextLineNo + 1) % size;
				AppCache.lastGarbageWord = s;
			}
		};
	}
}
