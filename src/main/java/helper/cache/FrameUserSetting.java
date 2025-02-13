package helper.cache;

import com.alibaba.fastjson2.annotation.JSONField;
import helper.bo.RankBO;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 用户的配置
 *
 * @author @_@
 */
@Data
public class FrameUserSetting {

	/**
	 * 自动寻找对局
	 */
	private Boolean autoSearch = false;

	/**
	 * 自动接受对局
	 */
	private Boolean autoAccept = true;

	/**
	 * 自动重连
	 */
	private Boolean autoReconnect = false;

	/**
	 * 再来一局
	 */
	private Boolean autoPlayAgain = false;

	/**
	 * 发送战绩
	 */
	private Boolean sendScore = true;

	/**
	 * 互动模式
	 */
	private Boolean communicate = true;

	/**
	 * 选择炫彩
	 */
	private Boolean pickSkin = false;
	/**
	 * 房间内显示战绩
	 */
	private Boolean showMatchHistory = true;
	/**
	 * 战绩功能提示
	 */
	private Boolean showNotice = false;
	/**
	 * 黑名单玩家
	 */
	private Map<String, String> blacklistPlayers = new LinkedHashMap<>();

	/**
	 * 秒选英雄id
	 */
	@JSONField(serialize = false)
	private Integer pickChampionId = null;
	/**
	 * 禁用英雄id
	 */
	@JSONField(serialize = false)
	private Integer banChampionId = null;
	/**
	 * 生涯背景英雄id
	 */
	@JSONField(serialize = false)
	private Integer careerChampionId = null;
	/**
	 * 伪造段位
	 */
	@JSONField(serialize = false)
	private RankBO currentRankBO = new RankBO("RANKED_SOLO_5x5", "UNRANKED", "I");
	/**
	 * 评分和胜率的筛选数量
	 */
	private Integer HistoryCount = 20;
	/**
	 * 评分标记
	 */
	private String[] playerTags = new String[]{"内鬼", "下等马", "中等马", "上等马"};
	/**
	 * 评分分割数
	 */
	private double[] playerBetween = new double[]{7, 8, 10};
	/**
	 * 禁英雄
	 */
	private Map<String, ArrayList<Integer>> banMap = new LinkedHashMap<>() {{
		put("default", new ArrayList<Integer>());
		put("top", new ArrayList<Integer>());
		put("jungle", new ArrayList<Integer>());
		put("middle", new ArrayList<Integer>());
		put("bottom", new ArrayList<Integer>());
		put("utility", new ArrayList<Integer>());
	}};
	/**
	 * 是否忽略队友预选
	 */
	private Boolean banTeammateFlag = false;
	/**
	 * 选英雄
	 */
	private Map<String, ArrayList<Integer>> pickMap = new LinkedHashMap<>() {{
		put("default", new ArrayList<Integer>());
		put("bench", new ArrayList<Integer>());
		put("top", new ArrayList<Integer>());
		put("jungle", new ArrayList<Integer>());
		put("middle", new ArrayList<Integer>());
		put("bottom", new ArrayList<Integer>());
		put("utility", new ArrayList<Integer>());
	}};
	/**
	 * 是否忽略队友预选
	 */
	private Boolean pickTeammateFlag = false;
	/**
	 * 是否排位秒选预选
	 */
	private Boolean championSelectFlag = false;
	/**
	 * 是否需要加载黑名单
	 */
	private Boolean blackListLoad = true;
	/**
	 * 是否输了后弹出黑名单添加界面
	 */
	private Boolean blackListAddVisible = false;
	/**
	 * 房间内显示战绩筛选的模式
	 */
	private Integer selectMode;
	/**
	 * 是否发送黑名单玩家
	 */
	private Boolean sendBlackPlayer = false;
	/**
	 * 是否展示自己的评分
	 */
	private Boolean showSelfScore = false;
}