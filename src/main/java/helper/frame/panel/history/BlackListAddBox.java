package helper.frame.panel.history;

import helper.cache.AppCache;
import helper.frame.panel.base.BaseCheckBox;
import helper.frame.utils.FrameConfigUtil;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author WuYi
 */
public class BlackListAddBox extends BaseCheckBox {
    public BlackListAddBox() {
        this.setText("失败后添加黑名单");
        this.setSelected(AppCache.settingPersistence.getBlackListAddVisible());
        this.addItemListener(listener());
    }

    private ItemListener listener() {
        return e -> {
            AppCache.settingPersistence.setBlackListAddVisible(e.getStateChange() == ItemEvent.SELECTED);
            FrameConfigUtil.save();
        };
    }
}
