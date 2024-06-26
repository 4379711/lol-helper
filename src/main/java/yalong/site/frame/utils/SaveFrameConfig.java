package yalong.site.frame.utils;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import yalong.site.cache.FrameUserSettingPersistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yalong
 */
public class SaveFrameConfig {
	private static final String FRAME_CONFIG_FILE = "frameConfig.txt";

	public static void save() {
		HashMap<String, Object> map = new HashMap<>();
		for (Field field : FrameUserSettingPersistence.class.getFields()) {
			String name = field.getName();
			try {
				Object o = field.get(name);
				map.put(name, o);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		String jsonString = JSON.toJSONString(map);
		FileUtil.writeUtf8String(jsonString, new File(FRAME_CONFIG_FILE));
	}

	public static void load() {
		boolean exist = FileUtil.exist(new File(FRAME_CONFIG_FILE));
		if (!exist) {
			return;
		}
		try {
			InputStream inputStream = Files.newInputStream(Paths.get(FRAME_CONFIG_FILE));
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
			String jsonString = reader.readLine();
			Map map1 = JSONObject.parseObject(jsonString, Map.class);
			for (Field field : FrameUserSettingPersistence.class.getFields()) {
				String name = field.getName();
				Object o = map1.get(name);
				if (o==null || o instanceof com.alibaba.fastjson2.JSONObject) {
					//对象类型暂时不缓存到文件
					continue;
				}
				field.set(name, o);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
