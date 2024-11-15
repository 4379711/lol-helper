package helper.enums;

import lombok.Getter;

/**
 * @author WuYi
 */
@Getter
public enum RankEnum {
    NONE("暂无段位"),
    IRON("坚韧黑铁"),
    BRONZE("英勇黄铜"),
    SILVER("不屈白银"),
    GOLD("荣耀黄金"),
    PLATINUM("华贵铂金"),
    EMERALD("流光翡翠"),
    DIAMOND("璀璨钻石"),
    MASTER("超凡大师"),
    GRANDMASTER("傲世宗师"),
    CHALLENGER("最强王者");

    private final String name;

    RankEnum(String name) {
        this.name = name;
    }
}
