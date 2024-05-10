package yalong.site.json;

import lombok.Data;

import java.util.List;

@Data
public class Teams {
    private List<Integer> bans;
    private Integer baronKills;
    private Integer dominionVictoryScore;
    private Integer dragonKills;
    private Boolean firstBaron;
    private Boolean firstBlood;
    private Boolean firstDargon;
    private Boolean firstInhibitor;
    private Boolean firstTower;
    private Integer hordeKills;
    private Integer teamId;
    private Integer towerKills;
    private Integer vilemawKills;
    private String win;


}
