package yalong.site.cache;

import yalong.site.bo.*;
import yalong.site.services.lcu.LinkLeagueClientApi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yalong
 */
public class AppCache {
	public static LinkLeagueClientApi api;
	public static ArrayList<ChampionBO> allChampion = new ArrayList<>();
	public static ArrayList<String> garbageWordList = new ArrayList<>();
	public static String lastGarbageWord;
	public static ArrayList<String> resultPanelMsgList = new ArrayList<>();
	public static Set<Integer> hotKeyCode = new HashSet<>();
	public static Set<Integer> commonKeyCode = new HashSet<>();

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

}
