package yalong.site.services.lcu;

import lombok.extern.slf4j.Slf4j;
import yalong.site.cache.FrameCache;
import yalong.site.cache.GameDataCache;

/**
 * @author yalong
 */
@Slf4j
public class ReadyCheckStrategy implements GameStatusStrategy {
	private final LinkLeagueClientApi api;

	public ReadyCheckStrategy(LinkLeagueClientApi api) {
		this.api = api;
	}

	@Override
	public void doThis() {
		//接受对局时为游戏开始时机,此时清除对局缓存
		GameDataCache.reset();
		if (FrameCache.autoAccept) {
			// 自动接受对局
			try {
				String accept = this.api.accept();
				log.info(accept);
			} catch (Exception e) {
				log.error("自动接受对局失败", e);
			}
		}
	}
}
