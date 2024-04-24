package yalong.site.services.lcu;

import lombok.extern.slf4j.Slf4j;
import yalong.site.enums.GameStatusEnum;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author yaLong
 */
@Slf4j
public class LeagueClientService {
	private final LinkLeagueClientApi api;

	public LeagueClientService(LinkLeagueClientApi api) {
		this.api = api;
	}

	/**
	 * 不同游戏状态做不同的事情
	 */
	public void switchGameStatus() throws IOException {
		GameStatusContext gameStatusContext = new GameStatusContext();
		CalculateScore calculateScore = new CalculateScore(api);
		//监听游戏状态
		GameStatusEnum gameStatus = api.getGameStatus();
		switch (gameStatus) {
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
			case EndOfGame: {
				gameStatusContext.setStrategy(new EndOfGameStrategy(api));
				break;
			}
			case Reconnect: {
				gameStatusContext.setStrategy(new ReconnectStrategy(api));
				break;
			}
			default: {
				break;
			}
		}
		gameStatusContext.executeStrategy();
	}

}
