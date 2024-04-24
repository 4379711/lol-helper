package yalong.site.frame.panel.client.listener;

import lombok.extern.slf4j.Slf4j;
import yalong.site.cache.AppCache;
import yalong.site.cache.FrameCache;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author yalong
 */
@Slf4j
public class RankBoxItemListener implements ItemListener {
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			if (!FrameCache.currentRankBO.isNull()) {
				try {
					AppCache.api.setRank(FrameCache.currentRankBO);
				} catch (Exception ex) {
					log.error("设置rank错误", ex);
				}
			}

		}

	}
}
