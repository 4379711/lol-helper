package helper.constant;

import helper.frame.bo.ItemBO;
import helper.frame.panel.base.BaseComboBox;

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

	public static BaseComboBox<ItemBO> CREATE_ROLE_SELECT() {
		BaseComboBox<ItemBO> item = new BaseComboBox<ItemBO>();
		item.addItem(new ItemBO("all", "全部定位"));
		item.addItem(new ItemBO("fighter", "战士"));
		item.addItem(new ItemBO("mage", "法师"));
		item.addItem(new ItemBO("assassin", "刺客"));
		item.addItem(new ItemBO("tank", "坦克"));
		item.addItem(new ItemBO("marksman", "射手"));
		item.addItem(new ItemBO("support", "辅助"));
		return item;
	}


	public static BaseComboBox<ItemBO> CREATE_POSITION_SELECT() {
		BaseComboBox<ItemBO> item = new BaseComboBox<ItemBO>();
		item.addItem(new ItemBO("all", "全部位置"));
		item.addItem(new ItemBO("top", "上单"));
		item.addItem(new ItemBO("jungle", "打野"));
		item.addItem(new ItemBO("mid", "中单"));
		item.addItem(new ItemBO("bottom", "下路"));
		item.addItem(new ItemBO("support", "辅助"));
		return item;
	}

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
	public final static String BLACK_LIST_FILE = "resources/blacklist/game/";
}
