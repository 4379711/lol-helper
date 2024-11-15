package helper.frame.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 游戏常量映射
 */

public class GameConstant {
	public final static int TEAM_ONE = 100;
	public final static int TEAM_TWO = 200;
	public final static Map<Integer, String> GAME_TYPE = new HashMap<Integer, String>() {
		{
			put(0, "自定义练习");
			put(420, "单双排位");
			put(440, "灵活排位");
			put(450, "极地大乱斗");
			put(870, "新·入门人机");
			put(880, "新·新手人机");
			put(890, "新·一般人机");
			put(900, "无限火力");
			put(1700, "斗魂竞技场");
		}
	};
	public final static Map<String, String> RANK = new HashMap<String, String>() {
		{
			put("NONE", "暂无段位");
			put("IRON", "坚韧黑铁");
			put("BRONZE", "不屈白银");
			put("GOLD", "荣耀黄金");
			put("PLATINUM", "华贵铂金");
			put("EMERALD", "流光翡翠");
			put("DIAMOND", "璀璨钻石");
			put("MASTER", "超凡大师");
			put("GRANDMASTER", "傲世宗师");
			put("CHALLENGER", "最强王者");
		}
	};
	public final static List<String> REGION = new ArrayList<String>() {
		{
			add("tj100");
			add("hn1");
			add("cq100");
			add("gz100");
			add("nj100");
			add("hn10");
			add("tj101");
			add("bgp2");
		}
	};
	public final static String BLACK_LIST_FILE = "blacklist/game/";
}
