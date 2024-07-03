package yalong.site.services.hotkey;

import yalong.site.cache.AppCache;
import yalong.site.frame.utils.FrameMsgUtil;

import java.util.function.Consumer;

/**
 * @author yalong
 */
public class MarkWordDeleteConsumer implements HotKeyConsumer {
	@Override
	public Consumer<Integer> build() {
		return i -> {
			String lastCommunicateWord = AppCache.lastGarbageWord;
			if (lastCommunicateWord != null && !lastCommunicateWord.isEmpty()) {
				FrameMsgUtil.sendLine("[屏蔽]:" + lastCommunicateWord);
			}
		};
	}
}
