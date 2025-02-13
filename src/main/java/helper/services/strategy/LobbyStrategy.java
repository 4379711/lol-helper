package helper.services.strategy;

import helper.cache.AppCache;
import helper.cache.FrameInnerCache;
import helper.cache.GameDataCache;
import helper.services.lcu.LinkLeagueClientApi;
import helper.services.sgp.RegionSgpApi;
import helper.utils.StrategyUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author @_@
 */
@Slf4j
public class LobbyStrategy implements GameStatusStrategy {
    private final LinkLeagueClientApi api;
    private final RegionSgpApi sgpApi;

    public LobbyStrategy(LinkLeagueClientApi api, RegionSgpApi sgpApi) {
        this.api = api;
        this.sgpApi = sgpApi;
    }

    private void autoSearch() {
        if (AppCache.settingPersistence.getAutoSearch()) {
            // 自动寻找对局
            try {
                String search = this.api.search();
                log.info(search);
            } catch (Exception e) {
                log.error("自动寻找对局失败", e);
            }
        }
    }

    private void getQueue() {
        if (GameDataCache.leagueClient.getRegion() != null) {
            try {
                GameDataCache.queueId = sgpApi.getPartiesLedgeQueueId(GameDataCache.leagueClient.getRegion(), GameDataCache.me.getPuuid());
            } catch (IOException e) {
                log.error("获取模式id失败", e);
            }
        }
    }

    @Override
    public void doThis() {
        autoSearch();
        getQueue();
        GameDataCache.championFlag = false;
        GameDataCache.endOfGameFlag = false;
        if (FrameInnerCache.blackListAddFrame != null) {
            if (FrameInnerCache.blackListAddFrame.isVisible()) {
                FrameInnerCache.blackListAddFrame.setVisible(false);
            }
        }
        StrategyUtil.resetHistory();
        StrategyUtil.resetRoomMessageId();
        StrategyUtil.hidePanel();
    }
}
