package yalong.site.cache;

import lombok.extern.slf4j.Slf4j;
import yalong.site.bo.*;

import java.util.ArrayList;

/**
 * 每一局游戏的数据缓存
 *
 * @author yalong
 */
@Slf4j
public class GameDataCache {

	public static SummonerInfoBO me;
	public static ArrayList<String> otherTeamScore = new ArrayList<>();
	public static ArrayList<String> myTeamScore = new ArrayList<>();
	public static ArrayList<ChampionBO> allChampion = new ArrayList<>();
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

	public static void reset() {
		resetScore();
	}

	public static void resetScore() {
		otherTeamScore = new ArrayList<>();
		myTeamScore = new ArrayList<>();
	}

	public static void cacheLcuMe() {
		// 缓存登录人的信息
		if (GameDataCache.me == null && AppCache.api != null) {
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
				log.error("获取资源文件成功");
			} catch (Exception err) {
				log.error("获取资源文件失败", err);
			}
		}

	}

	public static void cacheLcuAll() {
		cacheLcuMe();
		cacheLcuChampion();
		cacheLcuStatic();
	}
}
