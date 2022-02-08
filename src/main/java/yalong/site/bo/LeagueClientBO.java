package yalong.site.bo;

import lombok.Data;

/**
 * 客户端信息
 *
 * @author yaLong
 */
@Data
public class LeagueClientBO {
    /**
     * 端口
     */
    private String port;

    /**
     * 密钥
     */
    private String token;
}
