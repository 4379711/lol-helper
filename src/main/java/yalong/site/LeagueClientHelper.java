package yalong.site;

import yalong.site.services.LeagueClientService;

import java.io.IOException;

/**
 * @author yaLong
 */
public class LeagueClientHelper {


    public static void main(String[] args) throws IOException, InterruptedException {
        LeagueClientService leagueClientService = new LeagueClientService();
        leagueClientService.doit();
    }
}
