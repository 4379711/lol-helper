package helper.enums;

import lombok.Getter;

/**
 * @author WuYi
 */
@Getter
public enum RankTier {
    UNRANKED("UNRANKED", "无段位"),
    IRON("IRON", "坚韧黑铁"),
    BRONZE("BRONZE", "不屈白银"),
    GOLD("GOLD", "荣耀黄金"),
    PLATINUM("PLATINUM", "华贵铂金"),
    EMERALD("EMERALD", "流光翡翠"),
    DIAMOND("DIAMOND", "璀璨钻石"),
    MASTER("MASTER", "超凡大师"),
    GRANDMASTER("GRANDMASTER", "傲世宗师"),
    CHALLENGER("CHALLENGER", "最强王者"),
    ;
    private final String typeName;
    private final String cnName;

    RankTier(String typeName, String cnName) {
        this.typeName = typeName;
        this.cnName = cnName;
    }

}
