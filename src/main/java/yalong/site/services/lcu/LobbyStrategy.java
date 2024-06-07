package yalong.site.services.lcu;

import lombok.extern.slf4j.Slf4j;
import yalong.site.cache.FrameUserSettingPersistence;

/**
 * @author yalong
 */
@Slf4j
public class LobbyStrategy implements GameStatusStrategy {
	private final LinkLeagueClientApi api;

	public LobbyStrategy(LinkLeagueClientApi api) {
		this.api = api;
	}

	@Override
	public void doThis() {
		if (FrameUserSettingPersistence.autoSearch) {
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
