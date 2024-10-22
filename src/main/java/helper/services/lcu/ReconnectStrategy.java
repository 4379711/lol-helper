package helper.services.lcu;

import helper.cache.AppCache;
import lombok.extern.slf4j.Slf4j;

/**
 * @author @_@
 */
@Slf4j
public class ReconnectStrategy implements GameStatusStrategy {
	private final LinkLeagueClientApi api;

	public ReconnectStrategy(LinkLeagueClientApi api) {
		this.api = api;
	}

	@Override
	public void doThis() {
		if (AppCache.settingPersistence.getAutoReconnect()) {
			//重连
			try {
				String reconnect = api.reconnect();
				log.info(reconnect);
			} catch (Exception e) {
				log.error("掉线重连失败", e);
			}
		}
	}
}
