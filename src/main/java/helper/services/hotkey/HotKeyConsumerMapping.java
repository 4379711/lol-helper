package helper.services.hotkey;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author WuYi
 */
@Data
public class HotKeyConsumerMapping {
    private static final Logger log = LoggerFactory.getLogger(HotKeyConsumerMapping.class);
    /**
     * 监听实现
     */
    private HotKeyConsumer hotKeyConsumer;
    private String hotKeyConsumerClass;
    /**
     * 绑定按键
     */
    private Integer keyCode;
    /**
     * 是否为组合键
     */
    private boolean isCombinationKey;

    public HotKeyConsumerMapping(Integer keyCode, HotKeyConsumer hotKeyConsumer) {
        this.keyCode = keyCode;
        this.hotKeyConsumer = hotKeyConsumer;
        this.hotKeyConsumerClass = hotKeyConsumer.getClass().getName();
    }

    public void instantiateConsumer() {
        try {
            Class<?> clazz = Class.forName(hotKeyConsumerClass);
            this.hotKeyConsumer = (HotKeyConsumer) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            log.error("加载按键映射失败：{}", e.toString());
        }
    }
}
