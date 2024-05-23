package yalong.site.services.lcu;

import lombok.extern.slf4j.Slf4j;
import yalong.site.bo.ScoreBO;
import yalong.site.bo.SummonerInfoBO;
import yalong.site.bo.SummonerScoreBO;
import yalong.site.cache.GameDataCache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author yalong
 */
@Slf4j
public class CalculateScore {
	private final LinkLeagueClientApi api;

	public CalculateScore(LinkLeagueClientApi api) {
		this.api = api;
	}

	public ArrayList<SummonerScoreBO> getScoreByPuuidList(List<String> puuidList, int gameNum) throws IOException {
		ArrayList<SummonerScoreBO> arrayList = new ArrayList<>();
		//根据puuid查最近几次的战绩
		for (String puuid : puuidList) {
			//查看玩家名称
			SummonerInfoBO summonerInfo = api.getInfoByPuuId(puuid);
			//查询战绩
			List<ScoreBO> scoreBOList = api.getScoreById(puuid, gameNum - 1);
			SummonerScoreBO summonerScoreBO = new SummonerScoreBO(summonerInfo, scoreBOList);
			arrayList.add(summonerScoreBO);
		}
		return arrayList;
	}

	/**
	 * 根据战绩计算出得分
	 */
	public TreeMap<Float, SummonerScoreBO> calcScore2treeMap(ArrayList<SummonerScoreBO> list) {
		TreeMap<Float, SummonerScoreBO> treeMap = new TreeMap<>();
		for (SummonerScoreBO summonerScoreBO : list) {
			// 计算得分 最近三把(KDA+输赢)的平均值
			// KDA->(击杀*1.2+助攻*0.8)/(死亡*1.2)
			// 输赢->赢+1 输-1
			List<ScoreBO> scoreBOList = summonerScoreBO.getScoreBOList();
			float score = 0.0f;
			for (ScoreBO scoreBO : scoreBOList) {
				if (scoreBO.getWin()) {
					score += 1f;
				} else {
					score -= 1f;
				}
				Integer kills = scoreBO.getKills();
				Integer deaths = scoreBO.getDeaths();
				Integer assists = scoreBO.getAssists();
				score += ((kills * 1.2f + assists * 0.8f) / Math.max(deaths * 1.2f, 1f));
			}
			treeMap.put(score, summonerScoreBO);
		}
		return treeMap;
	}

	public ArrayList<String> entry2String(Map.Entry<Float, SummonerScoreBO> entry, String type) {
		SummonerScoreBO stupid = entry.getValue();
		//整理成字符串
		ArrayList<String> result = new ArrayList<>();
		StringBuilder stringBuilder = new StringBuilder();
		List<ScoreBO> stupidScoreBOList = stupid.getScoreBOList();
		int gameNum = stupidScoreBOList.size();
		stringBuilder.append(type);
		stringBuilder.append("是:【");
		stringBuilder.append(stupid.getSummonerInfo().getDisplayName());
		stringBuilder.append(String.format("】,KDA: 【%.2f】 ", entry.getKey() / gameNum));
		stringBuilder.append("近");
		stringBuilder.append(gameNum);
		stringBuilder.append("场战绩为：");
		for (ScoreBO scoreBO : stupidScoreBOList) {
			stringBuilder.append(scoreBO.getKills());
			stringBuilder.append("-");
			stringBuilder.append(scoreBO.getDeaths());
			stringBuilder.append("-");
			stringBuilder.append(scoreBO.getAssists());
			stringBuilder.append("(");
			stringBuilder.append(scoreBO.getWin() ? "赢" : "输");
			stringBuilder.append(") ");
		}
		result.add(stringBuilder.toString());
		return result;
	}

	/**
	 * 查询战绩,格式化为要发送的消息
	 */
	public ArrayList<String> dealScore2Msg(List<String> puuidList) {
		ArrayList<String> result = new ArrayList<>();
		if (puuidList.isEmpty()) {
			return result;
		}
		int gameNum = 3;
		ArrayList<SummonerScoreBO> scoreByPuuidList = new ArrayList<>();
		try {
			scoreByPuuidList = getScoreByPuuidList(puuidList, gameNum);
		} catch (Exception e) {
			log.error("查询战绩错误", e);
		}
		if (scoreByPuuidList.isEmpty()) {
			return result;
		}
		TreeMap<Float, SummonerScoreBO> treeMap = calcScore2treeMap(scoreByPuuidList);
		Map.Entry<Float, SummonerScoreBO> firstEntry = treeMap.firstEntry();
		Map.Entry<Float, SummonerScoreBO> lastEntry = treeMap.lastEntry();
		//排除自己
		SummonerScoreBO stupid = firstEntry.getValue();
		if (stupid.getSummonerInfo().getPuuid().equals(GameDataCache.me.getPuuid())) {
			treeMap.remove(firstEntry.getKey());
			firstEntry = treeMap.firstEntry();
		}

		ArrayList<String> stupidList = entry2String(firstEntry, "傻鸟");
		ArrayList<String> smartList = entry2String(lastEntry, "大神");
		result.addAll(stupidList);
		result.addAll(smartList);
		return result;
	}

}
