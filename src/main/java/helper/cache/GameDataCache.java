package helper.cache;

import helper.bo.*;
import helper.utils.ProcessUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 每一局游戏的数据缓存
 *
 * @author @_@
 */
@Slf4j
public class GameDataCache {

	public static MePlayer me;
	/**
	 * 敌人战绩
	 */
	public static ArrayList<String> enemyTeamScore = new ArrayList<>();
	/**
	 * 我方评分
	 */
	public static ArrayList<String> myTeamScore = new ArrayList<>();
	/**
	 * 所有英雄
	 */
	public static ArrayList<ChampionBO> allChampion = new ArrayList<>();
	/**
	 * 所有英雄的绰号
	 */
	public static List<TencentChampion> allChampionName = new ArrayList<>();
	/**
	 * 召唤师技能信息
	 */
	public static ArrayList<SummonerSpellsBO> summonerSpellsList = new ArrayList<>();
	/**
	 * 召唤师技能信息
	 */
	public static ArrayList<LOLItemBO> itemList = new ArrayList<>();
	/**
	 * 召唤师技能信息
	 */
	public static ArrayList<PerkBO> perkList = new ArrayList<>();
	/**
	 * 基石符文信息
	 */
	public static ArrayList<PerkStyleBO> perkStyleList = new ArrayList<>();
	/**
	 * 游戏地图模式信息
	 */
	public static Map<Integer, GameQueue> allGameQueuesList = new LinkedHashMap<>();
	/**
	 * 队友战绩缓存
	 */
	public static List<TeamSummonerBO> myTeamMatchHistory = new ArrayList<>();
	/**
	 * 敌人战绩缓存
	 */
	public static List<TeamSummonerBO> enemyTeamMatchHistory = new ArrayList<>();

	public static Map<Integer, GameQueue> selectGameQueueList = new LinkedHashMap<>();

	public static Integer queueId = null;

	public static LeagueClientBO leagueClient;

	public static void reset() {
		resetScore();
		resetHistory();
	}

	public static void resetScore() {
		enemyTeamScore = new ArrayList<>();
		myTeamScore = new ArrayList<>();
	}

	public static void resetHistory() {
		myTeamMatchHistory = new ArrayList<>();
		enemyTeamMatchHistory = new ArrayList<>();
	}

	public static void cacheLcuMe() {
		// 缓存登录人的信息
		if (AppCache.api != null) {
			try {
				GameDataCache.me = AppCache.api.getCurrentSummoner();
			} catch (Exception e) {
				log.error("获取登录人信息错误");
			}
		}
	}

	public static void cacheLcuChampion() {
		if (allChampion.isEmpty() && AppCache.api != null) {
			//缓存所有英雄
			try {
				allChampion = AppCache.api.getAllChampion();
				allChampionName = AppCache.api.getChampionNameList();
			} catch (Exception e) {
				log.error("获取所有英雄错误");
			}
		}
	}

	public static void cacheLcuStatic() {
		if (perkList.isEmpty() && AppCache.api != null) {
			try {
				perkList = AppCache.api.getAllPerk();
				itemList = AppCache.api.getAllItems();
				perkStyleList = AppCache.api.getAllPerkStyleBO();
				summonerSpellsList = AppCache.api.getAllSummonerSpells();
				allGameQueuesList = AppCache.api.getAllQueue();
				log.info("获取资源文件成功");
			} catch (Exception err) {
				log.error("获取资源文件失败", err);
			}
		}
	}

	public static void cacheSelectGameMode() {
		GameQueue allQueue = new GameQueue();
		allQueue.setId(-1);
		allQueue.setName("全部模式");
		selectGameQueueList.put(-1, allQueue);
		for (Integer key : allGameQueuesList.keySet()) {
			GameQueue value = allGameQueuesList.get(key);
			if ("true".equals(value.getIsVisible())) {
				//排除人机和自定义并且只包含开放的模式
				if (value.getId() != 0 && value.getId() != 870 && value.getId() != 880 && value.getId() != 890) {
					selectGameQueueList.put(key, value);
				}
			}
		}
	}

	public static void cacheLeagueClient() {
		try {
			leagueClient = ProcessUtil.getClientProcess();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void cacheLcuAll() {
		cacheLcuMe();
		cacheLeagueClient();
		cacheLcuChampion();
		cacheLcuStatic();
		cacheSelectGameMode();
	}
}
