package yalong.site.services.lcu;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import okhttp3.Request;
import yalong.site.bo.*;
import yalong.site.cache.AppCache;
import yalong.site.enums.GameStatusEnum;
import yalong.site.http.RequestLcuUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.*;
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
	 * 获取当前这局游戏数据
	 */
	public String getOnlineData() throws IOException {
		Request request = new Request.Builder()
				.url("https://127.0.0.1:2999/liveclientdata/allgamedata")
				.get()
				.build();
		return requestLcuUtil.callString(request);
	}

	/**
	 * 获取登录用户信息
	 */
	public Player getCurrentSummoner() throws IOException {
		String resp = requestLcuUtil.doGet("/lol-summoner/v1/current-summoner");
		return JSON.parseObject(resp, Player.class);
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
	 * value="EMERALD">流光翡翠
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
	 * @param body 皮肤数据
	 */
	public void setBackgroundSkin(String body) throws IOException {
//		JSONObject body = new JSONObject(2);
//		body.put("key", "backgroundSkinId");
//		body.put("value", skinId)
// 		增强皮肤(带签名),参数用这个,分两次调用
//		body.put("key", "backgroundSkinAugments");
		requestLcuUtil.doPost("/lol-summoner/v1/current-summoner/summoner-profile", body);
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
	 * 查询当前选定的英雄所有可用的炫彩皮肤
	 */
	public List<SkinBO> getCurrentChampionSkins() throws IOException {
		String resp = requestLcuUtil.doGet("/lol-champ-select/v1/skin-carousel-skins");
		JSONArray jsonArray = JSON.parseArray(resp);
		if (jsonArray == null || jsonArray.isEmpty()) {
			return new ArrayList<>();
		}
		Integer championId = jsonArray.getJSONObject(0).getInteger("championId");
		//查询此英雄的皮肤名字
		List<SkinBO> skinBOList = getChromasSkinByChampionId(championId);
		Map<Integer, SkinBO> map = skinBOList.stream().collect(Collectors.toMap(SkinBO::getId, i -> i));

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
			//有签名的皮肤
			JSONObject questSkinInfo = jsonObject.getJSONObject("questSkinInfo");
			if(questSkinInfo!=null){
				JSONArray tiers = questSkinInfo.getJSONArray("tiers");
				if(tiers!=null){
					for (int k = 0; k < tiers.size(); k++) {
						JSONObject jsonObject_ = tiers.getJSONObject(k);
						Integer id_ = jsonObject_.getInteger("id");
						String name_ = jsonObject_.getString("name");
						//加强版皮肤
						JSONObject skinAugments = jsonObject_.getJSONObject("skinAugments");
						if(skinAugments!=null){
							JSONArray augments = skinAugments.getJSONArray("augments");
							if (augments!=null){
								for (int l = 0; l < augments.size(); l++) {
                                    String contentId = augments.getJSONObject(l).getString("contentId");
                                    arrayList.add(new SkinBO(id_, name_,contentId));
                                }
							}
						}else {
                            arrayList.add(new SkinBO(id_, name_));
                        }

					}
				}
			}
		}
		return arrayList.stream().distinct().collect(Collectors.toList());
	}

	/**
	 * 设置炫彩皮肤
	 */
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
		String s = requestLcuUtil.doGet("/lol-champ-select/v1/session");
		return s;
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
	 * 通过玩家id查玩家信息
	 *
	 * @param summonerId 玩家游戏id
	 */
	public Player getInfoBySummonerId(String summonerId) throws IOException {
		String resp = requestLcuUtil.doGet("/lol-summoner/v1/summoners/" + summonerId);
		return JSON.parseObject(resp, Player.class);
	}

	/**
	 * 通过puuid查玩家信息
	 *
	 * @param puuId 玩家puuid
	 */
	public Player getInfoByPuuId(String puuId) throws IOException {
		String resp = requestLcuUtil.doGet("/lol-summoner/v2/summoners/puuid/" + puuId);
		return JSON.parseObject(resp, Player.class);
	}

	/**
	 * 段位查询
	 */
	public Rank getRank(String puuid) throws IOException {
		String resp = requestLcuUtil.doGet("/lol-ranked/v1/ranked-stats/" + puuid);
		return JSON.parseObject(resp, Rank.class);
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
	 * 获取游戏状态
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
	 * 寻找对局
	 */
	public String search() throws IOException {
		return requestLcuUtil.doPost("/lol-lobby/v2/lobby/matchmaking/search", "");
	}

	/**
	 * 游戏结束后点赞队友
	 */
	public String honor() throws IOException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("honorCategory", "");
		return requestLcuUtil.doPost("/lol-honor-v2/v1/honor-player", jsonObject.toJSONString());
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

	/**
	 * 通过玩家游戏名,查询多个玩家的信息
	 *
	 * @param nameList 玩家们的游戏名
	 */
	public List<Player> getV2InfoByNameList(List<String> nameList) throws IOException {
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(nameList);
		String resp = requestLcuUtil.doPost("/lol-summoner/v2/summoners/names", jsonArray.toString());
		return JSON.parseArray(resp, Player.class);
	}

	/**
	 * 通过玩家puuid查询近几把战绩
	 *
	 * @param id       玩家信息中的puuid
	 * @param begIndex 起始局数
	 * @param endIndex 结尾局数
	 */
	public ProductsMatchHistoryBO getProductsMatchHistoryByPuuid(String id, int begIndex, int endIndex) throws IOException {
		String endpoint = "/lol-match-history/v1/products/lol/" + id + "/matches?begIndex=" + begIndex + "&endIndex=" + endIndex;
		String resp = requestLcuUtil.doGet(endpoint);
		return JSONObject.parseObject(resp, ProductsMatchHistoryBO.class);
	}

	/**
	 * 通过游戏gameId查询详细战绩
	 *
	 * @param gameId 战绩的游戏ID
	 */
	public GameMatchHistoryBO getGameMatchHistoryByGameId(Long gameId) throws IOException {
		String endpoint = "/lol-match-history/v1/games/" + gameId;
		String resp = requestLcuUtil.doGet(endpoint);
		JSONObject jsonObject = JSON.parseObject(resp);
		return jsonObject.toJavaObject(GameMatchHistoryBO.class);
	}

	/**
	 * 通过英雄ID查询英雄头像
	 *
	 * @param championId 英雄ID
	 */
	public Image getChampionIcons(Integer championId) throws IOException {
		String endpoint = "/lol-game-data/assets/v1/champion-icons/" + championId + ".png ";
		String url;
		url = endpoint.substring(1);
		boolean exist = FileUtil.exist(new File(url));
		if (exist) {
			return ImageIO.read(FileUtil.getInputStream(new File(url)));
		} else {
			File file = FileUtil.writeBytes(requestLcuUtil.download(endpoint), new File(url));
			return ImageIO.read(FileUtil.getInputStream(file));
		}
	}

	/**
	 * 获取召唤师技能对应JSON文件
	 */
	public ArrayList<SummonerSpellsBO> getAllSummonerSpells() throws IOException {
		String endpoint = "/lol-game-data/assets/v1/summoner-spells.json";
		String resp = requestLcuUtil.doGet(endpoint);
		return JSON.parseObject(resp, new TypeReference<ArrayList<SummonerSpellsBO>>() {
		});

	}

	/**
	 * 通过传入地址获取图标
	 *
	 * @param iconsPath 图标或图像地址
	 */
	public Image geImageByPath(String iconsPath) throws IOException {
		String url;
		if (iconsPath.charAt(0) == '/') {
			url = iconsPath.substring(1);
		} else {
			url = iconsPath.replace("/", "\\");
		}
		boolean exist = FileUtil.exist(new File(url));
		if (exist) {
			return ImageIO.read(new FileInputStream(url));
		} else {
			File file = FileUtil.writeBytes(requestLcuUtil.download(iconsPath), new File(url));
			return ImageIO.read(FileUtil.getInputStream(file));
		}

	}

	/**
	 * 获取召唤师天赋对应JSON文件
	 */
	public ArrayList<PerkBO> getAllPerk() throws IOException {
		String endpoint = "/lol-game-data/assets/v1/perks.json";
		String resp = requestLcuUtil.doGet(endpoint);
		return JSON.parseObject(resp, new TypeReference<ArrayList<PerkBO>>() {
		});
	}

	/**
	 * 获取装备信息
	 */
	public ArrayList<LOLItemBO> getAllItems() throws IOException {
		String endpoint = "/lol-game-data/assets/v1/items.json";
		String resp = requestLcuUtil.doGet(endpoint);
		return JSON.parseObject(resp, new TypeReference<ArrayList<LOLItemBO>>() {
		});
	}

	/**
	 * 获取召唤师天赋对应JSON文件
	 */
	public ArrayList<PerkStyleBO> getAllPerkStyleBO() throws IOException {
		String endpoint = "/lol-game-data/assets/v1/perkstyles.json";
		String resp = requestLcuUtil.doGet(endpoint);
		JSONObject jsonObject = JSON.parseObject(resp);
		return JSON.parseObject(jsonObject.get("styles").toString(), new TypeReference<ArrayList<PerkStyleBO>>() {
		});
	}

	/**
	 * 获取玩家头像
	 *
	 * @param profileIconId 头像ID
	 */
	public Image getProfileIcon(Integer profileIconId) throws IOException {
		String endpoint = "/lol-game-data/assets/v1/profile-icons/" + profileIconId + ".jpg ";
		String url;
		url = endpoint.substring(1);
		boolean exist = FileUtil.exist(new File(url));
		if (exist) {
			return ImageIO.read(FileUtil.getInputStream(new File(url)));
		} else {
			File file = FileUtil.writeBytes(requestLcuUtil.download(endpoint), new File(url));
			return ImageIO.read(FileUtil.getInputStream(file));
		}
	}

	/**
	 * 获取SGP接口accessToken
	 */
	public String getSgpAccessToken() throws IOException {
		String endpoint = "/entitlements/v1/token";
		String resp = requestLcuUtil.doGet(endpoint);
		return JSON.parseObject(resp).get("accessToken").toString();

	}

	/**
	 * 获取全部模式地图信息
	 */
	public Map<Integer, GameQueue> getAllQueue() throws IOException {
		String endpoint = "/lol-game-queues/v1/queues";
		String resp = requestLcuUtil.doGet(endpoint);
		ArrayList<GameQueue> gameQueues = JSON.parseObject(resp, new TypeReference<ArrayList<GameQueue>>() {
		});
		Map<Integer, GameQueue> data = new HashMap<>();
		for (GameQueue gameQueue : gameQueues) {
			if (!gameQueue.getGameMode().equals("TFT")) {
				data.put(gameQueue.getId(), gameQueue);
			}
		}
		//添加自定义
		GameQueue gameQueue = new GameQueue();
		gameQueue.setId(0);
		gameQueue.setName("自定义");
		gameQueue.setGameMode("CUSTOM");
		gameQueue.setIsVisible("true");
		data.put(0, gameQueue);
		LinkedHashMap<Integer, GameQueue> sortedMap = data.entrySet().stream()
				.sorted(Map.Entry.comparingByKey())
				.collect(
						Collectors.toMap(
								Map.Entry<Integer, GameQueue>::getKey,
								Map.Entry<Integer, GameQueue>::getValue,
								(oldVal, newVal) -> oldVal,
								LinkedHashMap<Integer, GameQueue>::new
						)
				);
		return sortedMap;
	}

	/**
	 * 获取游戏模式加描述资源
	 */
	public String getMaps() throws IOException {
		String endpoint = "/lol-maps/v1/maps";
		return requestLcuUtil.doGet(endpoint);
	}
}
