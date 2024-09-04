package yalong.site.frame.bo;

import lombok.Data;

import javax.swing.*;

/**
 * 英雄胜率
 *
 * @author WuYi
 */
@Data
public class ChampionWin {
    private int championId;
    private int wins;
    private int fails;
    private ImageIcon icon;
    private String winRate;
}
