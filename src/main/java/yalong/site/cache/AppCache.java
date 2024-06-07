package yalong.site.cache;

import yalong.site.services.lcu.LinkLeagueClientApi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yalong
 */
public class AppCache {
	public static LinkLeagueClientApi api;
	public static ArrayList<String> garbageWordList = new ArrayList<>();
	public static String lastGarbageWord;
	public static ArrayList<String> resultPanelMsgList = new ArrayList<>();
	public static Set<Integer> hotKeyCode = new HashSet<>();
	public static Set<Integer> commonKeyCode = new HashSet<>();
	public static boolean stopAuto = false;

}
