package yalong.site.services.hotkey;

import yalong.site.cache.FrameUserSettingPersistence;
import yalong.site.services.word.CaihongpiWord;
import yalong.site.utils.KeyEventUtil;

import java.util.function.Consumer;

/**
 * @author yalong
 */
public class CaiHongPiConsumer implements HotKeyConsumer {
	@Override
	public Consumer<Integer> build() {
		return i -> {
			if (FrameUserSettingPersistence.communicate) {
				String s = CaihongpiWord.requestCaiHongPiText();
				if (s != null && !"".equals(s)) {
					KeyEventUtil.sendMsg(s);
				}
			}

		};
	}
}
