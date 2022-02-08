package yalong.site;

import com.alibaba.fastjson.JSON;
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
        RequestUtil requestUtil = new RequestUtil(leagueClientBO);
        LinkLeagueClientService leagueClientService = new LinkLeagueClientService(requestUtil);
//        String resp = leagueClientService.getLoginInfo();
//        String resp = leagueClientService.changeStatus("offline");
//        String resp = leagueClientService.getRoomInfo();
//        String id= "c1~bb0cf32d6f4743d69e8b1d9f87905c56d9fa877f";
//        String resp =leagueClientService.msg2Room(id,"hahha");
        String resp = leagueClientService.accept();//成功结果是空消息
        System.out.println(JSON.toJSONString(resp));
    }
}
