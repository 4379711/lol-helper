package helper.site.cache;

import helper.site.services.lcu.LinkLeagueClientApi;
import helper.site.services.sgp.RegionSgpApi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author @_@
 */
public class AppCache {
	public static LinkLeagueClientApi api;
	public static ArrayList<String> garbageWordList = new ArrayList<>();
	public static String lastGarbageWord;
	public static ArrayList<String> resultPanelMsgList = new ArrayList<>();
	public static Set<Integer> hotKeyCode = new HashSet<>();
	public static Set<Integer> commonKeyCode = new HashSet<>();
	//快捷键控制是否暂停自动执行的各功能
	public static boolean stopAuto = false;
	public static RegionSgpApi sgpApi;
	// 用来持久化用户的配置
	public static FrameUserSetting settingPersistence = new FrameUserSetting();
}
