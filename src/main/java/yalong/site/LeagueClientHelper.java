package yalong.site;

import com.alibaba.fastjson.JSON;
import yalong.site.bo.LeagueClientBO;
import yalong.site.bo.SummonerInfoBO;
import yalong.site.services.LinkLeagueClientService;
import yalong.site.utils.ProcessUtil;
import yalong.site.utils.RequestUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yaLong
 */
public class LeagueClientHelper {
    public static void main(String[] args) throws IOException {
        LeagueClientBO leagueClientBO = ProcessUtil.getClientProcess();
        RequestUtil requestUtil = new RequestUtil(leagueClientBO);
        LinkLeagueClientService leagueClientService = new LinkLeagueClientService(requestUtil);

//        SummonerInfoBO resp = leagueClientService.getCurrentSummoner();

        //System.out.println(leagueClientService.getRoomInfo());
        //String resp = leagueClientService.getOnlineData();
        //String id= "endgame2821246797";
        //String resp =leagueClientService.msg2Room(id,"hahha");
         //leagueClientService.accept();
//        SummonerInfoBO resp = leagueClientService.getInfoByName("PresumpTuous丶白");
        System.out.println(requestUtil.doGet("/lol-gameflow/v1/session"));
        //String resp =leagueClientService.getScoreById("3bb4d96a-5c04-55e5-a991-38e45897e89b",5);
        //Matchmaking ChampSelect PreEndOfGame EndOfGame
    }
}
