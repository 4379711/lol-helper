package yalong.site.services.hotkey;

import yalong.site.cache.FrameCache;
import yalong.site.cache.GameDataCache;
import yalong.site.utils.KeyEventUtil;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author yalong
 */
public class SendMyTeamScoreConsumer implements HotKeyConsumer {

	@Override
	public Consumer<Integer> build() {
		return i -> {
			if (FrameCache.sendScore) {
				ArrayList<String> myTeamScore = GameDataCache.myTeamScore;
				for (int j = 0; j < myTeamScore.size(); j++) {
					String s = myTeamScore.get(j);
					if (j == 0) {
						s = "我方" + s;
					}
					KeyEventUtil.sendMsg(s);
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
