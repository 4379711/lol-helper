package yalong.site.services.lcu;

import yalong.site.bo.ScoreBO;
import yalong.site.bo.SummonerInfoBO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author yalong
 */
public class CalculateScore {
	private final LinkLeagueClientApi api;

	public CalculateScore(LinkLeagueClientApi api) {
		this.api = api;
	}

	/**
	 * 查询战绩,格式化为要发送的消息
	 */
	public ArrayList<String> dealScore2Msg(List<String> puuidList) throws IOException {
		ArrayList<String> result = new ArrayList<>();
		TreeMap<Float, String> treeMap = new TreeMap<>();
		int gameNum=3;
		//根据puuid查最近几次的战绩
		for (String puuid : puuidList) {
			//查看玩家名称
			SummonerInfoBO summonerInfo = api.getInfoByPuuId(puuid);
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("【");
			stringBuilder.append(summonerInfo.getDisplayName());
			stringBuilder.append("】, 最近");
			stringBuilder.append(gameNum);
			stringBuilder.append("场战绩为：");
			//查询战绩
			List<ScoreBO> scoreBOList = api.getScoreById(puuid, gameNum-1);
			// 计算得分 最近三把(KDA+输赢)的平均值
			// KDA->(击杀*1.2+助攻*0.8)/(死亡*1.2)
			// 输赢->赢+1 输-1
			float score = 0.0f;
			for (ScoreBO scoreBO : scoreBOList) {
				if (scoreBO.getWin()) {
					score += 1;
				} else {
					score -= 1;
				}
				Integer kills = scoreBO.getKills();
				Integer deaths = scoreBO.getDeaths();
				Integer assists = scoreBO.getAssists();
				score += (kills * 1.2 + assists * 0.8) / Math.max(deaths * 1.2, 1);
				stringBuilder.append(kills);
				stringBuilder.append("-");
				stringBuilder.append(deaths);
				stringBuilder.append("-");
				stringBuilder.append(assists);
				stringBuilder.append(",  ");
			}
			score /= gameNum;
			stringBuilder.append("评分: ");
			stringBuilder.append(String.format("%.2f", score));
			treeMap.put(score, stringBuilder.toString());
		}
		Map.Entry<Float, String> firstEntry = treeMap.firstEntry();
		Map.Entry<Float, String> lastEntry = treeMap.lastEntry();
		result.add("傻鸟是:" + firstEntry.getValue());
		result.add("大神是:" + lastEntry.getValue());
		return result;
	}

}
