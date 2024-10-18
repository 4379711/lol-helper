package helper.utils;

import cn.hutool.core.io.FileUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import helper.bo.SettingRecordBO;
import helper.bo.SettingStateBO;
import helper.cache.AppCache;
import helper.services.word.GarbageWord;
import helper.vo.SettingRankVO;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author WuYi
 */
@Slf4j
public class GlobalDataUtil {
    private static final String SETTING_STATE_FILE = "SettingState.json";
    private static final String SETTING_RECORD_FILE = "SettingRecord.json";
    private static final String WORDS_FILE = "words.txt";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static SettingStateBO getSettingState() {
        SettingStateBO bo;
        boolean exist = FileUtil.exist(new File(SETTING_STATE_FILE));
        if (exist) {
            String s = FileUtil.readUtf8String(new File(SETTING_STATE_FILE));
            try {
                bo = mapper.readValue(s, SettingStateBO.class);
            } catch (JsonProcessingException e) {
                log.error("Json转换错误");
                return new SettingStateBO();
            }
        } else {
            bo = new SettingStateBO();
            String jsonString;
            try {
                jsonString = mapper.writeValueAsString(bo);
            } catch (JsonProcessingException e) {
                log.error("Json转换错误");
                jsonString = "{}";
            }
            FileUtil.writeUtf8String(jsonString, new File(SETTING_STATE_FILE));
        }
        return bo;
    }

    public static void saveSettingState(SettingStateBO bo) {
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(bo);
            FileUtil.writeUtf8String(jsonString, new File(SETTING_STATE_FILE));
        } catch (JsonProcessingException e) {
            log.error("Json转换错误");
        }

    }

    public static SettingRecordBO getSettingRecord() {
        SettingRecordBO bo;
        boolean exist = FileUtil.exist(new File(SETTING_RECORD_FILE));
        if (exist) {
            String s = FileUtil.readUtf8String(new File(SETTING_RECORD_FILE));
            try {
                bo = mapper.readValue(s, SettingRecordBO.class);
            } catch (JsonProcessingException e) {
                log.error("Json转换错误");
                return new SettingRecordBO();
            }
        } else {
            bo = new SettingRecordBO();
            String jsonString = null;
            try {
                jsonString = mapper.writeValueAsString(bo);
            } catch (JsonProcessingException e) {
                log.error("Json转换错误");
                jsonString = "{}";
            }
            FileUtil.writeUtf8String(jsonString, new File(SETTING_RECORD_FILE));
        }
        return bo;
    }

    public static void saveSettingRecord(SettingRecordBO bo) {
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(bo);
            FileUtil.writeUtf8String(jsonString, new File(SETTING_RECORD_FILE));
        } catch (JsonProcessingException e) {
            log.error("Json转换错误");
        }
    }

    public static SettingRankVO getRank() {
        SettingRankVO vo = new SettingRankVO();
        List<String> firstRanks = Arrays.asList("1", "2", "3");
        return null;
    }



    public static void loadWordDefaultFile() {
        //读取默认文件到指定路径
        try {
            InputStream inputStream = GarbageWord.class.getResourceAsStream("/fuck.txt");
            //复制默认文件
            FileUtil.writeFromStream(inputStream, new File(WORDS_FILE));
        } catch (Exception e) {
            log.error("默认垃圾话复制加载失败", e);
        }
    }

    public static ArrayList<String> loadWord() {
        ArrayList<String> strings = new ArrayList<>();
        //加载到内存
        try {
            boolean exist = FileUtil.exist(new File(WORDS_FILE));
            if (!exist) {
                loadWordDefaultFile();
            }
            InputStream inputStream = Files.newInputStream(Paths.get(WORDS_FILE));
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

    public static void saveWordsFile() {
        FileUtil.writeLines(AppCache.garbageWordList,new File(WORDS_FILE),"UTF-8");
    }
}
