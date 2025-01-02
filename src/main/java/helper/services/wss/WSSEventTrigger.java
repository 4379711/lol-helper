package helper.services.wss;

import cn.hutool.core.util.StrUtil;
import helper.cache.AppCache;
import helper.cache.FrameInnerCache;
import helper.enums.GameStatusEnum;
import helper.enums.WSSEventEnum;
import helper.services.lcu.strategy.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author WuYi
 */
@Slf4j
public class WSSEventTrigger {
    public static void eventRun(WSSEventEnum eventEnum, String data) {
        switch (eventEnum) {
            case GAMEFLOW_PHASE:
                try {
                    listenGameStatus(GameStatusEnum.valueOf(data));
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
                break;
            default:
                break;
        }
    }

    public static void listenGameStatus(GameStatusEnum gameStatus) throws IOException {
        GameStatusContext gameStatusContext = new GameStatusContext();

        if (!gameStatus.equals(GameStatusEnum.ChampSelect)) {
            if (FrameInnerCache.myTeamMatchHistoryPanel != null && FrameInnerCache.myTeamMatchHistoryPanel.isVisible()) {
                FrameInnerCache.myTeamMatchHistoryPanel.setVisible(false);
            }
        }
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
