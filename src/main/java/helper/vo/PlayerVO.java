package helper.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author WuYi
 */
@Data
@Schema(description = "召唤师信息")
public class PlayerVO {
    @Schema(description = "用户ID")
    private Long accountId;
    @Schema(description = "姓名")
    private String displayName;
    @Schema(description = "用户ID")
    private String puuid;
    @Schema(description = "游戏名")
    private String gameName;
    @Schema(description = "游戏内姓名")
    private String internalName;
    /*@Schema(description = "姓名是否改变")
    private Boolean nameChangeFlag;*/
    @Schema(description = "距离升级的百分比")
    private Integer percentCompleteForNextLevel;
    @Schema(description = "生涯是否公开")
    private String privacy;
    @Schema(description = "召唤师头像")
    private String profileIconId;
    @Schema(description = "召唤师ID")
    private Long summonerId;
    @Schema(description = "召唤师等级")
    private Integer summonerLevel;
    @Schema(description = "召唤师尾标")
    private Integer tagLine;
    /*private String unnamed;*/
    @Schema(description = "上一级经验")
    private Integer xpSinceLastLevel;
    @Schema(description = "升级所需经验")
    private Integer xpUntilNextLevel;
    @Schema(description = "大乱斗骰子")
    private RerollPointsVO rerollPoints;

}
