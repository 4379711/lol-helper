package yalong.site.cache;

import yalong.site.bo.RankBO;

/**
 * 配置全局属性
 *
 * @author yaLong
 */
public class FrameUserSetting {

	/**
	 * 秒选英雄id
	 */
	public static Integer pickChampionId = null;
	/**
	 * 禁用英雄id
	 */
	public static Integer banChampionId = null;
	/**
	 * 生涯背景英雄id
	 */
	public static Integer careerChampionId = null;
	/**
	 * 伪造段位
	 */
	public static RankBO currentRankBO = new RankBO("RANKED_SOLO_5x5", "UNRANKED", "I");
}
