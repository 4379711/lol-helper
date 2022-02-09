package yalong.site;

import yalong.site.bo.ScoreBO;
import yalong.site.bo.TeamPuuidBO;
import yalong.site.services.LeagueClientService;

import java.io.IOException;
import java.util.List;

/**
 * @author yaLong
 */
public class LeagueClientHelper {


    public static void main(String[] args) throws IOException, InterruptedException {
        LeagueClientService leagueClientService = new LeagueClientService();

        leagueClientService.doit();
    }
}
