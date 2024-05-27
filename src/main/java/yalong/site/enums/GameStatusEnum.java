package yalong.site.enums;

/**
 * 游戏状态枚举
 *
 * @author yaLong
 */
public enum GameStatusEnum {
	/**
	 * 无(游戏大厅)
	 */
	None ,
	/**
	 * 组队房间内
	 */
	Lobby,
	/**
	 *寻找匹配对局中
	 */
	Matchmaking,
	/**
	 *找到对局,等待接受
	 */
	ReadyCheck,
	/**
	 *选英雄中
	 */
	ChampSelect,
	/**
	 *游戏开始
	 */
	GameStart,
	/**
	 *游戏中
	 */
	InProgress,
	/**
	 *游戏即将结束
	 */
	PreEndOfGame,
	/**
	 *等待结算页面
	 */
	WaitingForStats,
	/**
	 *游戏结束
	 */
	EndOfGame,
	/**
	 *重新连接
	 */
	Reconnect,
	/**
	 *观战中
	 */
	WatchInProgress

}
