package helper.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author WuYi
 */
@Data
public class SettingStateBO {
    @Schema(description = "段位游戏模式")
    private String rankFirst;
    @Schema(description = "段位")
    private String rankSecond;
    @Schema(description = "段位等级")
    private String rankThird;
    @Schema(description = "自动接受对局")
    private boolean autoAccept;
    @Schema(description = "自动寻找对局")
    private boolean autoSearch;
    @Schema(description = "自动再来一局")
    private boolean autoPlayAgain;
    @Schema(description = "掉线自动重连")
    private boolean autoReconnect;
    @Schema(description = "一键连招")
    private boolean autoKey;
    @Schema(description = "互动模式")
    private boolean communicate;
    @Schema(description = "客户端状态")
    private String state;
    @Schema(description = "禁用英雄")
    private Integer banChampionId;
    @Schema(description = "秒选英雄")
    private Integer pickChampionId;
    @Schema(description = "生涯背景英雄")
    private Integer backgroundChampionId;
    @Schema(description = "生涯背景英雄皮肤")
    private Integer backgroundSkinId;
}
