package helper.bo;

import helper.frame.utils.FrameConfigUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 当前段位选择
 *
 * @author @_@
 * @date 2022/2/14
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RankBO {
	private String firstRank;
	private String secondRank;
	private String thirdRank;

	public boolean isNull() {
		return firstRank == null || secondRank == null || thirdRank == null;
	}

	public void setFirstRank(String firstRank) {
		this.firstRank = firstRank;
		FrameConfigUtil.save();
	}

	public void setSecondRank(String secondRank) {
		this.secondRank = secondRank;
		FrameConfigUtil.save();
	}

	public void setThirdRank(String thirdRank) {
		this.thirdRank = thirdRank;
		FrameConfigUtil.save();
	}
}
