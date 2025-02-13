package helper.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author WuYi
 */
@Slf4j
public class ConfigUtil {
    public static String getVersion() {
        String version = "";
        try (InputStream input = ConfigUtil.class.getClassLoader()
                .getResourceAsStream("version.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            // 读取版本号
            version = prop.getProperty("app.version");
        } catch (Exception e) {
            log.error("读取配置失败");
        }
        return version;
    }
}