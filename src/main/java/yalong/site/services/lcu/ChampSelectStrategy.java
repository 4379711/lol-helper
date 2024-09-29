package yalong.site.services.lcu;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import yalong.site.bo.*;
import yalong.site.cache.FrameInnerCache;
import yalong.site.cache.FrameUserSetting;
import yalong.site.cache.FrameUserSettingPersistence;
import yalong.site.cache.GameDataCache;
import yalong.site.frame.panel.history.MyTeamMatchHistoryPanel;
import yalong.site.services.sgp.RegionSgpApi;
import yalong.site.utils.ProcessUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yalong
 */
@Slf4j
public class ChampSelectStrategy implements GameStatusStrategy {
	private final LinkLeagueClientApi api;
	private final CalculateScore calculateScore;
	private final RegionSgpApi sgpApi;

	public ChampSelectStrategy(LinkLeagueClientApi api, RegionSgpApi sgpApi, CalculateScore calculateScore) {
		this.api = api;
		this.calculateScore = calculateScore;
		this.sgpApi = sgpApi;
	}

	private void autoBanPick() throws IOException {
		// todo 有些游戏模式会让选择多次,暂未发现什么标记能够分辨是预选和确认选择,所以目前的方式让程序一直发起pick请求
		if ((FrameUserSetting.pickChampionId != null && FrameUserSetting.pickChampionId != 0) || (FrameUserSetting.banChampionId != null && FrameUserSetting.banChampionId != 0)) {
			String roomGameInfo = api.getChampSelectInfo();
			JSONObject jsonObject = JSONObject.parseObject(roomGameInfo);
			int localPlayerCellId = jsonObject.getIntValue("localPlayerCellId");
			JSONArray actions = jsonObject.getJSONArray("actions");
			for (int j = 0; j < actions.size(); j++) {
				JSONArray action = actions.getJSONArray(j);
				for (int i = 0; i < action.size(); i++) {
					JSONObject actionElement = action.getJSONObject(i);
					if (localPlayerCellId == actionElement.getIntValue("actorCellId")) {
						int actionId = actionElement.getIntValue("id");
						String type = actionElement.getString("type");
						if ("pick".equals(type)) {
							//报错处理? {"errorCode":"RPC_ERROR","httpStatus":500,"implementationDetails":{},"message":"Error response for PATCH /lol-lobby-team-builder/champ-select/v1/session/actions/2: Unable to process action change: Received status Error: INVALID_STATE instead of expected status of OK from request to teambuilder-draft:updateActionV1"}
							api.banPick("pick", actionId, FrameUserSetting.pickChampionId);
						} else {
							api.banPick("ban", actionId, FrameUserSetting.banChampionId);
						}
					}
				}

			}
		}
	}

	private void selectScore() throws IOException {
		if (FrameUserSettingPersistence.sendScore && GameDataCache.myTeamScore.isEmpty()) {
			String roomGameInfo = api.getChampSelectInfo();
			JSONObject jsonObject = JSONObject.parseObject(roomGameInfo);
			JSONArray myTeam = jsonObject.getJSONArray("myTeam");
			//可能队友还没进入房间
			if (myTeam.size() != 5) {
				return;
			}
			ArrayList<String> puuidList = new ArrayList<>();
			for (int i = 0; i < myTeam.size(); i++) {
				String puuid = myTeam.getJSONObject(i).getString("puuid");
				puuidList.add(puuid);
			}
			ArrayList<String> msg = calculateScore.dealScore2Msg(puuidList);
			if (msg != null) {
				// 查询我方队员战绩,放到公共数据区
				GameDataCache.myTeamScore = msg;
			}
		}
	}

	/**
	 * 展示队友数据
	 */
	private void showMatchHistory() throws IOException {
		if (FrameUserSettingPersistence.showMatchHistory && (FrameInnerCache.myTeamMatchHistoryPanel == null || !FrameInnerCache.myTeamMatchHistoryPanel.isVisible())) {
			String region = ProcessUtil.getClientProcess().getRegion();
			String roomGameInfo = api.getChampSelectInfo();
			JSONObject jsonObject = JSONObject.parseObject(roomGameInfo);
			JSONArray myTeam = jsonObject.getJSONArray("myTeam");
//			//可能队友还没进入房间
			if (myTeam.size() != 5) {
				log.error(myTeam.getJSONObject(0).getString("puuid"));
				return;
			}
			List<TeamSummonerBO> dataList = new ArrayList<>();
			for (int i = 0; i < myTeam.size(); i++) {
				TeamSummonerBO teamSummonerBO = new TeamSummonerBO();
				String puuid = myTeam.getJSONObject(i).getString("puuid");
				List<SpgProductsMatchHistoryBO> productsMatchHistory = sgpApi.getProductsMatchHistoryByPuuid(region, puuid, 0, 20);
				SGPRank rank = sgpApi.getRankedStatsByPuuid(puuid);
				SummonerAlias alias = sgpApi.getSummerNameByPuuids(puuid);
				SgpSummonerInfoBo summonerInfo = sgpApi.getSummerInfoByPuuid(region, puuid);
				String mapSide = api.getBlueRed();
				teamSummonerBO.setMapSide(mapSide);
				teamSummonerBO.setPuuid(puuid);
				teamSummonerBO.setRank(rank);
				teamSummonerBO.setLevel(summonerInfo.getLevel());
				teamSummonerBO.setProfileIconId(summonerInfo.getProfileIconId());
				teamSummonerBO.setName(alias.getGameName());
				teamSummonerBO.setTagLine(alias.getTagLine());
				teamSummonerBO.setPrivacy(summonerInfo.getPrivacy().equalsIgnoreCase("private"));
				teamSummonerBO.setMatchHistory(productsMatchHistory);
				dataList.add(teamSummonerBO);
			}
			if (!dataList.isEmpty()) {
				GameDataCache.myTeamMatchHistory = dataList;
			}
			if (FrameInnerCache.myTeamMatchHistoryPanel == null) {
				MyTeamMatchHistoryPanel.start();
			}else{
				MyTeamMatchHistoryPanel.start();
			}
		}
	}

	@Override
	public void doThis() {
		try {
			autoBanPick();
		} catch (Exception e) {
			log.error("ban/pick错误", e);
		}

		try {
			showMatchHistory();
		} catch (Exception e) {
			log.error("显示队友数据错误", e);
		}

		try {
			selectScore();
		} catch (Exception e) {
			log.error("sendScore错误", e);
		}
	}
}
