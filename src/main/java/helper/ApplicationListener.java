package helper;

import helper.bo.SettingRecordBO;
import helper.bo.SettingStateBO;
import helper.cache.GameDataCache;
import helper.listener.GameStatusListener;
import helper.utils.GlobalDataFileUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author WuYi
 */
@Slf4j
@Component
public class ApplicationListener implements CommandLineRunner, DisposableBean {
    @Resource
    private SettingStateBO settingStateBO;
    @Resource
    private SettingRecordBO settingRecordBO;
    @Resource
    private GameStatusListener gameStatusListener;

    @Override
    public void destroy() throws Exception {
        GlobalDataFileUtil.saveSettingStateBO(settingStateBO);
        GlobalDataFileUtil.saveSettingRecordBO(settingRecordBO);
    }

    @Override
    public void run(String... args)  {
        GameDataCache.cacheLcuAll();
        try {
            gameStatusListener.listenerStart();
        } catch (InterruptedException e) {
            log.error("客户端错误");
        } catch (IOException e) {
            log.error("客户端错误2");
        }
    }
}
