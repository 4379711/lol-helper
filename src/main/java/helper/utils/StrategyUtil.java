package helper.utils;

import helper.cache.FrameInnerCache;
import helper.cache.GameDataCache;

/**
 * @author WuYi
 */
public class StrategyUtil {
    /**
     * 隐藏队友面板
     */
    public static void hidePanel() {
        if (FrameInnerCache.myTeamMatchHistoryPanel != null && FrameInnerCache.myTeamMatchHistoryPanel.isVisible()) {
            FrameInnerCache.myTeamMatchHistoryPanel.setVisible(false);
        }
    }

    /**
     * 重置战绩和评分
     */
    public static void resetHistory() {
        if (!GameDataCache.myTeamMatchHistory.isEmpty() || !GameDataCache.enemyTeamMatchHistory.isEmpty() || !GameDataCache.myTeamScore.isEmpty() || !GameDataCache.enemyTeamScore.isEmpty()) {
            GameDataCache.reset();
        }
    }

    /**
     * 重置房间信息ID
     */
    public static void resetRoomMessageId(){
        GameDataCache.roomMessageId = null;
    }




}
