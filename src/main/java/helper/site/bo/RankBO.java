package helper.site.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 当前段位选择
 *
 * @author @_@
 * @date 2022/2/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankBO {
	private String firstRank;
	private String secondRank;
	private String thirdRank;

	public boolean isNull() {
		return firstRank == null || secondRank == null || thirdRank == null;
	}
}
