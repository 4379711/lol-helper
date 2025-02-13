package helper.frame.panel.history;

import helper.cache.AppCache;
import helper.frame.panel.base.BaseCheckBox;
import helper.frame.utils.FrameConfigUtil;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author WuYi
 */
public class SendBlackPlayerBox extends BaseCheckBox {
    public SendBlackPlayerBox() {
        this.setText("队友黑名单提示");
        this.setSelected(AppCache.settingPersistence.getSendBlackPlayer());
        this.addItemListener(listener());
    }

    private ItemListener listener() {
        return e -> {
            AppCache.settingPersistence.setSendBlackPlayer(e.getStateChange() == ItemEvent.SELECTED);
            FrameConfigUtil.save();
        };

    }
}
