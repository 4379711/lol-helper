package helper.services.word;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * @author @_@
 * @date 2023/6/2
 */
@Slf4j
public class GarbageWord {
	private static final String USER_FILE = "resources/words.txt";

	public static void loadDefaultFile() {
		//读取默认文件到指定路径
		try {
			InputStream inputStream = GarbageWord.class.getResourceAsStream("/fuck.txt");
			//复制默认文件
			FileUtil.writeFromStream(inputStream, new File(USER_FILE));
		} catch (Exception e) {
			log.error("默认垃圾话复制加载失败", e);
		}
	}

	public static ArrayList<String> loadWord() {
		ArrayList<String> strings = new ArrayList<>();
		//加载到内存
		try {
			boolean exist = FileUtil.exist(new File(USER_FILE));
			if (!exist) {
				loadDefaultFile();
			}
			InputStream inputStream = Files.newInputStream(Paths.get(USER_FILE));
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("#") || "".equals(line.strip())) {
					continue;
				}
				strings.add(line);
			}
		} catch (Exception e) {
			log.error("垃圾话读取失败", e);
		}
		return strings;
	}

	public static void saveFile(String text) {
		FileUtil.writeUtf8String(text.strip(), new File(USER_FILE));
	}

}
