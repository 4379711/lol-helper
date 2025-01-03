package helper.services.wss;

import helper.enums.GameStatusEnum;
import helper.enums.WSSEventEnum;
import helper.services.strategy.StrategyStarter;
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
                    StrategyStarter.listenGameStatus(GameStatusEnum.valueOf(data));
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
                break;
            default:
                break;
        }
    }
}
