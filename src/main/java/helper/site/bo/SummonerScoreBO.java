package helper.site.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 战绩
 *
 * @author @_@
 */
@AllArgsConstructor
@Data
public class SummonerScoreBO {
	private Player summonerInfo;
	private List<ScoreBO> scoreBOList;
}
