package yalong.site.bo;

import lombok.Data;

import java.util.List;

/**
 * 召唤师技能
 *
 * @author WuYi
 */
@Data
public class SummonerSpellsBO {
    private Integer id;
    private String name;
    private String description;
    private Integer summonerLevel;
    private Integer cooldown;
    private List<String> gameModes;
    private String iconPath;

}
