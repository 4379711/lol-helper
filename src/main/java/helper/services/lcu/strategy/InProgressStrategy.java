package helper.services.lcu.strategy;

import helper.bo.TeamPuuidBO;
import helper.bo.TeamSummonerBO;
import helper.cache.AppCache;
import helper.cache.GameDataCache;
import helper.frame.utils.MatchHistoryUtil;
import helper.services.lcu.LinkLeagueClientApi;
import helper.services.sgp.RegionSgpApi;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author @_@
 */
@Slf4j
public class InProgressStrategy implements GameStatusStrategy {
	private final LinkLeagueClientApi api;
	private final RegionSgpApi sgpApi;

	public InProgressStrategy(LinkLeagueClientApi api, RegionSgpApi sgpApi) {
		this.api = api;
		this.sgpApi = sgpApi;
	}

	/**
	 * 查询对方puuid,必须进入游戏后才能查到
	 */
	public List<String> getOtherPuuid() throws IOException {
		String ownerPuuid = GameDataCache.me.getPuuid();
		//查询两队的所有人puuid
		TeamPuuidBO teamPuuidBO = api.getTeamPuuid();
		//找出对手的puuid
		List<String> puuidList = teamPuuidBO.getTeamPuuid1();
		if (puuidList.contains(ownerPuuid)) {
			puuidList = teamPuuidBO.getTeamPuuid2();
		}
		return puuidList;
	}

	@Override
	public void doThis() {
		if (AppCache.settingPersistence.getSendScore() && (GameDataCache.enemyTeamScore.isEmpty() || GameDataCache.enemyTeamMatchHistory.isEmpty())) {
			try {
				List<String> enemyPuuids = getOtherPuuid();
				if (!enemyPuuids.contains(null)) {
					List<TeamSummonerBO> dataList = new ArrayList<>();
					for (String enemyPuuid : enemyPuuids) {
						dataList.add(MatchHistoryUtil.buildTeamSummoner(enemyPuuid, sgpApi));
					}
					GameDataCache.enemyTeamMatchHistory = dataList;
					// 查询对方队员战绩,放到缓存区
					GameDataCache.enemyTeamScore = MatchHistoryUtil.dealScoreMsg(GameDataCache.enemyTeamMatchHistory, false);
				}
			} catch (Exception e) {
				log.error("查询战绩失败", e);
			}
		}
	}
}
