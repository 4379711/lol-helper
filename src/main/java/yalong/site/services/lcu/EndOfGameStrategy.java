package yalong.site.services.lcu;

import lombok.extern.slf4j.Slf4j;
import yalong.site.cache.FrameCache;
import yalong.site.cache.GameDataCache;

/**
 * @author yalong
 */
@Slf4j
public class EndOfGameStrategy implements GameStatusStrategy {
	private final LinkLeagueClientApi api;

	public EndOfGameStrategy(LinkLeagueClientApi api) {
		this.api = api;
	}

	@Override
	public void doThis() {
		if (FrameCache.autoPlayAgain) {
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
