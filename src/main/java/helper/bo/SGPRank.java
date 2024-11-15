package helper.bo;

import lombok.Data;

/**
 * 段位
 *
 * @author WuYi
 */
@Data
public class SGPRank {
	/**
	 * 游戏模式
	 */
	private String queueType;
	/**
	 * 段位
	 */
	private String tier;
	/**
	 * 段位等级
	 */
	private String rank;
	/**
	 * 胜场
	 */
	private int wins;
	/**
	 * 败场
	 */
	private int losses;
	/**
	 * 上赛季段位
	 */
	private String previousSeasonEndTier;
	/**
	 * 上赛季段位等级
	 */
	private String previousSeasonEndRank;
}
