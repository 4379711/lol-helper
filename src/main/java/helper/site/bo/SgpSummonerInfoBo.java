package helper.site.bo;

import lombok.Data;

/**
 * sgp的召唤术数据姓名为老数据尽量
 *
 * @author WuYi
 */
@Data
public class SgpSummonerInfoBo {
	public Long id;
	private String puuid;
	private Long accountId;
	/**
	 * 头像id
	 */
	private int profileIconId;
	private int level;
	private int expPoints;
	private int levelAndXpVersion;
	private String revisionId;
	private Long revisionDate;
	private Long lastGameDate;
	private String privacy;
	private int expToNextLevel;
}
