package yalong.site;

import yalong.site.bo.LeagueClientBO;
import yalong.site.services.LinkLeagueClientService;
import yalong.site.utils.ProcessUtil;
import yalong.site.utils.RequestUtil;

import java.io.IOException;

/**
 * @author yaLong
 */
public class LeagueClientHelper {
    public static void main(String[] args) throws IOException {
        LeagueClientBO leagueClientBO = ProcessUtil.getClientProcess();
        LinkLeagueClientService leagueClientService = new LinkLeagueClientService();
        RequestUtil requestUtil = new RequestUtil(leagueClientBO);
        leagueClientService.setRequestUtil(requestUtil);
        String loginInfo = leagueClientService.getLoginInfo();
    }
}
