package helper.services.hotkey;

import helper.cache.FrameUserSettingPersistence;
import helper.cache.GlobalData;
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
			if (GlobalData.settingState.getCommunicate()) {
				String s = CaihongpiWord.requestCaiHongPiText();
				if (s != null && !"".equals(s)) {
					KeyEventUtil.sendMsg(s);
				}
			}

		};
	}
}
