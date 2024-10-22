package helper.site;

import cn.hutool.core.util.StrUtil;
import helper.site.bo.LeagueClientBO;
import helper.site.cache.AppCache;
import helper.site.cache.FrameInnerCache;
import helper.site.cache.GameDataCache;
import helper.site.enums.GameStatusEnum;
import helper.site.exception.NoLcuApiException;
import helper.site.exception.NoProcessException;
import helper.site.http.RequestLcuUtil;
import helper.site.http.RequestSgpUtil;
import helper.site.services.lcu.*;
import helper.site.services.sgp.RegionSgpApi;
import helper.site.utils.ProcessUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author @_@
 */
@Slf4j
public class ClientStarter {
	private LinkLeagueClientApi api;
	private RegionSgpApi localSgpApi;

	public void initLcu() throws Exception {
		LeagueClientBO leagueClientBO = ProcessUtil.getClientProcess();
		if (leagueClientBO.equals(new LeagueClientBO())) {
			throw new NoProcessException();
		}
		RequestLcuUtil requestUtil = new RequestLcuUtil(leagueClientBO);
		api = new LinkLeagueClientApi(requestUtil);
		GameDataCache.cacheLcuMe();
	}

	public void initSgp() throws Exception {
		LeagueClientBO leagueClientBO = ProcessUtil.getClientProcess();
		if (leagueClientBO.equals(new LeagueClientBO())) {
			throw new NoProcessException();
		}
		if (AppCache.api == null) {
			throw new NoLcuApiException();
		}
		RequestSgpUtil requestUtil = new RequestSgpUtil(AppCache.api.getSgpAccessToken(), leagueClientBO.getRegion().toLowerCase());
		localSgpApi = new RegionSgpApi(requestUtil);
	}

	@SuppressWarnings("InfiniteLoopStatement")
	public void listenGameStatus() throws InterruptedException, IOException {
		while (true) {
			TimeUnit.MILLISECONDS.sleep(500);
			if (AppCache.stopAuto) {
				continue;
			}
			GameStatusContext gameStatusContext = new GameStatusContext();
			CalculateScore calculateScore = new CalculateScore(api);
			//监听游戏状态
			GameStatusEnum gameStatus = api.getGameStatus();
			if (!gameStatus.equals(GameStatusEnum.ChampSelect)) {
				if (FrameInnerCache.myTeamMatchHistoryPanel != null && FrameInnerCache.myTeamMatchHistoryPanel.isVisible()) {
					FrameInnerCache.myTeamMatchHistoryPanel.setVisible(false);
				}
			}
			switch (gameStatus) {
				case None:
				case Matchmaking:
				case WaitingForStats: {
					gameStatusContext.setStrategy(new OtherStatusStrategy());
					GameDataCache.reset();
					break;
				}
				case Lobby: {
					gameStatusContext.setStrategy(new LobbyStrategy(api));
					break;
				}
				case ReadyCheck: {
					gameStatusContext.setStrategy(new ReadyCheckStrategy(api));
					break;
				}
				case ChampSelect: {
					gameStatusContext.setStrategy(new ChampSelectStrategy(api, localSgpApi, calculateScore));
					break;
				}
				case InProgress: {
					gameStatusContext.setStrategy(new InProgressStrategy(api, calculateScore));
					break;
				}
				case PreEndOfGame: {
					gameStatusContext.setStrategy(new PreEndOfGameStrategy(api));
					break;
				}
				case EndOfGame: {
					gameStatusContext.setStrategy(new EndOfGameStrategy(api));
					break;
				}
				case Reconnect: {
					gameStatusContext.setStrategy(new ReconnectStrategy(api));
					break;
				}
				default: {
					gameStatusContext.setStrategy(new OtherStatusStrategy());
					break;
				}
			}
			gameStatusContext.executeStrategy();

			if (StrUtil.isNotBlank(api.status)) {
				// 重新设置状态
				api.changeStatus(api.status);
			}
		}
	}

}
