package helper.services.strategy;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import helper.bo.ChatMessage;
import helper.bo.SummonerAlias;
import helper.bo.TeamSummonerBO;
import helper.cache.AppCache;
import helper.cache.FrameInnerCache;
import helper.cache.FrameSetting;
import helper.cache.GameDataCache;
import helper.frame.panel.history.MyTeamMatchHistoryPanel;
import helper.frame.utils.BlackListUtil;
import helper.frame.utils.MatchHistoryUtil;
import helper.services.lcu.LinkLeagueClientApi;
import helper.services.sgp.RegionSgpApi;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;

/**
 * @author @_@
 */
@Slf4j
public class ChampSelectStrategy implements GameStatusStrategy {
	private final LinkLeagueClientApi api;
	private final RegionSgpApi sgpApi;
	/**
	 * 禁英雄列表
	 */
	private static Map<String, ArrayList<Integer>> selectBanMap = null;
	/**
	 * 秒选英雄列表
	 */
	private static Map<String, ArrayList<Integer>> selectPickMap = null;
	/**
	 * 摇英雄模式
	 */
	private static Boolean benchEnabled = false;
	/**
	 * 同时禁英雄模式
	 */
	private static Boolean hasSimultaneousBans = false;
	/**
	 * 同时选英雄模式
	 */
	private static Boolean hasSimultaneousPicks = false;
	private static JSONObject roomFirstJson;
	/**
	 * 是否忽略队友预选
	 */
	private Boolean pickTeammateFlag = false;
	/**
	 * 是否排位秒选预选
	 */
	private Boolean championSelectFlag = false;


	public ChampSelectStrategy(LinkLeagueClientApi api, RegionSgpApi sgpApi) {
		this.api = api;
		this.sgpApi = sgpApi;
	}

	private void init() {
		String roomGameInfoJson = null;
		try {
			roomGameInfoJson = api.getChampSelectInfo();
		} catch (IOException e) {
			log.error("获取房间信息错误", e);
			return;
		}
		JSONObject roomInfoObject = JSONObject.parseObject(roomGameInfoJson);
		roomFirstJson = roomInfoObject;
		//摇英雄模式
		benchEnabled = roomInfoObject.getBoolean("benchEnabled");
		//同时ban
		hasSimultaneousBans = roomInfoObject.getBoolean("hasSimultaneousBans");
		//同时选
		hasSimultaneousPicks = roomInfoObject.getBoolean("hasSimultaneousPicks");
		log.error(roomGameInfoJson);
		selectPickMap = MapUtil.newConcurrentHashMap(AppCache.settingPersistence.getPickMap());
		selectBanMap = MapUtil.newConcurrentHashMap(AppCache.settingPersistence.getBanMap());
	}

