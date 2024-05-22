package yalong.site.frame.panel.match.listener;

import lombok.extern.slf4j.Slf4j;
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
 * 检测选中哪个模块事件
 *
 * @author WuYi
 */
@Slf4j
public class TabPaneChangeListener implements ChangeListener {
    @Override
    public void stateChanged(ChangeEvent e) {
        Component selectedComponent = ((TabPane) e.getSource()).getSelectedComponent();
        if (selectedComponent instanceof MatchPanel) {
            if (FrameInnerCache.frame != null && GameDataCache.me != null) {
                FrameInnerCache.frame.setSize(FrameSetting.WIDTH, FrameSetting.HEIGHT);
                try {
                    ProductsMatchHistoryBO pmh = AppCache.api.getProductsMatchHistoryByPuuid(GameDataCache.me.getPuuid(), 0, FrameSetting.PAGE_SIZE - 1);
                    FrameInnerCache.matchPanel.clear();
                    FrameInnerCache.matchPanel.setData(pmh, GameDataCache.me.getPuuid());

                } catch (IOException ex) {
                    log.error("查询战绩错误" + ex.getMessage());
                }
            }
        } else {
            if (FrameInnerCache.frame != null) {
                FrameInnerCache.frame.setSize(FrameSetting.WIDTH, FrameSetting.HEIGHT);
            }
        }
    }
}
