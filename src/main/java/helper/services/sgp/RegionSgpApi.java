package helper.services.sgp;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import helper.bo.*;
import helper.cache.AppCache;
import helper.constant.GameConstant;
import helper.http.RequestSgpUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * SGP接口API
 *
 * @author WuYi
 */
@Slf4j
public class RegionSgpApi {
	private final RequestSgpUtil requestSpgUtil;

	public RegionSgpApi(RequestSgpUtil requestSpgUtil) {
		this.requestSpgUtil = requestSpgUtil;
		//缓存本实例
		AppCache.sgpApi = this;
	}

	/**
	 * 查询全大区的召唤师
	 *
	 * @param gameName 游戏名
	 * @param tagLine  尾号数字
	 */
	public SummonerAlias getSummerByName(String gameName, String tagLine) throws IOException {
		Request request = new Request.Builder()
				.url("https://prod-rso.lol.qq.com:3001/aliases/v1/aliases?gameName=" + gameName + "&tagLine=" + tagLine)
				.get()
				.build();
		String s = requestSpgUtil.callString(request);
		JSONArray objects = JSON.parseArray(s);
		if (objects.size() == 1) {
			SummonerAlias summonerAlias = new SummonerAlias();
			summonerAlias.setPuuid(objects.getJSONObject(0).getString("puuid"));
			JSONObject alias = JSON.parseObject(objects.getJSONObject(0).getString("alias"));
			summonerAlias.setGameName(alias.get("game_name").toString());
			summonerAlias.setTagLine(alias.get("tag_line").toString());
			return summonerAlias;
		}
		return null;
	}

	/**
	 * 获取现在的名字 lcu的官方接口和战绩接口都有可能获取到改名前的姓名
	 *
	 * @param puuids
	 */

	public List<SummonerAlias> getSummerNameByPuuids(List<String> puuids) throws IOException {
		List<SummonerAlias> list = new ArrayList<SummonerAlias>();
		JSONObject json = new JSONObject();
		json.put("puuids", puuids.toArray());
		RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json; charset=utf-8"));
		Request request = new Request.Builder()
				.url("https://prod-rso.lol.qq.com:3001/namesets/v1/namesets")
				.post(body)
				.build();
		String s = requestSpgUtil.callString(request);
		JSONArray array = JSON.parseArray(s);
		if (array != null) {
			for (int i = 0; i < array.size(); i++) {
				JSONObject req = JSON.parseObject(array.get(i).toString());
				SummonerAlias alias = new SummonerAlias();
				alias.setPuuid(req.get("puuid").toString());
				JSONObject reqAlias = req.getJSONObject("alias");
				alias.setGameName(reqAlias.get("game_name").toString());
				alias.setTagLine(reqAlias.get("tag_line").toString());
				list.add(alias);
			}
		}
		return list;
	}

	/**
	 * 获取现在的名字 lcu的官方接口和战绩接口都有可能获取到改名前的姓名
	 *
	 * @param puuid
	 */
	public SummonerAlias getSummerNameByPuuids(String puuid) throws IOException {
		JSONObject json = new JSONObject();
		json.put("puuids", new ArrayList<String>() {{
			add(puuid);
		}});
		RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json; charset=utf-8"));
		Request request = new Request.Builder()
				.url("https://prod-rso.lol.qq.com:3001/namesets/v1/namesets")
				.post(body)
				.build();
		String s = requestSpgUtil.callString(request);
		JSONArray array = JSON.parseArray(s);
		if (array != null) {
			JSONObject req = JSON.parseObject(array.get(0).toString());
			SummonerAlias alias = new SummonerAlias();
			alias.setPuuid(req.get("puuid").toString());
			JSONObject reqAlias = req.getJSONObject("alias");
			alias.setGameName(reqAlias.get("game_name").toString());
			alias.setTagLine(reqAlias.get("tag_line").toString());
			return alias;
		}
		return null;
	}

	/**
	 * 通过玩家puuid查询近几把战绩
	 *
	 * @param id         玩家信息中的puuid
	 * @param startIndex 起始局数
	 * @param count      结尾局数
	 */
	public List<SpgProductsMatchHistoryBO> getProductsMatchHistoryByPuuid(String region, String id, int startIndex, int count) throws IOException {
		String endpoint = "/match-history-query/v1/products/lol/player/" + id + "/SUMMARY?startIndex=" + startIndex + "&count=" + count;
		JSONObject jsonObject = null;
		String resp = requestSpgUtil.doGet(endpoint, region);
		if (resp.equals("[]")) {
			return null;
		} else {
			jsonObject = JSONObject.parseObject(resp);

		}
		return JSON.parseArray(jsonObject.get("games").toString(), SpgProductsMatchHistoryBO.class);
	}

