package yalong.site.services.lcu;

import lombok.extern.slf4j.Slf4j;
import yalong.site.bo.TeamPuuidBO;
import yalong.site.cache.AppCache;
import yalong.site.cache.FrameCache;
import yalong.site.cache.GameDataCache;

import java.io.IOException;
import java.util.List;

/**
 * @author yalong
 */
@Slf4j
public class InProgressStrategy implements GameStatusStrategy {
	private final LinkLeagueClientApi api;
	private final CalculateScore calculateScore;

	public InProgressStrategy(LinkLeagueClientApi api, CalculateScore calculateScore) {
		this.api = api;
		this.calculateScore = calculateScore;
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
		if (FrameCache.sendScore && GameDataCache.otherTeamScore.isEmpty()) {
			try {
				List<String> otherPuuid = getOtherPuuid();
				if (!otherPuuid.contains(null)) {
					// 查询对方队员战绩,放到缓存区
					GameDataCache.otherTeamScore = calculateScore.dealScore2Msg(otherPuuid);
				}
			} catch (Exception e) {
				log.error("查询战绩失败", e);
			}
		}
	}
}
