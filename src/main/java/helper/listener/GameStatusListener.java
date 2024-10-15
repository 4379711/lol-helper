package helper.listener;

import cn.hutool.core.util.StrUtil;
import helper.cache.AppCache;
import helper.cache.FrameInnerCache;
import helper.cache.GameDataCache;
import helper.enums.GameStatusEnum;
import helper.services.lcu.*;
import helper.services.sgp.RegionSgpApi;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author WuYi
 */
@Component
public class GameStatusListener {
    @Resource
    private LinkLeagueClientApi lcuApi;
    @Resource
    private RegionSgpApi sgpApi;
    public void listenerStart() throws InterruptedException, IOException {
        while (true) {
            TimeUnit.MILLISECONDS.sleep(500);
            if (AppCache.stopAuto) {
                continue;
            }
            GameStatusContext gameStatusContext = new GameStatusContext();
            CalculateScore calculateScore = new CalculateScore(lcuApi);
            //监听游戏状态
            GameStatusEnum gameStatus = lcuApi.getGameStatus();
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
                    gameStatusContext.setStrategy(new LobbyStrategy(lcuApi));
                    break;
                }
                case ReadyCheck: {
                    gameStatusContext.setStrategy(new ReadyCheckStrategy(lcuApi));
                    break;
                }
                case ChampSelect: {
                    gameStatusContext.setStrategy(new ChampSelectStrategy(lcuApi, sgpApi, calculateScore));
                    break;
                }
                case InProgress: {
                    gameStatusContext.setStrategy(new InProgressStrategy(lcuApi, calculateScore));
                    break;
                }
                case PreEndOfGame: {
                    gameStatusContext.setStrategy(new PreEndOfGameStrategy(lcuApi));
                    break;
                }
                case EndOfGame: {
                    gameStatusContext.setStrategy(new EndOfGameStrategy(lcuApi));
                    break;
                }
                case Reconnect: {
                    gameStatusContext.setStrategy(new ReconnectStrategy(lcuApi));
                    break;
                }
                default: {
                    gameStatusContext.setStrategy(new OtherStatusStrategy());
                    break;
                }
            }
            gameStatusContext.executeStrategy();

            if (StrUtil.isNotBlank(lcuApi.status)) {
                // 重新设置状态
                lcuApi.changeStatus(lcuApi.status);
            }
        }
    }
}