	public List<SpgProductsMatchHistoryBO> getProductsMatchHistoryByPuuid(String region, String id, int startIndex, int count, String queueId) throws IOException {
		String endpoint = "/match-history-query/v1/products/lol/player/" + id + "/SUMMARY?startIndex=" + startIndex + "&count=" + count + "&tag=" + queueId;
		JSONObject jsonObject = null;
		String resp = requestSpgUtil.doGet(endpoint, region);
		if (resp.equals("[]")) {
			return null;
		} else {
			jsonObject = JSONObject.parseObject(resp);

		}
		return JSON.parseArray(jsonObject.get("games").toString(), SpgProductsMatchHistoryBO.class);
	}

	/**
	 * 获取玩家排位信息
	 *
	 * @param puuid 玩家id
	 */
	public SGPRank getRankedStatsByPuuid(String puuid) throws IOException {
		SGPRank sgpRank = new SGPRank();
		String endpoint = "/leagues-ledge/v2/rankedStats/puuid/" + puuid;
		String resp = requestSpgUtil.doGet(endpoint);
		List<SGPRank> queues = JSON.parseArray(JSONObject.parseObject(resp).get("queues").toString(), SGPRank.class);
		Optional<SGPRank> respRank = queues.stream().filter(item -> item.getQueueType().equals("RANKED_SOLO_5x5")).findFirst();
		if (respRank.isPresent()) {
			if (respRank.get().getTier() == null) {
				return null;
			} else {
				sgpRank.setTier(GameConstant.RANK.get(respRank.get().getTier()));
				sgpRank.setRank(respRank.get().getRank());
				return sgpRank;
			}
		} else {
			return null;
		}
	}

	public List<SGPRank> getRankedStatsListByPuuid(String puuid, String region) throws IOException {
		String endpoint = "/leagues-ledge/v2/rankedStats/puuid/" + puuid;
		String resp = requestSpgUtil.doGet(endpoint, region);
		if(resp.isEmpty()){
			return new ArrayList<SGPRank>();
		}
		List<SGPRank> queues = JSON.parseArray(JSONObject.parseObject(resp).get("queues").toString(), SGPRank.class);
		return queues;
	}

	/**
	 * 获取召唤师信息
	 *
	 * @param puuidList
	 */
	public List<Player> getSummerInfoByPuuid(String region, List<String> puuidList) throws IOException {
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(puuidList);
		String endpoint = "/summoner-ledge/v1/regions/" + region + "/summoners/puuids";
		String resp = requestSpgUtil.doPost(endpoint, jsonArray.toString(), region);
		if (!resp.equals("[]")) {
			return JSON.parseArray(resp, Player.class);
		}
		return null;
	}

	/**
	 * 获取召唤师信息
	 *
	 * @param puuid
	 */
	public SgpSummonerInfoBo getSummerInfoByPuuid(String region, String puuid) throws IOException {
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(new ArrayList<String>() {{
			add(puuid);
		}});
		String endpoint = "/summoner-ledge/v1/regions/" + region + "/summoners/puuids";
		String resp = requestSpgUtil.doPost(endpoint, jsonArray.toString(), region);
		if (!resp.equals("[]")) {
			return JSON.parseArray(resp, SgpSummonerInfoBo.class).get(0);
		}
		return null;
	}

	/**
	 * 获取回放文件 人多的大区获取不了
	 * @param region
	 * @param gameId
	 * @return
	 * @throws IOException
	 */
	public byte[] getReplay(String region, Long gameId) throws IOException {
		String endpoint = "/match-history-query/v3/product/lol/matchId/" + region + "_" + gameId + "/infoType/replay";
		return requestSpgUtil.doGetByte(endpoint, region);
	}

	/**
	 * 获取当前房间的模式
	 * @param region
	 * @param puuid
	 * @return
	 * @throws IOException
	 */
	public Integer getPartiesLedgeQueueId(String region, String puuid) throws IOException {
		String endpoint = "/parties-ledge/v1/players/" + puuid;
		String req = requestSpgUtil.doGet(endpoint, region);
		if (req.isEmpty()) {
			return null;
		}
		JSONObject json = JSONObject.parseObject(req);
		if (json.getJSONObject("currentParty").getJSONObject("gameMode") == null) {
			return null;
		}
		return json.getJSONObject("currentParty").getJSONObject("gameMode").getInteger("queueId");
	}
}
