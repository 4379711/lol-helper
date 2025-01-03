package helper.services.strategy;

import cn.hutool.core.util.StrUtil;
import helper.cache.AppCache;
import helper.enums.GameStatusEnum;

import java.io.IOException;

/**
 * @author WuYi
 */
public class StrategyStarter {
    public static void listenGameStatus(GameStatusEnum gameStatus) throws IOException {
        GameStatusContext gameStatusContext = new GameStatusContext();
        switch (gameStatus) {
            case Lobby: {
                gameStatusContext.setStrategy(new LobbyStrategy(AppCache.api, AppCache.sgpApi));
                break;
            }
            case ReadyCheck: {
                gameStatusContext.setStrategy(new ReadyCheckStrategy(AppCache.api));
                break;
            }
            case ChampSelect: {
                gameStatusContext.setStrategy(new ChampSelectStrategy(AppCache.api, AppCache.sgpApi));
                break;
            }
            case InProgress: {
                gameStatusContext.setStrategy(new InProgressStrategy(AppCache.api, AppCache.sgpApi));
                break;
            }
            case PreEndOfGame: {
                gameStatusContext.setStrategy(new PreEndOfGameStrategy(AppCache.api));
                break;
            }
            case EndOfGame: {
                gameStatusContext.setStrategy(new EndOfGameStrategy(AppCache.api));
                break;
            }
            case Reconnect: {
                gameStatusContext.setStrategy(new ReconnectStrategy(AppCache.api));
                break;
            }
            default: {
                gameStatusContext.setStrategy(new OtherStatusStrategy());
                break;
            }
        }
        gameStatusContext.executeStrategy();

        if (StrUtil.isNotBlank(AppCache.api.status)) {
            // 重新设置状态
            AppCache.api.changeStatus(AppCache.api.status);
        }
    }
}
