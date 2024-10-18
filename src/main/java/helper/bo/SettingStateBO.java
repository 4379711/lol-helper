package helper.bo;

import com.alibaba.fastjson2.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author WuYi
 */
@Data
public class SettingStateBO implements Serializable {
    @Schema(description = "段位游戏模式")
    private String rankFirst;
    @Schema(description = "段位")
    private String rankSecond;
    @Schema(description = "段位等级")
    private String rankThird;
    @Schema(description = "自动接受对局")
    private Boolean autoAccept;
    @Schema(description = "自动寻找对局")
    private Boolean autoSearch;
    @Schema(description = "自动再来一局")
    private Boolean autoPlayAgain;
    @Schema(description = "掉线自动重连")
    private Boolean autoReconnect;
    @Schema(description = "一键连招")
    private Boolean autoKey;
    @Schema(description = "互动模式")
    private Boolean communicate;
    @Schema(description = "卡炫彩")
    private Boolean pickSkin;
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
    public SettingStateBO(){
        this.rankFirst = "";
        this.rankSecond = "";
        this.rankThird = "";
        this.autoAccept = false;
        this.autoSearch = false;
        this.autoPlayAgain = false;
        this.autoReconnect = false;
        this.autoKey = false;
        this.communicate = false;
        this.state = "";
        this.banChampionId = 0;
        this.pickChampionId = 0;
        this.backgroundChampionId = 0;
        this.backgroundSkinId = 0;
    }
}
