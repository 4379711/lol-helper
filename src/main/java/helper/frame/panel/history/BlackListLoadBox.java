package helper.frame.panel.history;

import helper.cache.AppCache;
import helper.frame.panel.base.BaseCheckBox;
import helper.frame.utils.FrameConfigUtil;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author WuYi
 */
public class BlackListLoadBox extends BaseCheckBox {
    public BlackListLoadBox() {
        this.setText("重启后加载黑名单");
        this.setSelected(AppCache.settingPersistence.getBlackListLoad());
        this.addItemListener(listener());
    }

    private ItemListener listener() {
        return e -> {
            AppCache.settingPersistence.setBlackListLoad(e.getStateChange() == ItemEvent.SELECTED);
            FrameConfigUtil.save();
        };
    }
}
