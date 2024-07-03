package yalong.site;

import cn.hutool.core.lang.hash.Hash;
import lombok.extern.slf4j.Slf4j;
import yalong.site.bo.LeagueClientBO;
import yalong.site.cache.AppCache;
import yalong.site.cache.GameDataCache;
import yalong.site.enums.GameStatusEnum;
import yalong.site.exception.NoProcessException;
import yalong.site.http.RequestLcuUtil;
import yalong.site.services.lcu.*;
import yalong.site.utils.ProcessUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author yalong
 */
@Slf4j
public class ClientStarter {
	private LinkLeagueClientApi api;
	private final static Map<GameStatusEnum, GameStatusStrategy> strategyMap = new HashMap<>();
	public void initLcu() throws Exception {
		LeagueClientBO leagueClientBO = ProcessUtil.getClientProcess();
		if (leagueClientBO.equals(new LeagueClientBO())) {
			throw new NoProcessException();
		}
		RequestLcuUtil requestUtil = new RequestLcuUtil(leagueClientBO);
		api = new LinkLeagueClientApi(requestUtil);
		GameDataCache.cacheLcuMe();
	}

	@SuppressWarnings("InfiniteLoopStatement")
	public void listenGameStatus() throws InterruptedException, IOException {
		initGameStatusContext();
		while (true) {
			TimeUnit.MILLISECONDS.sleep(500);
			if(AppCache.stopAuto){
				continue;
			}
			GameStatusContext gameStatusContext = new GameStatusContext();
			GameStatusEnum gameStatus = api.getGameStatus();
			GameStatusStrategy gameStatusStrategy = strategyMap.get(gameStatus);
			if(GameStatusEnum.None.equals(gameStatus) || GameStatusEnum.Matchmaking.equals(gameStatus) || GameStatusEnum.WaitingForStats.equals(gameStatus)) {
				GameDataCache.reset();
			}
			gameStatusContext.setStrategy(gameStatusStrategy);
			gameStatusContext.executeStrategy();
		}
	}

	/**
	 * 初始化
	 */
	private void initGameStatusContext() {
		strategyMap.put(null, new OtherStatusStrategy());
		strategyMap.put(GameStatusEnum.None, new OtherStatusStrategy());
		strategyMap.put(GameStatusEnum.Matchmaking, new OtherStatusStrategy());
		strategyMap.put(GameStatusEnum.WaitingForStats, new OtherStatusStrategy());
		strategyMap.put(GameStatusEnum.Lobby, new LobbyStrategy(api));
		strategyMap.put(GameStatusEnum.ReadyCheck, new ReadyCheckStrategy(api));
		strategyMap.put(GameStatusEnum.ChampSelect, new ChampSelectStrategy(api, new CalculateScore(api)));
		strategyMap.put(GameStatusEnum.InProgress, new InProgressStrategy(api, new CalculateScore(api)));
		strategyMap.put(GameStatusEnum.PreEndOfGame, new PreEndOfGameStrategy(api));
		strategyMap.put(GameStatusEnum.EndOfGame, new EndOfGameStrategy(api));
		strategyMap.put(GameStatusEnum.Reconnect, new ReconnectStrategy(api));
	}

}
