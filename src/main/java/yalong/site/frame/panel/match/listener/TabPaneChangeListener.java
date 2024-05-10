package yalong.site.frame.panel.match.listener;

import yalong.site.bo.ProductsMatchHistoryBO;
import yalong.site.cache.AppCache;
import yalong.site.cache.FrameInnerCache;
import yalong.site.cache.FrameSetting;
import yalong.site.cache.GameDataCache;
import yalong.site.frame.panel.TabPane;
import yalong.site.frame.panel.match.MatchPanel;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.IOException;

/**
 * 检测选中哪个模块
 *
 * @author WuYi
 */
public class TabPaneChangeListener implements ChangeListener {
    @Override
    public void stateChanged(ChangeEvent e) {
        Component selectedComponent = ((TabPane) e.getSource()).getSelectedComponent();
        if (selectedComponent instanceof MatchPanel) {
            if (FrameInnerCache.frame != null) {
                FrameInnerCache.frame.setSize(FrameSetting.BIG_WIDTH, FrameSetting.BIG_HEIGHT);
                try {
                    ProductsMatchHistoryBO pmh = AppCache.api.getProductsMatchHistoryByPuuid(GameDataCache.me.getPuuid(), 0, 20);
                    FrameInnerCache.matchPanel.setData(pmh);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

        } else {
            if (FrameInnerCache.frame != null) {
                FrameInnerCache.frame.setSize(FrameSetting.WIDTH, FrameSetting.HEIGHT);
            }
        }
    }
}
