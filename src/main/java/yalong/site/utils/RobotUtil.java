package yalong.site.utils;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;

/**
 * @author yalong
 */
@Slf4j
public class RobotUtil {
	public static final Robot ROBOT;
	static {
		try {
			ROBOT = new Robot();
		} catch (AWTException e) {
			log.error("注册Robot失败", e);
			throw new RuntimeException(e);
		}
	}

}
