package yalong.site.bo;

import lombok.Data;

/**
 * 玩家
 *
 * @author WuYi
 */
@Data
public class Player {
	private String accountId;
	private String currentAccountId;
	private String currentPlatformId;
	private String gameName;
	private String matchHistoryUri;
	private String platformId;
	private String profileIcon;
	private String puuid;
	private Long summonerId;
	private String summonerName;
	private String tagLine;
	private String privacy;
	private int summonerLevel;
}
