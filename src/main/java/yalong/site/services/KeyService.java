package yalong.site.services;

import org.jnativehook.keyboard.NativeKeyEvent;
import yalong.site.bo.GlobalData;
import yalong.site.utils.DdUtil;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author yaLong
 * @date 2022/3/3 21:16
 */
public class KeyService {
    public static Random random = new Random();

    public static Consumer<Integer> moyan() {
        return i -> {
            if (GlobalData.moyan && i == NativeKeyEvent.VC_T) {
                try {
                    // 可能没按到,多试几次
                    DdUtil.INSTANCE.DD_key(204, 1);
                    DdUtil.INSTANCE.DD_key(204, 2);
                    TimeUnit.MILLISECONDS.sleep(10);
                    DdUtil.INSTANCE.DD_key(403, 1);
                    DdUtil.INSTANCE.DD_key(403, 2);
                    TimeUnit.MILLISECONDS.sleep(10);
                    for (int j = 0; j < 5; j++) {
                        DdUtil.INSTANCE.DD_key(302, 1);
                        TimeUnit.MILLISECONDS.sleep(20);
                        DdUtil.INSTANCE.DD_key(302, 2);
                    }

                } catch (InterruptedException ignored) {
                }
            }
        };
    }

    public static void leave() {
        while (true) {
            if (GlobalData.leave) {
                int x = random.nextInt(300);
                int y = random.nextInt(100);
                if (x % 2 == 1) {
                    x = -x;
                }
                if (y % 2 == 1) {
                    y = -y;
                }

                //移动
                DdUtil.INSTANCE.DD_movR(x, y);
                //鼠标右键点击
                DdUtil.INSTANCE.DD_btn(4);
                DdUtil.INSTANCE.DD_btn(8);
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ignored) {
                    break;
                }

            } else {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ignored) {
                    break;
                }
            }
        }
    }


}
