package helper.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author WuYi
 */
@AllArgsConstructor
@Data
public class BlackListBO {
    private String mePuuid;
    private List<BlackListPlayer> blackListPlayers;
    private SpgGames spgGames;
    private Boolean win;
}
