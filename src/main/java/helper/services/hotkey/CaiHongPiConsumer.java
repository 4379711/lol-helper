package helper.services.hotkey;

import helper.cache.AppCache;
import helper.services.word.CaihongpiWord;
import helper.utils.KeyEventUtil;

import java.util.function.Consumer;

/**
 * @author @_@
 */
public class CaiHongPiConsumer implements HotKeyConsumer {
	@Override
	public Consumer<Integer> build() {
		return i -> {
			if (AppCache.settingPersistence.getCommunicate()) {
				String s = CaihongpiWord.requestCaiHongPiText();
				if (s != null && !"".equals(s)) {
					KeyEventUtil.sendMsg(s);
				}
			}

		};
	}
}
