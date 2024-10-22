package helper.site.services.hotkey;

import helper.site.cache.AppCache;
import helper.site.services.word.CaihongpiWord;
import helper.site.utils.KeyEventUtil;

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
