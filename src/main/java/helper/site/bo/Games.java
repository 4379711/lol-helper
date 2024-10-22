package helper.site.bo;

import lombok.Data;

import java.util.List;

/**
 * 查询到多条比赛记录
 *
 * @author WuYi
 */
@Data
public class Games {
	private String gameBeginDate;
	private Integer gameCount;
	private String gameEndDate;
	private Integer gameIndexBegin;
	private Integer gameIndexEnd;
	private List<GameData> games;

}
