package yalong.site;

import yalong.site.bo.GlobalData;
import yalong.site.frame.MainFrame;
import yalong.site.services.LeagueClientService;

/**
 * @author yaLong
 */
public class LeagueClientHelper {

    public static void main(String[] args) {
        while (true) {
            try {
                MainFrame.start();
                LeagueClientService service = new LeagueClientService();
                service.setRank(GlobalData.currentRankBO);
                service.runForever();
            } catch (Exception e) {
                e.printStackTrace();
                MainFrame.hiddenFrame();
                int running = MainFrame.continueRun(e.getMessage());
                if (running == 1) {
                    System.exit(0);
                }
            }
        }
    }
}