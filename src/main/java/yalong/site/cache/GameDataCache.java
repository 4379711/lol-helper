package yalong.site.cache;

import yalong.site.bo.SummonerInfoBO;

import java.util.ArrayList;

/**
 * 每一局游戏的数据缓存
 *
 * @author yalong
 */
public class GameDataCache {
	public static SummonerInfoBO me;
	public static ArrayList<String> otherTeamScore = new ArrayList<>();
	public static ArrayList<String> myTeamScore = new ArrayList<>();

	public static void reset() {
		otherTeamScore = new ArrayList<>();
		myTeamScore = new ArrayList<>();
	}

}
