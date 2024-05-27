package yalong.site.services.lcu;

import lombok.extern.slf4j.Slf4j;
import yalong.site.cache.FrameCache;

/**
 * @author yalong
 */
@Slf4j
public class PreEndOfGameStrategy implements GameStatusStrategy {
	private final LinkLeagueClientApi api;

	public PreEndOfGameStrategy(LinkLeagueClientApi api) {
		this.api = api;
	}

	@Override
	public void doThis() {
		if (FrameCache.autoPlayAgain) {
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
