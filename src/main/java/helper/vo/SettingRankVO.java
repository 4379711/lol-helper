package helper.vo;

import helper.enums.RankDivision;
import helper.enums.RankQueue;
import helper.enums.RankTier;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author WuYi
 */
@Data
public class SettingRankVO {
    @Schema(description = "段位游戏模式")
    private List<RankQueue> rankQueues = List.of(RankQueue.values());
    @Schema(description = "段位")
    private List<RankTier> rankTiers = List.of(RankTier.values());
    @Schema(description = "段位等级")
    private List<RankDivision> rankDivisions = List.of(RankDivision.values());
}
