package yalong.site.frame.utils;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import yalong.site.cache.AppCache;
import yalong.site.cache.FrameUserSettingPersistence;
import yalong.site.services.hotkey.HotKeyConsumer;
import yalong.site.utils.RobotUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author YaLong
 */
@Slf4j
public class DiyKeyUtil {
	private static final String HOT_KEY_FILE = "hotkey.txt";

	public static void loadDefaultFile() {
		try {
			InputStream inputStream = DiyKeyUtil.class.getResourceAsStream("/default-hotkey.txt");
			FileUtil.writeFromStream(inputStream, new File(HOT_KEY_FILE));
		} catch (Exception e) {
			log.error("默认按键复制失败", e);
		}
	}

	public static ArrayList<String> loadKey() {
		ArrayList<String> strings = new ArrayList<>();
		try {
			boolean exist = FileUtil.exist(new File(HOT_KEY_FILE));
			if (!exist) {
				loadDefaultFile();
			}
			InputStream inputStream = Files.newInputStream(Paths.get(HOT_KEY_FILE));
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
			String line;
			while ((line = reader.readLine()) != null) {
				strings.add(line);
			}
		} catch (Exception e) {
			log.error("默认按键加载失败", e);
		}
		return strings;
	}

	public static void saveFile(String text) {
		FileUtil.writeUtf8String(text.strip(), new File(HOT_KEY_FILE));
	}

	public static ArrayList<String> parseString(String text) {
		String[] split = text.split(System.lineSeparator());
		return new ArrayList<>(Arrays.asList(split));
	}

	/**
	 * 解析按键
	 *
	 * @param list 1.全部是数字
	 *             2.全部是合法键码
	 */

	public static Map<Integer, HotKeyConsumer> parseKey2Consumer(ArrayList<String> list) {
		return list.stream()
				.filter(i -> !i.trim().isEmpty())
				.distinct()
				.map(i -> i.split(" +"))
				.peek(str -> {
					int codeKey = Integer.parseInt(str[0]);
					if (!AppCache.hotKeyCode.contains(codeKey) && codeKey >= 0) {
						throw new RuntimeException(String.format("快捷键键码%d不可用", codeKey));
					}
					for (int j = 1; j < str.length; j++) {
						int codeValue = Integer.parseInt(str[j]);
						if (!AppCache.commonKeyCode.contains(codeValue) && codeValue >= 0) {
							throw new RuntimeException(String.format("连招键码%d不可用", codeValue));
						}
					}
				})
				.collect(Collectors.toMap(
						i -> Integer.parseInt(i[0]),
						line -> () -> keyCodeParam -> {
							for (int i = 1; i < line.length; i++) {
								if (FrameUserSettingPersistence.autoKey) {
									int keyCode = Integer.parseInt(line[i]);
									if (keyCode >= 0) {
										RobotUtil.ROBOT.keyPress(keyCode);
										RobotUtil.ROBOT.keyRelease(keyCode);
									} else {
										RobotUtil.ROBOT.delay(Math.abs(keyCode));
									}
								}
							}
						},
						(a, b) -> b
				));
	}
}
