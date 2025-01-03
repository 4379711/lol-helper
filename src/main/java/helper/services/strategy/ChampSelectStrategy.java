package helper.services.strategy;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import helper.bo.TeamSummonerBO;
import helper.cache.AppCache;
import helper.cache.FrameInnerCache;
import helper.cache.GameDataCache;
import helper.frame.panel.history.MyTeamMatchHistoryPanel;
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
public class ChampSelectStrategy implements GameStatusStrategy {
	private final LinkLeagueClientApi api;
	private final RegionSgpApi sgpApi;

	public ChampSelectStrategy(LinkLeagueClientApi api, RegionSgpApi sgpApi) {
		this.api = api;
		this.sgpApi = sgpApi;
	}

	private void autoBanPick() throws IOException {
		if ((AppCache.settingPersistence.getPickChampionId() != null && AppCache.settingPersistence.getPickChampionId() != 0) || (AppCache.settingPersistence.getBanChampionId() != null && AppCache.settingPersistence.getBanChampionId() != 0)) {
			String roomGameInfo = api.getChampSelectInfo();
			JSONObject jsonObject = JSONObject.parseObject(roomGameInfo);
			JSONObject timer = jsonObject.getJSONObject("timer");
			String phase = timer.getString("phase");
			int localPlayerCellId = jsonObject.getIntValue("localPlayerCellId");
			JSONArray actions = jsonObject.getJSONArray("actions");
			for (int j = 0; j < actions.size(); j++) {
				JSONArray action = actions.getJSONArray(j);
				for (int i = 0; i < action.size(); i++) {
					JSONObject actionElement = action.getJSONObject(i);
					if (localPlayerCellId == actionElement.getIntValue("actorCellId") && !actionElement.getBooleanValue("completed")) {
						int actionId = actionElement.getIntValue("id");
						String type = actionElement.getString("type");
						//预选英雄
						if ("pick".equals(type) && "PLANNING".equals(phase)) {
							api.banPick("pick", actionId, AppCache.settingPersistence.getPickChampionId());
						}
						//选英雄
						else if ("pick".equals(type) && "BAN_PICK".equals(phase)) {
							api.banPick("pick", actionId, AppCache.settingPersistence.getPickChampionId());
						}
						//ban英雄
						else if ("ban".equals(type) && "BAN_PICK".equals(phase)) {
							api.banPick("ban", actionId, AppCache.settingPersistence.getBanChampionId());
						}
					}
				}

			}
		}
	}

	private void selectScore() throws IOException {
		if (AppCache.settingPersistence.getSendScore() && GameDataCache.myTeamScore.isEmpty()) {
			if (!GameDataCache.myTeamMatchHistory.isEmpty()) {
				ArrayList<String> msg = MatchHistoryUtil.dealScoreMsg(GameDataCache.myTeamMatchHistory,true);
				if (!msg.isEmpty()) {
					// 查询我方队员战绩,放到公共数据区
					GameDataCache.myTeamScore = msg;
				}
			} else {
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

				List<TeamSummonerBO> dataList = new ArrayList<>();
				for (String puuid : puuidList) {
					dataList.add(MatchHistoryUtil.buildTeamSummoner(puuid, sgpApi));
				}
				GameDataCache.myTeamMatchHistory = dataList;
				ArrayList<String> msg = MatchHistoryUtil.dealScoreMsg(GameDataCache.myTeamMatchHistory, true);
				if (!msg.isEmpty()) {
					// 查询我方队员战绩,放到公共数据区
					GameDataCache.myTeamScore = msg;
				}
			}
		}
	}

	/**
	 * 展示队友数据
	 */
	private void showMatchHistory() throws IOException {
		if (FrameInnerCache.myTeamMatchHistoryPanel == null || !FrameInnerCache.myTeamMatchHistoryPanel.isVisible()) {
			if (AppCache.settingPersistence.getShowMatchHistory()) {
				String roomGameInfo = api.getChampSelectInfo();
				JSONObject jsonObject = JSONObject.parseObject(roomGameInfo);
				JSONArray myTeam = jsonObject.getJSONArray("myTeam");
				//可能队友还没进入房间
				if (myTeam.size() != 5) {
					log.error(myTeam.getJSONObject(0).getString("puuid"));
					return;
				}
				List<TeamSummonerBO> dataList = new ArrayList<>();
				for (int i = 0; i < myTeam.size(); i++) {
					String puuid = myTeam.getJSONObject(i).getString("puuid");
					TeamSummonerBO teamSummonerBO = MatchHistoryUtil.buildTeamSummoner(puuid, sgpApi);
					String mapSide = api.getBlueRed();
					teamSummonerBO.setMapSide(mapSide);
					dataList.add(teamSummonerBO);
				}
				if (!dataList.isEmpty()) {
					GameDataCache.myTeamMatchHistory = dataList;
				}
				MyTeamMatchHistoryPanel.start();
			} else if (AppCache.settingPersistence.getPickSkin()) {
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
