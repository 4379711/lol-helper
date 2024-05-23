package yalong.site.services.hotkey;

import yalong.site.cache.FrameCache;
import yalong.site.cache.GameDataCache;
import yalong.site.utils.KeyEventUtil;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author yalong
 */
public class SendOtherTeamScoreConsumer implements HotKeyConsumer {

	@Override
	public Consumer<Integer> build() {
		return i -> {
			if (FrameCache.sendScore) {
				for (String s : GameDataCache.otherTeamScore) {
					KeyEventUtil.sendMsg("对方" + s);
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
