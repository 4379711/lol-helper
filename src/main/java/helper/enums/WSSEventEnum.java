package helper.enums;

import helper.services.wss.ChampSelectData;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author WuYi
 */
@Getter
public enum WSSEventEnum {
    //MATCHMAKING_READY("OnJsonApiEvent_lol-matchmaking_v1_ready-check", MatchmakingReadyData.class, "Update"),
    //    GAMEFLOW_SESSION("OnJsonApiEvent_lol-gameflow_v1_session", GameflowSessionUpdateData.class, "Update"),
    GAMEFLOW_PHASE("OnJsonApiEvent_lol-gameflow_v1_gameflow-phase", String.class, "Update"),
    //CHAMP_SELECT("OnJsonApiEvent_lol-champ-select_v1_session", ChampSelectData.class, "Update");
    SGP_TOKEN("OnJsonApiEvent_entitlements_v1_token",String.class, "Update");

    private Class<?> data;

    private String uri;

    private String eventType;

    WSSEventEnum(String uri, Class<?> data, String eventType) {
        this.uri = uri;
        this.data = data;
        this.eventType = eventType;
    }

    /**
     * 根据事件订阅地址和事件类型查找事件枚举
     *
     * @param uri       事件订阅地址
     * @param eventType 事件类型
     */
    public WSSEventEnum getWSSEventEnum(String uri, String eventType) {
        Optional<WSSEventEnum> first = Arrays.stream(WSSEventEnum.values()).filter(item -> uri.contains(item.getUri()) && item.eventType.equals(eventType)).findFirst();
        return first.orElse(null);
    }

    /**
     * 根据事件订阅地址查找事件枚举 默认传参eventType为Update
     *
     * @param uri 订阅地址
     */
    public WSSEventEnum getWSSEventEnum(String uri) {
        return getWSSEventEnum(uri, "Update");
    }
    }

