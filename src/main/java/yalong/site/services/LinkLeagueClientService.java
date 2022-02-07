package yalong.site.services;

import lombok.Data;
import yalong.site.utils.RequestUtil;

import java.io.IOException;

/**
 * 连接lol客户端服务
 *
 * @author yaLong
 */
@Data
public class LinkLeagueClientService {
    private RequestUtil requestUtil;

    public String getLoginInfo() throws IOException {
        String s = requestUtil.doGet("/lol-login/v1/session");
        System.out.println(s);
        return s;
    }

}
