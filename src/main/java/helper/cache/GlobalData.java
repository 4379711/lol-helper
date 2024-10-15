package helper.cache;

import helper.bo.SettingRecordBO;
import helper.bo.SettingStateBO;
import helper.utils.GlobalDataFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author WuYi
 */
@Configuration
@Slf4j
public class GlobalData {


    @Bean
    public SettingStateBO getSettingState() {
        return GlobalDataFileUtil.getSettingStateBO();
    }
    @Bean
    public SettingRecordBO getSettingRecord() {
        return GlobalDataFileUtil.SettingRecordBO();
    }

}
