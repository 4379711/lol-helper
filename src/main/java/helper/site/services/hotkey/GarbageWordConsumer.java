package helper.site.services.hotkey;

import helper.site.cache.AppCache;
import helper.site.utils.KeyEventUtil;

import java.util.function.Consumer;

/**
 * @author @_@
 */
public class GarbageWordConsumer implements HotKeyConsumer {
	private static int nextLineNo = 0;

	@Override
	public Consumer<Integer> build() {
		return i -> {
			if (AppCache.settingPersistence.getCommunicate()) {
				String s = AppCache.garbageWordList.get(nextLineNo);
				KeyEventUtil.sendMsg(s);
				int size = AppCache.garbageWordList.size();
				nextLineNo = (nextLineNo + 1) % size;
				AppCache.lastGarbageWord = s;
			}
		};
	}
}
