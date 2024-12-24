package helper.cache;

import com.alibaba.fastjson2.annotation.JSONField;
import helper.bo.RankBO;
import lombok.Data;

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
	 * 房间内显示战绩筛选的模式
	 */
	private Integer selectMode;
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
}