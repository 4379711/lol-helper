package helper.site.services.hotkey;

import helper.site.cache.AppCache;
import helper.site.frame.utils.FrameMsgUtil;

import java.util.function.Consumer;

/**
 * @author @_@
 */
public class MarkWordDeleteConsumer implements HotKeyConsumer {
	@Override
	public Consumer<Integer> build() {
		return i -> {
			String lastCommunicateWord = AppCache.lastGarbageWord;
			if (lastCommunicateWord != null && !"".equals(lastCommunicateWord)) {
				FrameMsgUtil.sendLine("[屏蔽]:" + lastCommunicateWord);
			}
		};
	}
}
