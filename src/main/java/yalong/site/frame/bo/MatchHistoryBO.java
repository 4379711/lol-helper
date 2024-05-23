package yalong.site.frame.bo;

import lombok.Data;

import javax.swing.*;
import java.util.Date;

@Data
public class MatchHistoryBO {
    private ImageIcon Champion;
    private ImageIcon summonerSpell1;
    private ImageIcon summonerSpell2;
    private ImageIcon item0;
    private ImageIcon item1;
    private ImageIcon item2;
    private ImageIcon item3;
    private ImageIcon item4;
    private ImageIcon item5;
    private ImageIcon item6;
    private String kda;
    private String gameType;
    private String win;
    private Date gameCreationDate;
    private String gameDuration;
}
