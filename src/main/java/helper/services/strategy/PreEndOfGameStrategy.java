package helper.services.strategy;

import helper.cache.AppCache;
import helper.services.lcu.LinkLeagueClientApi;
import lombok.extern.slf4j.Slf4j;

/**
 * @author @_@
 */
@Slf4j
public class PreEndOfGameStrategy implements GameStatusStrategy {
	private final LinkLeagueClientApi api;

	public PreEndOfGameStrategy(LinkLeagueClientApi api) {
		this.api = api;
	}

	@Override
	public void doThis() {
		if (AppCache.settingPersistence.getAutoPlayAgain()) {
			//点赞
			try {
				String s = api.honor();
				log.info(s);
			} catch (Exception e) {
				log.error("点赞失败", e);
			}
		}
	}
}
