package helper.site.services.hotkey;

import helper.site.cache.AppCache;
import helper.site.cache.GameDataCache;
import helper.site.utils.KeyEventUtil;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author @_@
 */
public class SendMyTeamScoreConsumer implements HotKeyConsumer {

	@Override
	public Consumer<Integer> build() {
		return i -> {
			if (AppCache.settingPersistence.getSendScore()) {
				for (String s : GameDataCache.myTeamScore) {
					KeyEventUtil.sendMsg("我方" + s);
					try {
						TimeUnit.MILLISECONDS.sleep(500);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
			}
		};
	}
}
