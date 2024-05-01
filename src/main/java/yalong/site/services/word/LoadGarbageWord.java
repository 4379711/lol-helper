package yalong.site.services.word;

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
 * @author YaLong
 * @date 2023/6/2
 */
@Slf4j
public class LoadGarbageWord {
	private static final String USER_FILE = "words.txt";

	public void loadDefaultFile() {
		//读取默认文件到指定路径
		try {
			InputStream inputStream = LoadGarbageWord.class.getResourceAsStream("/fuck.txt");
			//复制默认文件
			FileUtil.writeFromStream(inputStream, new File(USER_FILE));
		} catch (Exception e) {
			log.error("默认垃圾话复制加载失败", e);
		}
	}

	public ArrayList<String> loadWord() {
		ArrayList<String> strings = new ArrayList<>();
		//加载到内存
		try {
			boolean exist = FileUtil.exist(USER_FILE);
			if (!exist) {
				loadDefaultFile();
			}
			InputStream inputStream = Files.newInputStream(Paths.get(USER_FILE));
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("#") || "".equals(line.trim())) {
					continue;
				}
				strings.add(line);
			}
		} catch (Exception e) {
			log.error("垃圾话读取失败", e);
		}
		return strings;
	}

}
