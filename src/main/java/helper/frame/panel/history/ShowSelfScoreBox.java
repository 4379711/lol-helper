package helper.frame.panel.history;

import helper.cache.AppCache;
import helper.frame.panel.base.BaseCheckBox;
import helper.frame.utils.FrameConfigUtil;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author WuYi
 */
public class ShowSelfScoreBox extends BaseCheckBox {
    public ShowSelfScoreBox() {
        this.setText("是否发送自己评分");
        this.setSelected(AppCache.settingPersistence.getShowSelfScore());
        this.addItemListener(listener());
    }

    private ItemListener listener() {
        return e -> {
            AppCache.settingPersistence.setShowSelfScore(e.getStateChange() == ItemEvent.SELECTED);
            FrameConfigUtil.save();
        };

    }

}
