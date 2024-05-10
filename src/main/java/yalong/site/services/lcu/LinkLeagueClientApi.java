package yalong.site.services.lcu;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import yalong.site.bo.*;
import yalong.site.cache.AppCache;
import yalong.site.enums.GameStatusEnum;
import yalong.site.http.RequestLcuUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 连接lol客户端服务
 *
 * @author yaLong
 */
public class LinkLeagueClientApi {
	private final RequestLcuUtil requestLcuUtil;

	public LinkLeagueClientApi(RequestLcuUtil requestLcuUtil) {
		this.requestLcuUtil = requestLcuUtil;
		//缓存本实例
		AppCache.api = this;
	}

	/**
	 * 获取登录用户信息
	 */
	public SummonerInfoBO getCurrentSummoner() throws IOException {
		String resp = requestLcuUtil.doGet("/lol-summoner/v1/current-summoner");
		return JSON.parseObject(resp, SummonerInfoBO.class);
	}

	/**
	 * 设置rank段位,默认设置为单排最强王者1
	 * <p>
	 * 段位:
	 * value="IRON">坚韧黑铁
	 * value="BRONZE">英勇黄铜
	 * value="SILVER">不屈白银
	 * value="GOLD">荣耀黄金
	 * value="PLATINUM">华贵铂金
	 * value="DIAMOND">璀璨钻石
	 * value="MASTER">超凡大师
	 * value="GRANDMASTER">傲世宗师
	 * value="CHALLENGER">最强王者
	 * value="UNRANKED">没有段位
	 * 段位级别
	 * value="IV"
	 * value="III"
	 * value="II"
	 * value="I"
	 * rank模式
	 * value="RANKED_SOLO_5x5">单排/双排
	 * value="RANKED_FLEX_SR">灵活组排 5v5
	 * value="RANKED_FLEX_TT">灵活组排 3v3
	 * value="RANKED_TFT">云顶之弈
	 */
	public String setRank(RankBO bo) throws IOException {
		JSONObject body = new JSONObject(1);
		JSONObject jsonObject = new JSONObject(3);
		jsonObject.put("rankedLeagueQueue", bo.getFirstRank());
		jsonObject.put("rankedLeagueTier", bo.getSecondRank());
		jsonObject.put("rankedLeagueDivision", bo.getThirdRank());
		body.put("lol", jsonObject);
		return requestLcuUtil.doPut("/lol-chat/v1/me", body.toJSONString());
	}

	/**
	 * 生涯设置背景皮肤
	 *
	 * @param skinId 皮肤id,长度5位
	 *               比如:其中 13006，这个ID分为两部分 13 和 006,
	 *               13是英雄id,6是皮肤id(不足3位,前面补0)
	 */
	public void setBackgroundSkin(int skinId) throws IOException {
		JSONObject body = new JSONObject(1);
		body.put("key", "backgroundSkinId");
		body.put("value", skinId);
		requestLcuUtil.doPost("/lol-summoner/v1/current-summoner/summoner-profile", body.toJSONString());
	}

	/**
	 * 获取某个英雄的所有皮肤和炫彩皮肤的id名字
	 *
	 * @param championId 英雄id
	 */
	public List<SkinBO> getChromasSkinByChampionId(int championId) throws IOException {
		String resp = requestLcuUtil.doGet("/lol-game-data/assets/v1/champions/" + championId + ".json");
		ArrayList<SkinBO> arrayList = new ArrayList<>();
		JSONArray skins = JSON.parseObject(resp).getJSONArray("skins");
		for (int i = 0; i < skins.size(); i++) {
			JSONObject jsonObject = skins.getJSONObject(i);
			Integer id = jsonObject.getInteger("id");
			String name = jsonObject.getString("name");
			arrayList.add(new SkinBO(id, name));
			JSONArray chromas = jsonObject.getJSONArray("chromas");
			if (chromas == null) {
				continue;
			}
			for (int j = 0; j < chromas.size(); j++) {
				Integer chromasId = chromas.getJSONObject(j).getInteger("id");
				String chromasName = chromas.getJSONObject(j).getString("name");
				arrayList.add(new SkinBO(chromasId, chromasName));
			}
		}
		return arrayList.stream().distinct().collect(Collectors.toList());
	}

