package helper.utils;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson2.JSON;
import helper.bo.SettingRecordBO;
import helper.bo.SettingStateBO;

import java.io.File;

/**
 * @author WuYi
 */
public class GlobalDataFileUtil {
    private static final String SETTING_STATE_FILE = "SettingState.json";
    private static final String SETTING_RECORD_FILE = "SettingRecord.json";

    public static SettingStateBO getSettingStateBO() {
        SettingStateBO bo;
        boolean exist = FileUtil.exist(new File(SETTING_STATE_FILE));
        if (exist) {
            String s = FileUtil.readUtf8String(new File(SETTING_STATE_FILE));
            bo = JSON.parseObject(s, SettingStateBO.class);
        } else {
            bo = new SettingStateBO();
            String jsonString = JSON.toJSONString(bo);
            FileUtil.writeUtf8String(jsonString, new File(SETTING_STATE_FILE));
        }
        return bo;
    }

    public static void saveSettingStateBO(SettingStateBO bo) {
        String jsonString = JSON.toJSONString(bo);
        FileUtil.writeUtf8String(jsonString, new File(SETTING_STATE_FILE));
    }

    public static SettingRecordBO SettingRecordBO() {
        SettingRecordBO bo;
        boolean exist = FileUtil.exist(new File(SETTING_RECORD_FILE));
        if (exist) {
            String s = FileUtil.readUtf8String(new File(SETTING_RECORD_FILE));
            bo = JSON.parseObject(s, SettingRecordBO.class);
        } else {
            bo = new SettingRecordBO();
            String jsonString = JSON.toJSONString(bo);
            FileUtil.writeUtf8String(jsonString, new File(SETTING_RECORD_FILE));
        }
        return bo;
    }

    public static void saveSettingRecordBO(SettingRecordBO bo) {
        String jsonString = JSON.toJSONString(bo);
        FileUtil.writeUtf8String(jsonString, new File(SETTING_RECORD_FILE));
    }
}
