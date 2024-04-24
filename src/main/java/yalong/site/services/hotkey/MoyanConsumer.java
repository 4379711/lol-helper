package yalong.site.services.hotkey;

import yalong.site.cache.FrameCache;
import yalong.site.utils.KeyEventUtil;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

/**
 * @author yalong
 */
public class MoyanConsumer implements HotKeyConsumer {
    private final Robot robot = KeyEventUtil.ROBOT;
    private final int keyDelay = 100;

    @Override
    public Consumer<Integer> build() {
        return i -> {
            if (FrameCache.moyan) {
                // 按键:4DW
                robot.keyPress(KeyEvent.VK_4);
                robot.keyRelease(KeyEvent.VK_4);
                robot.delay(keyDelay);
                robot.keyPress(KeyEvent.VK_D);
                robot.keyRelease(KeyEvent.VK_D);
                robot.delay(keyDelay);
                //w可能没按到,多试几次
                for (int j = 0; j < 5; j++) {
                    robot.keyPress(KeyEvent.VK_W);
                    robot.delay(keyDelay);
                    robot.keyRelease(KeyEvent.VK_W);
                    robot.delay(keyDelay);
                }
            }
        };
    }
}
