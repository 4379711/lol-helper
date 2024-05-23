package yalong.site.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 游戏模式枚举
 *
 * @author WuYi
 */
public enum GameTypeEnum {
    CUSTOM(0, "自定义"),
    RANK_SOLO(420, "单双排位"),
    RANK_FLEX(440, "灵活排位"),
    ARAM(450, "极地大乱斗"),
    BOT_INTRO(870, "新·入门人机"),
    BOT_INTERMEDIATE(880, "新·新手人机"),
    BOT_BEGINNER(890, "新·一般人机"),
    ARURF(900, "无限火力"),
    CHERRY(1700, "斗魂竞技场");
    private int queueId;
    private String typeName;

    GameTypeEnum(int queueId, String typeName) {
        this.queueId = queueId;
        this.typeName = typeName;
    }

    private static final Map<Integer, GameTypeEnum> BY_ID = new HashMap<>();

    static {
        for (GameTypeEnum e : values()) {
            BY_ID.put(e.getQueueId(), e);
        }
    }

    public static GameTypeEnum valueOf(int id) {
        return BY_ID.get(id);
    }

    public int getQueueId() {
        return queueId;
    }

    public String getTypeName() {
        return typeName;
    }
}