	private void autoBanPick() throws IOException {
		//匹配秒选英雄
		if (!benchEnabled && hasSimultaneousPicks) {
			String roomGameInfoJson;
			JSONObject roomInfoObject = null;
			//拿第一次请求的数据来抢英雄更快
			if (!GameDataCache.championFlag) {
				roomInfoObject = roomFirstJson;
			} else {
				roomGameInfoJson = api.getChampSelectInfo();
				roomInfoObject = JSONObject.parseObject(roomGameInfoJson);
			}
			JSONObject timer = roomInfoObject.getJSONObject("timer");
			String phase = timer.getString("phase");
			int localPlayerCellId = roomInfoObject.getIntValue("localPlayerCellId");
			JSONArray actions = roomInfoObject.getJSONArray("actions");
			for (int j = 0; j < actions.size(); j++) {
				JSONArray action = actions.getJSONArray(j);
				for (int i = 0; i < action.size(); i++) {
					JSONObject actionElement = action.getJSONObject(i);
					if (localPlayerCellId == actionElement.getIntValue("actorCellId") && !actionElement.getBooleanValue("completed")) {
						int actionId = actionElement.getIntValue("id");
						String type = actionElement.getString("type");
						if ("pick".equals(type) && "BAN_PICK".equals(phase)) {
							String s = "";
							ArrayList<Integer> list = selectPickMap.get("default");
							for (Integer championId : list) {
								s = api.banPick("pick", actionId, championId, true);
								if (s.isEmpty()) {
									break;
								}
							}
							log.error(s);
						}
					}
				}
			}
		}
		//摇骰子模式
		else if (benchEnabled && hasSimultaneousPicks) {
			String roomGameInfoJson = api.getChampSelectInfo();
			JSONObject roomInfoObject = JSONObject.parseObject(roomGameInfoJson);
			JSONObject timer = roomInfoObject.getJSONObject("timer");
			String phase = timer.getString("phase");
			int localPlayerCellId = roomInfoObject.getIntValue("localPlayerCellId");
			//摇骰子选英雄
			if ("FINALIZATION".equals(phase)) {
				JSONArray benchChampions = roomInfoObject.getJSONArray("benchChampions");
				for (int i = 0; i < benchChampions.size(); i++) {
					JSONObject champions = benchChampions.getJSONObject(i);
					JSONArray myTeam = roomInfoObject.getJSONArray("myTeam");
					Integer championId = champions.getIntValue("championId");
					int championIndex = indexOfChampionId(championId);
					for (int j = 0; j < myTeam.size(); j++) {
						if (myTeam.getJSONObject(j).getIntValue("cellId") == localPlayerCellId) {
							int myChampionIndex = indexOfChampionId(myTeam.getJSONObject(j).getInteger("championId"));
							if (championIndex != -1 && championIndex > myChampionIndex) {
								api.sendBenchSwap(championId);
								selectPickMap.get("bench").remove(championId);
								log.error("抢了");
							}
						}
					}
				}
			}
		}
		//排位模式
		else if (!benchEnabled && hasSimultaneousBans && !hasSimultaneousPicks) {
			String roomGameInfoJson = api.getChampSelectInfo();
			log.error(roomGameInfoJson);
			JSONObject roomInfoObject = JSONObject.parseObject(roomGameInfoJson);
			JSONObject timer = roomInfoObject.getJSONObject("timer");
			String phase = timer.getString("phase");
			int localPlayerCellId = roomInfoObject.getIntValue("localPlayerCellId");
			JSONArray actions = roomInfoObject.getJSONArray("actions");
			//获取队友数据
			JSONArray myTeam = roomInfoObject.getJSONArray("myTeam");
			String myRole = "";
			List<Integer> championPickIntents = new ArrayList<>();
			List<Integer> banChampionIds = new ArrayList<>();
			for (int i = 0; i < myTeam.size(); i++) {
				if (myTeam.getJSONObject(i).getInteger("cellId").equals(localPlayerCellId)) {
					myRole = myTeam.getJSONObject(i).getString("assignedPosition");
				} else {
					Integer championId = myTeam.getJSONObject(i).getInteger("championPickIntent");
					if (championId > 0) {
						championPickIntents.add(championId);
					}
				}
			}
			for (int j = 0; j < actions.size(); j++) {
				JSONArray action = actions.getJSONArray(j);
				for (int i = 0; i < action.size(); i++) {
					JSONObject actionElement = action.getJSONObject(i);
					if (actionElement.getBooleanValue("completed") && actionElement.getString("type").equals("ban")) {
						Integer championId = actionElement.getInteger("championId");
						if (championId > 0) {
							banChampionIds.add(championId);
						}
					}
					if (localPlayerCellId == actionElement.getIntValue("actorCellId") && !actionElement.getBooleanValue("completed")) {
						int actionId = actionElement.getIntValue("id");
						String type = actionElement.getString("type");
						//预选英雄
						if ("pick".equals(type) && "PLANNING".equals(phase) && !selectPickMap.get(myRole).isEmpty()) {
							api.banPick("pick", actionId, selectPickMap.get(myRole).get(0), true);
						}
						//选英雄
						else if ("pick".equals(type) && "BAN_PICK".equals(phase) && !selectPickMap.get(myRole).isEmpty()) {
							//去除ban过的
							for (Integer banChampionId : banChampionIds) {
								selectPickMap.get(myRole).remove(banChampionId);
							}
							if (!selectPickMap.get(myRole).isEmpty()) {
								api.banPick("pick", actionId, selectPickMap.get(myRole).get(0), championSelectFlag);
							}
						}
						//ban英雄
						else if ("ban".equals(type) && "BAN_PICK".equals(phase) && !selectBanMap.get(myRole).isEmpty()) {
							//无视队友预选
							if (pickTeammateFlag) {
								for (Integer championId : championPickIntents) {
									selectBanMap.get(myRole).remove(championId);
								}
							}
							//去除ban过的
							for (Integer banChampionId : banChampionIds) {
								selectBanMap.get(myRole).remove(banChampionId);
							}
							if (!selectBanMap.get(myRole).isEmpty()) {
								api.banPick("ban", actionId, selectBanMap.get(myRole).get(0), true);
							}

						}
					}
				}
			}
		}

		if (!GameDataCache.championFlag) {
			GameDataCache.championFlag = true;
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
				if (myTeam == null || myTeam.size() != 5) {
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
				if (myTeam == null || myTeam.size() != 5) {
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

	/**
	 * 发送黑名单消息
	 */
	private void sendMessage() throws IOException {
		if (AppCache.settingPersistence.getSendBlackPlayer() && GameDataCache.roomMessageId == null) {
			String roomGameInfo = api.getChampSelectInfo();
			JSONObject jsonObject = JSONObject.parseObject(roomGameInfo);
			JSONArray myTeam = jsonObject.getJSONArray("myTeam");
			//可能队友还没进入房间
			if (myTeam == null || myTeam.size() != 5) {
				return;
			}
			String message = api.getMessage();
			ArrayList<ChatMessage> messagesList = JSON.parseObject(message, new TypeReference<ArrayList<ChatMessage>>() {
			});
			Optional<ChatMessage> championSelect = messagesList.stream().filter(item -> item.getType().equals("championSelect")).findFirst();
			if (championSelect.isPresent()) {
				GameDataCache.roomMessageId = championSelect.get().getId();
			} else {
				log.error("未找到选择英雄房间id");
				return;
			}
			for (int i = 0; i < myTeam.size(); i++) {
				String puuid = myTeam.getJSONObject(i).getString("puuid");
				boolean isBlackListPlayer = AppCache.settingPersistence.getBlacklistPlayers().containsKey(puuid);
				if (isBlackListPlayer) {
					String gameIds = AppCache.settingPersistence.getBlacklistPlayers().get(puuid);
					SummonerAlias alias = sgpApi.getSummerNameByPuuids(puuid);
					StringBuilder sb = new StringBuilder();
					sb.append(alias.getGameName()).append("为拉黑人员");
					//是否有备注
					StringBuilder remark = new StringBuilder();
					List<String> split = Arrays.stream(gameIds.split(",")).sorted().limit(FrameSetting.PAGE_SIZE - 1).toList();
					for (String gameId : split) {
						remark.append(BlackListUtil.getPlayerRemark(gameId, puuid));
					}
					if (StrUtil.isNotBlank(remark)) {
						sb.append("理由为").append(remark);
					}
					api.sendMessage(GameDataCache.roomMessageId, sb.toString());
				}
			}
		}
	}

	protected int indexOfChampionId(Integer championId) {
		return selectPickMap.get("bench").indexOf(championId);
	}

	@Override
	public void doThis() {
		if (!GameDataCache.championFlag){
			init();
		}
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
			log.error("发送战绩错误", e);
		}

		try {
			sendMessage();
		} catch (Exception e) {
			log.error("发送黑名单错误", e);
		}
	}
}
