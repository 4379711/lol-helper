package yalong.site.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 战绩
 *
 * @author yaLong
 */
@AllArgsConstructor
@Data
public class SummonerScoreBO {
	private Player summonerInfo;
	private List<ScoreBO> scoreBOList;
}
