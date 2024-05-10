package yalong.site.cache;

import yalong.site.frame.MainFrame;
import yalong.site.frame.panel.client.*;
import yalong.site.frame.panel.match.MatchPanel;
import yalong.site.frame.panel.result.ResultTextPane;

/**
 * 配置全局属性
 *
 * @author yaLong
 */
public class FrameInnerCache {
    public static BanBox banBox;
    public static PickBox pickBox;
    public static PickSkinBox pickSkinBox;
    public static CareerBackgroundBox careerBackgroundBox;
    public static CareerBackgroundSkinBox careerBackgroundSkinBox;
    public static ResultTextPane resultTextPane;

    /**
     * 主窗口缓存
     */
    public static MainFrame frame;

    public static MatchPanel matchPanel;

}
