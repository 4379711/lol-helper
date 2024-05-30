package yalong.site;

import lombok.extern.slf4j.Slf4j;
import yalong.site.bo.LeagueClientBO;
import yalong.site.cache.GameDataCache;
import yalong.site.enums.GameStatusEnum;
import yalong.site.exception.NoProcessException;
import yalong.site.http.RequestLcuUtil;
import yalong.site.services.lcu.*;
import yalong.site.utils.ProcessUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author yalong
 */
@Slf4j
public class ClientStarter {
	private LinkLeagueClientApi api;

	public void initLcu() throws Exception {
		LeagueClientBO leagueClientBO = ProcessUtil.getClientProcess();
		if (leagueClientBO.equals(new LeagueClientBO())) {
			throw new NoProcessException();
		}
		RequestLcuUtil requestUtil = new RequestLcuUtil(leagueClientBO);
		api = new LinkLeagueClientApi(requestUtil);
	}

	@SuppressWarnings("InfiniteLoopStatement")
	public void listenGameStatus() throws InterruptedException, IOException {
		while (true) {
			TimeUnit.MILLISECONDS.sleep(500);
			GameStatusContext gameStatusContext = new GameStatusContext();
			CalculateScore calculateScore = new CalculateScore(api);
			//监听游戏状态
			GameStatusEnum gameStatus = api.getGameStatus();
			switch (gameStatus) {
				case None:
				case Lobby:
				case Matchmaking:
				case WaitingForStats: {
					gameStatusContext.setStrategy(new OtherStatusStrategy());
					GameDataCache.reset();
					break;
				}
				case ReadyCheck: {
					gameStatusContext.setStrategy(new ReadyCheckStrategy(api));
					break;
				}
				case ChampSelect: {
					gameStatusContext.setStrategy(new ChampSelectStrategy(api, calculateScore));
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
		}
	}

}
