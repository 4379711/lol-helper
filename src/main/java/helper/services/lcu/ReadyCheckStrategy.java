package helper.services.lcu;

import helper.cache.AppCache;
import lombok.extern.slf4j.Slf4j;

/**
 * @author @_@
 */
@Slf4j
public class ReadyCheckStrategy implements GameStatusStrategy {
	private final LinkLeagueClientApi api;

	public ReadyCheckStrategy(LinkLeagueClientApi api) {
		this.api = api;
	}

	@Override
	public void doThis() {
		if (AppCache.settingPersistence.getAutoAccept()) {
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
