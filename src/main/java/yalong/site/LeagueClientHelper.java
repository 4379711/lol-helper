package yalong.site;

import yalong.site.frame.MainFrame;
import yalong.site.frame.utils.FrameMsgUtil;
import yalong.site.services.HotKeyService;
import yalong.site.services.LeagueClientService;

/**
 * @author yaLong
 */
public class LeagueClientHelper {

    public static void main(String[] args) {
        MainFrame.start();
        HotKeyService.start();
        while (true) {
            try {
                LeagueClientService.start();
            } catch (Exception e) {
                FrameMsgUtil.sendLine(e.getLocalizedMessage());
                MainFrame.hiddenFrame();
                int running = MainFrame.continueRun(e.getMessage());
                if (running == 1) {
                    System.exit(0);
                } else {
                    MainFrame.showFrame();
                }
            }
        }
    }
}