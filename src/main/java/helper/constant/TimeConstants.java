package helper.constant;

/**
 * @author @_@
 */
public interface TimeConstants {

	/**
	 * 1天的分钟数
	 */
	Long DAY_MIN = 60 * 24L;

	/**
	 * 1天的秒数
	 */
	Long DAY_SECOND = DAY_MIN * 60;

	/**
	 * 1天的毫秒数
	 */
	Long DAY_MILL = DAY_SECOND * 1000;

	/**
	 * 格式化时间到天,用来转换为Long
	 */
	String DAY_LONG_FORMAT = "yyyyMMdd";

	/**
	 * 格式化时间到天
	 */
	String DAY_FORMAT = "yyyy-MM-dd";

	/**
	 * 格式化时间到天
	 */
	String DAY_FORMAT_DIAGONAL = "yyyy/MM/dd";

	/**
	 * 格式化时间到月
	 */
	String MONTH_FORMAT = "yyyy-MM";

	/**
	 * 格式化时间保留月日
	 */
	String MONTH_DAY_FORMAT = "MM-dd";

	/**
	 * 格式化时间到秒
	 */
	String SECONDS_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 格式化时间到秒,long
	 */
	String SECOND_LONG_FORMAT = "yyyyMMddHHmmss";

	/**
	 * 格式化时间到分钟,long
	 */
	String MINUTE_LONG_FORMAT = "yyyyMMddHHmm";

	/**
	 * 格式化时间到小时
	 */
	String HOUR_FORMAT = "yyyy-MM-dd HH";

	/**
	 * 格式化时间保留月日
	 */
	String YEAR_MONTH_FORMAT = "yyyyMM";

	/**
	 * 格式化时间保留时分
	 */
	String HOUR_MINUTE_FORMAT = "HH:mm";

}
