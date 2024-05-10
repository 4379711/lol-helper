package yalong.site.cache;

import yalong.site.bo.RankBO;
import yalong.site.frame.MainFrame;

/**
 * 配置全局属性
 *
 * @author yaLong
 */
public class FrameCache {
	public static boolean autoAccept = true;

	public static boolean autoReconnect = true;

	public static boolean autoPlayAgain = false;

	public static Integer pickChampionId = null;

	public static Integer banChampionId = null;

	public static boolean sendScore = true;

	public static boolean communicate = true;

	public static boolean moyan = false;

	public static RankBO currentRankBO = new RankBO("RANKED_SOLO_5x5", "UNRANKED", "I");

}
