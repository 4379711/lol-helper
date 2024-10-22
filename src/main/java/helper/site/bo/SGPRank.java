package helper.site.bo;

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
}
