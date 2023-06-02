package yalong.site.services;

import cn.hutool.core.io.FileUtil;
import yalong.site.frame.panel.client.SendScoreCheckBox;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * @author YaLong
 * @date 2023/6/2
 */
public class LoadGarbageWord {
    private static final String USER_FILE = "words.txt";

    public void loadDefaultFile() {
        //喷人的词语从文件读取
        boolean exist = FileUtil.exist(USER_FILE);
        if (!exist) {
            //读取默认文件
            try {
                InputStream inputStream = SendScoreCheckBox.class.getResourceAsStream("/fuck.txt");
                //复制默认文件
                FileUtil.writeFromStream(inputStream, new File(USER_FILE));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> loadWord() {
        //加载到内存
        try {
            InputStream inputStream = Files.newInputStream(Paths.get(USER_FILE));
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line;
            ArrayList<String> strings = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if(line.startsWith("#") || "".equals(line.trim())){
                    continue;
                }
                strings.add(line);
            }
            return strings;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
