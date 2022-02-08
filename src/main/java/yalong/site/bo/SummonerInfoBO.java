package yalong.site.bo;

import lombok.Data;

/**
 * 召唤师信息
 *
 * @author yaLong
 */
@Data
public class SummonerInfoBO {
    private String puuid;
    private String accountId;
    private String displayName;
    private String summonerId;
    private String summonerLevel;
}
