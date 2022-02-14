package yalong.site;

import yalong.site.frame.MainFrame;
import yalong.site.services.LeagueClientService;
import yalong.site.utils.DmUtil;

/**
 * @author yaLong
 */
public class LeagueClientHelper {

    public static void main(String[] args) {
        while (true) {
            try {
                DmUtil dmUtil = new DmUtil();
                LeagueClientService service = new LeagueClientService(dmUtil);
                service.runForever();
                MainFrame.start();
            } catch (Exception e) {
                int running = MainFrame.isRunning();
                if (running == 1) {
                    System.exit(0);
                }
            }
        }
    }
}
