package helper.enums;

import lombok.Getter;

/**
 * @author WuYi
 */
@Getter
public enum RankQueue {
    RANKED_SOLO_5x5("RANKED_SOLO_5x5", "单排/双排"),
    RANKED_DUO_5x5("RANKED_FLEX_SR", "灵活组排 5v5"),
    RANKED_FLEX_SR("RANKED_FLEX_TT", "灵活组排 3v3"),
    RANKED_FLEX_TT("RANKED_TFT", "云顶之弈"),
    ;
    private final String queue;
    private final String cnName;

    RankQueue(String queue, String cnName) {
        this.queue = queue;
        this.cnName = cnName;
    }
}
