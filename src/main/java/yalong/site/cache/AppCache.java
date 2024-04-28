package yalong.site.cache;

import yalong.site.bo.ChampionBO;
import yalong.site.services.lcu.LinkLeagueClientApi;

import java.util.ArrayList;

/**
 * @author yalong
 */
public class AppCache {
	public static LinkLeagueClientApi api;
	public static ArrayList<ChampionBO> allChampion = new ArrayList<>();
	public static ArrayList<String> garbageWordList = new ArrayList<>();
	public static String lastGarbageWord;
	public static ArrayList<String> resultPanelMsgList = new ArrayList<>();
}
