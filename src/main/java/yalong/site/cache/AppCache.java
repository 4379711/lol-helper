package yalong.site.cache;

import yalong.site.bo.ChampionBO;
import yalong.site.bo.SummonerInfoBO;
import yalong.site.services.lcu.LinkLeagueClientApi;

import java.util.ArrayList;

/**
 * @author yalong
 */
public class AppCache {
	public static LinkLeagueClientApi api;
	public static ArrayList<ChampionBO> allChampion = new ArrayList<>();
	public static ArrayList<String> communicateWords = new ArrayList<>();

	public static String lastCommunicateWord;

}
