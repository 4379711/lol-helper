package yalong.site.cache;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置全局属性
 *
 * @author yaLong
 */
public class FrameUserSettingPersistence {

	/**
	 * 自动寻找对局
	 */
	public static boolean autoSearch = true;

	/**
	 * 自动接受对局
	 */
	public static boolean autoAccept = true;

	/**
	 * 自动重连
	 */
	public static boolean autoReconnect = false;

	/**
	 * 再来一局
	 */
	public static boolean autoPlayAgain = false;

	/**
	 * 发送战绩
	 */
	public static boolean sendScore = true;

	/**
	 * 互动模式
	 */
	public static boolean communicate = true;

	/**
	 * 一键连招
	 */
	public static boolean autoKey = false;
	/**
	 * 房间内显示战绩
	 */
	public static boolean showMatchHistory = true;
	/**
	 * 房间内显示战绩筛选的模式
	 */
	public static List<Integer> selectMode = new ArrayList<>();


}
