package helper.frame.utils;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import helper.cache.AppCache;

import java.io.File;

/**
 * @author @_@
 */
public class FrameConfigUtil {
	private static final String FRAME_CONFIG_FILE = "frameConfig.json";

	public static void save() {
		String jsonString = JSON.toJSONString(AppCache.settingPersistence);
		FileUtil.writeUtf8String(jsonString, new File(FRAME_CONFIG_FILE));
	}

	public static void load() {
		boolean exist = FileUtil.exist(new File(FRAME_CONFIG_FILE));
		if (!exist) {
			return;
		}
		try {
			String jsonString = FileUtil.readUtf8String(new File(FRAME_CONFIG_FILE));
			AppCache.settingPersistence = JSONObject.parseObject(jsonString, AppCache.settingPersistence.getClass());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
