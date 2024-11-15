package helper.enums;

import lombok.Getter;

/**
 * @author WuYi
 */
@Getter
public enum RankModeEnum {
    RANKED_TFT("云顶之弈"),
    RANKED_SOLO_5x5("单双排"),
    RANKED_FLEX_SR("灵活排"),
    RANKED_TFT_TURBO("云顶狂暴"),
    RANKED_TFT_DOUBLE_UP("云顶双人"),
    CHERRY("斗魂竞技场");
    private final String rankName;

    RankModeEnum(String rankName) {
        this.rankName = rankName;
    }
}
