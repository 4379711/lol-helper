package helper.services.strategy;

import helper.cache.AppCache;
import helper.cache.GameDataCache;
import helper.services.lcu.LinkLeagueClientApi;
import helper.utils.StrategyUtil;
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

	private void autoAccept() {
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

	@Override
	public void doThis() {
		autoAccept();
		GameDataCache.championFlag = false;
		StrategyUtil.resetHistory();
		StrategyUtil.resetRoomMessageId();
		StrategyUtil.hidePanel();
	}


}
