package helper.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author WuYi
 */
@Data
@Schema(description = "大乱斗骰子")
public class RerollPointsVO {
    @Schema(description = "当前点数")
    private Integer currentPoints;
    @Schema(description = "最大骰子数")
    private Integer maxRolls;
    @Schema(description = "已有骰子")
    private Integer numberOfRolls;
    @Schema(description = "骰子所需点数")
    private Integer pointsCostToRoll;
    @Schema(description = "下个骰子所需点数")
    private Integer pointsToReroll;
}
