package yalong.site.bo;

import lombok.Data;

/**
 * 战绩
 *
 * @author yaLong
 */
@Data
public class ScoreBO {

    /**
     * 对英雄伤害
     */
    private Integer totalDamageDealtToChampions;

    /**
     * 承受伤害
     */
    private Integer totalDamageTaken;
    /**
     * 击杀
     */
    private Integer kills;
    /**
     * 死亡
     */
    private Integer deaths;
    /**
     * 助攻
     */
    private Integer assists;
    /**
     * 赢了吗?
     */
    private Boolean win;
    /**
     * 补刀
     */
    private Integer totalTimeCrowdControlDealt;
}
