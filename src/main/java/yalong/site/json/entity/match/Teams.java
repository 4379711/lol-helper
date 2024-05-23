package yalong.site.json.entity.match;

import lombok.Data;

import java.util.List;

/**
 * 队伍数据
 *
 * @author WuYi
 */
@Data
public class Teams {
	private List<Integer> bans;
	private Integer baronKills;
	private Integer dominionVictoryScore;
	private Integer dragonKills;
	private Boolean firstBaron;
	private Boolean firstBlood;
	private Boolean firstDargon;
	private Boolean firstInhibitor;
	private Boolean firstTower;
	private Integer hordeKills;
	private Integer teamId;
	private Integer towerKills;
	private Integer vilemawKills;
	private String win;

}
