package yalong.site.services.hotkey;

import yalong.site.cache.GameDataCache;
import yalong.site.utils.KeyEventUtil;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author yalong
 */
public class SendOtherTeamScoreConsumer implements HotKeyConsumer {

	@Override
	public Consumer<Integer> build() {
		return i -> {
			ArrayList<String> otherTeamScore = GameDataCache.otherTeamScore;
			for (int j = 0; j < otherTeamScore.size(); j++) {
				String s = otherTeamScore.get(j);
				if (j == 0) {
					s = "对方" + s;
				}
				KeyEventUtil.sendMsg(s);
				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		};
	}
}
