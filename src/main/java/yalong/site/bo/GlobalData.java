package yalong.site.bo;

import yalong.site.enums.GameStatusEnum;
import yalong.site.services.LeagueClientService;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * 配置全局属性
 *
 * @author yaLong
 */
public class GlobalData {
    /**
     * 宽度
     */
    public static final int WIDTH = 500;
    /**
     * 高度
     */
    public static final int HEIGHT = 250;

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("[HH:mm:ss]: ");

    public static MutableAttributeSet GREEN_ATTR = new SimpleAttributeSet();
    public static MutableAttributeSet BLACK_ATTR = new SimpleAttributeSet();

    static {
        StyleConstants.setForeground(GREEN_ATTR, Color.GREEN);
        StyleConstants.setForeground(BLACK_ATTR, Color.black);
    }

    public static boolean autoAccept = true;

    public static boolean autoReconnect = false;

    public static boolean sendScore = true;

    public static boolean communicate = true;

    public static boolean moyan = false;

    public static RankBO currentRankBO = new RankBO("RANKED_SOLO_5x5", "CHALLENGER", "IV");

    public static LeagueClientService service;
    public static GameStatusEnum gameStatus;
    public static ArrayList<String> otherTeamScore = new ArrayList<>();
    public static ArrayList<String> myTeamScore = new ArrayList<>();
    public static ArrayList<String> communicateWords;
    public static String lastCommunicateWord;
}
