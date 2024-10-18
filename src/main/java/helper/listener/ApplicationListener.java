package helper.listener;

import helper.ClientStarter;
import helper.cache.AppCache;
import helper.cache.GlobalData;
import helper.utils.GlobalDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * APP生命周期
 *
 * @author WuYi
 */
@Slf4j
@Component
public class ApplicationListener implements CommandLineRunner, DisposableBean {

    @Override
    public void run(String... args) {
        GlobalData.rank = GlobalDataUtil.getRank();
        GlobalData.settingState = GlobalDataUtil.getSettingState();
        GlobalData.settingRecord = GlobalDataUtil.getSettingRecord();
        // 加载垃圾话
        AppCache.garbageWordList = GlobalDataUtil.loadWord();

        ClientStarter client = new ClientStarter();
        client.run();
    }

    @Override
    public void destroy() {
        GlobalDataUtil.saveSettingState(GlobalData.settingState);
        GlobalDataUtil.saveSettingRecord(GlobalData.settingRecord);
    }


}
