package yalong.site.cache;

import yalong.site.frame.MainFrame;
import yalong.site.frame.panel.client.BanBox;
import yalong.site.frame.panel.client.PickBox;
import yalong.site.frame.panel.client.PickSkinBox;
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
    public static ResultTextPane resultTextPane;

    /**
     * 主窗口缓存
     */
    public static MainFrame frame;

    public static MatchPanel matchPanel;

}
