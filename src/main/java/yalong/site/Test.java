package yalong.site;

import yalong.site.frame.MainFrame;
import yalong.site.frame.utils.FrameMsgUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author yaLong
 * @date 2022/2/10
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        MainFrame.start();
        for (int i = 0; i < 50; i++) {
            FrameMsgUtil.sendLine("fjhaoi");
            TimeUnit.SECONDS.sleep(1);
        }

    }
}
