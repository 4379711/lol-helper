package yalong.site;

import yalong.site.services.LeagueClientService;
import yalong.site.utils.DmUtil;

/**
 * @author yaLong
 */
public class LeagueClientHelper {

    public static void main(String[] args) throws Exception {
        DmUtil dmUtil = new DmUtil();
        LeagueClientService leagueClientService = new LeagueClientService(dmUtil);
        while (true) {
            leagueClientService.switchGameStatus();
            //F11 开关
            int key = dmUtil.waitKey(122, 3);
            if (key == 1) {
                break;
            }
        }
    }
}
