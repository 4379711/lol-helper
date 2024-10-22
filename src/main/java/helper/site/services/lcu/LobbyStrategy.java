package helper.site.services.lcu;

import helper.site.cache.AppCache;
import lombok.extern.slf4j.Slf4j;

/**
 * @author @_@
 */
@Slf4j
public class LobbyStrategy implements GameStatusStrategy {
	private final LinkLeagueClientApi api;

	public LobbyStrategy(LinkLeagueClientApi api) {
		this.api = api;
	}

	@Override
	public void doThis() {
		if (AppCache.settingPersistence.getAutoSearch()) {
			// 自动寻找对局
			try {
				String search = this.api.search();
				log.info(search);
			} catch (Exception e) {
				log.error("自动寻找对局失败", e);
			}
		}
	}
}
