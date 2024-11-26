package helper.services.lcu.strategy;

import helper.cache.AppCache;
import helper.cache.GameDataCache;
import helper.services.lcu.LinkLeagueClientApi;
import helper.services.sgp.RegionSgpApi;
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
                Integer queueId = sgpApi.getPartiesLedgeQueueId(GameDataCache.leagueClient.getRegion(), GameDataCache.me.getPuuid());
                GameDataCache.queueId = queueId;
                System.out.println(queueId);
            } catch (IOException e) {
                log.error("获取模式id失败", e);
            }
        }
    }

    /**
     * 重置战绩和评分
     */
    private void resetHistory() {
        if (!GameDataCache.myTeamMatchHistory.isEmpty() || !GameDataCache.enemyTeamMatchHistory.isEmpty() || !GameDataCache.myTeamScore.isEmpty() || !GameDataCache.enemyTeamScore.isEmpty()) {
            GameDataCache.reset();
        }
    }

    @Override
    public void doThis() {
        autoSearch();
        getQueue();
        resetHistory();
    }
}
