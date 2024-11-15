package helper.bo;

import lombok.Data;

/**
 * 比赛记录数据
 *
 * @author WuYi
 */
@Data
public class Participants {
	private Integer championId;
	private String highestAchievedSeasonTier;
	private Integer participantId;
	private Integer spell1Id;
	private Integer spell2Id;
	private Integer teamId;
	private Stats stats;
	private Timeline timeline;
}

