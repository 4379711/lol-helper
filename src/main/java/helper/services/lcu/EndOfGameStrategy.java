package helper.services.lcu;

import helper.cache.AppCache;
import lombok.extern.slf4j.Slf4j;

/**
 * @author @_@
 */
@Slf4j
public class EndOfGameStrategy implements GameStatusStrategy {
	private final LinkLeagueClientApi api;

	public EndOfGameStrategy(LinkLeagueClientApi api) {
		this.api = api;
	}

	@Override
	public void doThis() {
		if (AppCache.settingPersistence.getAutoPlayAgain()) {
			//再来一局
			try {
				String s = api.playAgain();
				log.info(s);
			} catch (Exception e) {
				log.error("再来一局失败", e);
			}
		}
	}
}
