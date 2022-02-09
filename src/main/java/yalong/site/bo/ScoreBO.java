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
}
