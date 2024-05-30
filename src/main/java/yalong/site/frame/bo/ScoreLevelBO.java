package yalong.site.frame.bo;

import lombok.Data;

/**
 * 评分
 *
 * @author WuYi
 */
@Data
public class ScoreLevelBO {
	/**
	 * 评分
	 */
	private Double score;
	/**
	 * 排名
	 */
	private Integer order;
	/**
	 * 是否是MVP或者SVP
	 */
	private Boolean isMvp;
	/**
	 * 所在队伍
	 */
	private Integer TeamId;
}