	/**
	 * 获取某个英雄的所有皮肤id名字
	 *
	 * @param championId 英雄id
	 */
	public List<SkinBO> getSkinByChampionId(int championId) throws IOException {
		String resp = requestLcuUtil.doGet("/lol-game-data/assets/v1/champions/" + championId + ".json");
		ArrayList<SkinBO> arrayList = new ArrayList<>();
		JSONArray skins = JSON.parseObject(resp).getJSONArray("skins");
		for (int i = 0; i < skins.size(); i++) {
			JSONObject jsonObject = skins.getJSONObject(i);
			Integer id = jsonObject.getInteger("id");
			String name = jsonObject.getString("name");
			arrayList.add(new SkinBO(id, name));
		}
		return arrayList.stream().distinct().collect(Collectors.toList());
	}

	/**
	 * 查询当前选定的英雄所有可用的炫彩皮肤
	 */
	public List<SkinBO> getCurrentChampionSkins() throws IOException {
		String resp = requestLcuUtil.doGet("/lol-champ-select/v1/skin-carousel-skins");
		JSONArray jsonArray = JSON.parseArray(resp);
		if(jsonArray == null || jsonArray.isEmpty()) {
			return new ArrayList<>();
		}
		Integer championId = jsonArray.getJSONObject(0).getInteger("championId");
		//查询此英雄的皮肤名字
		List<SkinBO> skinBOList = getChromasSkinByChampionId(championId);
		Map<Integer, SkinBO> map = skinBOList.stream().collect(Collectors.toMap(SkinBO::getId, i->i));

		List<SkinBO> childSkinList = new ArrayList<>();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			JSONArray childSkins = jsonObject.getJSONArray("childSkins");
			for (int j = 0; j < childSkins.size(); j++) {
				JSONObject childSkinsJsonObject = childSkins.getJSONObject(j);
				if (childSkinsJsonObject.getBooleanValue("unlocked")) {
					int skinId = childSkinsJsonObject.getIntValue("id");
					childSkinList.add(map.get(skinId));
				}
			}
		}
		return childSkinList;
	}

	public String setCurrentChampionSkins(int skinId) throws IOException {
		JSONObject body = new JSONObject(3);
		body.put("selectedSkinId", skinId);
		return requestLcuUtil.doPatch("/lol-champ-select/v1/session/my-selection", body.toString());
	}

	/**
	 * 获取所有英雄
	 */
	public ArrayList<ChampionBO> getAllChampion() throws IOException {
		String s = requestLcuUtil.doGet("/lol-game-data/assets/v1/champion-summary.json");
		return JSON.parseObject(s, new TypeReference<ArrayList<ChampionBO>>() {
		});
	}

	/**
	 * 更改游戏状态
	 * "chat"->在线
	 * "away"->离开
	 * "dnd"->游戏中
	 * "offline"->离线
	 * "mobile"->手机在线
	 *
	 * @param status 状态值
	 */
	public void changeStatus(String status) throws IOException {
		JSONObject body = new JSONObject(1);
		body.put("availability", status);
		requestLcuUtil.doPut("/lol-chat/v1/me", body.toJSONString());
	}

	/**
	 * 英雄选择界面信息
	 */
	public String getChampSelectInfo() throws IOException {
		return requestLcuUtil.doGet("/lol-champ-select/v1/session");
	}

	/**
	 * ban pick
	 */
	public String banPick(String type, int actionId, int championId) throws IOException {
		JSONObject body = new JSONObject(3);
		body.put("completed", true);
		body.put("type", type);
		body.put("championId", championId);
		return requestLcuUtil.doPatch("/lol-champ-select/v1/session/actions/" + actionId, body.toJSONString());
	}

	/**
	 * 通过玩家游戏名,查他的信息
	 *
	 * @param name 玩家游戏名
	 */
	public SummonerInfoBO getInfoByName(String name) throws IOException {
		String resp = requestLcuUtil.doGet("/lol-summoner/v1/summoners?name=" + name);
		return JSON.parseObject(resp, SummonerInfoBO.class);
	}

	/**
	 * 通过玩家id查玩家信息
	 *
	 * @param summonerId 玩家游戏id
	 */
	public SummonerInfoBO getInfoBySummonerId(String summonerId) throws IOException {
		String resp = requestLcuUtil.doGet("/lol-summoner/v1/summoners/" + summonerId);
		return JSON.parseObject(resp, SummonerInfoBO.class);
	}

	/**
	 * 通过puuid查玩家信息
	 *
	 * @param puuId 玩家puuid
	 */
	public SummonerInfoBO getInfoByPuuId(String puuId) throws IOException {
		String resp = requestLcuUtil.doGet("/lol-summoner/v2/summoners/puuid/" + puuId);
		return JSON.parseObject(resp, SummonerInfoBO.class);
	}

	/**
	 * 段位查询
	 */
	public String getRank(String puuid) throws IOException {
		return requestLcuUtil.doGet("/lol-ranked/v1/ranked-stats/" + puuid);
	}

	/**
	 * 通过玩家puuid查询近几把战绩
	 *
	 * @param id       玩家信息中的puuid
	 * @param endIndex 局数
	 */
	public List<ScoreBO> getScoreById(String id, int endIndex) throws IOException {
		String endpoint = "/lol-match-history/v1/products/lol/" + id + "/matches?begIndex=0&endIndex=" + endIndex;
		String resp = requestLcuUtil.doGet(endpoint);
		JSONObject jsonObject = JSON.parseObject(resp);
		return jsonObject.getJSONObject("games").getJSONArray("games")
				.toJavaList(JSONObject.class)
				.stream().map(i -> {
					JSONObject participants = i.getJSONArray("participants").getJSONObject(0);
					JSONObject stats = participants.getJSONObject("stats");
					return stats.toJavaObject(ScoreBO.class);
				}).collect(Collectors.toList());
	}

	/**
	 * 查看游戏当前状态
	 * 游戏大厅:None
	 * 房间内:Lobby
	 * 匹配中:Matchmaking
	 * 找到对局:ReadyCheck
	 * 选英雄中:ChampSelect
	 * 游戏中:InProgress
	 * 游戏即将结束:PreEndOfGame
	 * 等待结算页面:WaitingForStats
	 * 游戏结束:EndOfGame
	 * 等待重新连接:Reconnect
	 */
	public GameStatusEnum getGameStatus() throws IOException {
		String s = requestLcuUtil.doGet("/lol-gameflow/v1/gameflow-phase");
		String s1 = JSON.parseObject(s, String.class);
		return GameStatusEnum.valueOf(s1);
	}

	/**
	 * 接受对局
	 */
	public String accept() throws IOException {
		return requestLcuUtil.doPost("/lol-matchmaking/v1/ready-check/accept", "");
	}

	/**
	 * 重连游戏
	 */
	public String reconnect() throws IOException {
		return requestLcuUtil.doPost("/lol-gameflow/v1/reconnect", "");
	}

	/**
	 * 再来一局
	 */
	public String playAgain() throws IOException {
		return requestLcuUtil.doPost("/lol-lobby/v2/play-again", "");
	}

	/**
	 * 获取当前游戏两队人的puuid
	 */
	public TeamPuuidBO getTeamPuuid() throws IOException {
		TeamPuuidBO result = new TeamPuuidBO();
		String resp = requestLcuUtil.doGet("/lol-gameflow/v1/session");
		JSONObject jsonObject = JSON.parseObject(resp).getJSONObject("gameData");
		List<String> teamOne = jsonObject.getJSONArray("teamOne")
				.toJavaList(JSONObject.class)
				.stream()
				.map(i -> i.getString("puuid"))
				.collect(Collectors.toList());

		List<String> teamTwo = jsonObject.getJSONArray("teamTwo")
				.toJavaList(JSONObject.class)
				.stream()
				.map(i -> i.getString("puuid"))
				.collect(Collectors.toList());
		result.setTeamPuuid1(teamOne);
		result.setTeamPuuid2(teamTwo);
		return result;
	}
}
