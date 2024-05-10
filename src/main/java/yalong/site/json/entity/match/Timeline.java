package yalong.site.json.entity.match;

import lombok.Data;

/**
 * 时间线
 *
 * @author WuYi
 */
@Data
public class Timeline {
    private String creepsPerMinDeltas;
    private String csDiffPerMinDeltas;
    private String damageTakenDiffPerMinDeltas;
    private String damageTakenPerMinDeltas;
    private String goldPerMinDeltas;
    private String lane;
    private Integer participantId;
    private String role;
    private String xpDiffPerMinDeltas;
    private String xpPerMinDeltas;
}
